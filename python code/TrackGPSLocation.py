import os
import serial
import pynmea2
import time
import firebase_admin
from firebase_admin import credentials
from firebase_admin import db

cred = credentials.Certificate('firebase/serviceAccountKey.json')
default_app = firebase_admin.initialize_app(cred,{
    'databaseURL': 'https://smarthandbag1.firebaseio.com/'
})

def parseGPS(str):
    if str.find('GGA') > 0:
        msg = pynmea2.parse(str)
        print "Timestamp: %s -- Lat: %s %s -- Lon: %s %s -- Altitude: %s %s" % (msg.timestamp,msg.latitude,msg.lat_dir,msg.longitude,msg.lon_dir,msg.altitude,msg.altitude_units)
        ref = db.reference('DeviceLocation')
        ref.set({
            'latitude': msg.latitude,
            'longitude': msg.longitude
            })
        
serialPort = serial.Serial("/dev/serial0", 9600, timeout=0.5)

while True:
    str = serialPort.readline()
    parseGPS(str)
    time.sleep(0.2)