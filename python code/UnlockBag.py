import RPi.GPIO as GPIO
import time
import firebase_admin
from firebase_admin import credentials
from firebase_admin import db

cred = credentials.Certificate('firebase/serviceAccountKey.json')
default_app = firebase_admin.initialize_app(cred,{
    'databaseURL': 'https://smarthandbag1.firebaseio.com/'
})
ref = db.reference('DeviceLock/LockStatus')
while True:
    staus = ref.get()
    if staus == "deviceunlocked":
        GPIO.setmode(GPIO.BOARD)
        GPIO.setup(12,GPIO.OUT)
        GPIO.output(12,1)
        time.sleep(3)
        GPIO.output(12,0)
        time.sleep(3)
       
        GPIO.cleanup()
        ref.set("devicelocked")
