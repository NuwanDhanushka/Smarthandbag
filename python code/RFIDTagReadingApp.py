import RPi.GPIO as GPIO
import MFRC522
import signal
import firebase_admin
from firebase_admin import credentials
from firebase_admin import db
continue_reading = True

cred = credentials.Certificate('firebase/serviceAccountKey.json')
default_app = firebase_admin.initialize_app(cred,{
    'databaseURL': 'https://smarthandbag1.firebaseio.com/'
})
ref = db.reference('MandatoryItemList/')
tags = ref.get()


# Capture SIGINT for cleanup when the script is aborted
def end_read(signal,frame):
    global continue_reading
    print "Ctrl+C captured, ending read."
    continue_reading = False
    GPIO.cleanup()

# Hook the SIGINT
signal.signal(signal.SIGINT, end_read)

# Create an object of the class MFRC522
MIFAREReader = MFRC522.MFRC522()

# Welcome message
print "Welcome to the MFRC522 data read example"
print "Press Ctrl-C to stop."

# This loop keeps checking for chips. If one is near it will get the UID and authenticate
while continue_reading:
    
    # Scan for cards    
    (status,TagType) = MIFAREReader.MFRC522_Request(MIFAREReader.PICC_REQIDL)

    # If a card is found
    if status == MIFAREReader.MI_OK:
        print "Card detected"
    
    # Get the UID of the card
    (status,uid) = MIFAREReader.MFRC522_Anticoll()

    # If we have the UID, continue
    if status == MIFAREReader.MI_OK:

        # Print UID
        print "Card read UID: %s,%s,%s,%s,%s" % (uid[0], uid[1], uid[2], uid[3], uid[4])
        str1 =  ''.join(map(str, uid))
        print str1
        
        for key in tags:
            if str1 == key:
                parent = 'MandatoryItemList'
                child = 'ItemStatus'
                refer = "/".join((parent, key,child))
                ref2 = db.reference(refer)
                status =ref2.get()
                if status == "InsideTheBag":
                    ref2.set("OutOfBag")
                else:
                    ref2.set("InsideTheBag")
                          
        # This is the default key for authentication
        key = [0xFF,0xFF,0xFF,0xFF,0xFF,0xFF]
        
        # Select the scanned tag
        MIFAREReader.MFRC522_SelectTag(uid)

        # Authenticate
        status = MIFAREReader.MFRC522_Auth(MIFAREReader.PICC_AUTHENT1A, 8, key, uid)

        # Check if authenticated
        if status == MIFAREReader.MI_OK:
            MIFAREReader.MFRC522_Read(8)
            MIFAREReader.MFRC522_StopCrypto1()
        else:
            print "Authentication error"

