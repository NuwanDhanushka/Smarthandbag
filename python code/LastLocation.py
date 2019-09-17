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

def parseGPS(strt):
    if strt.find('GGA') > 0:
        msg = pynmea2.parse(strt)
        #print "Timestamp: %s -- Lat: %s %s -- Lon: %s %s -- Altitude: %s %s" % (msg.timestamp,msg.latitude,msg.lat_dir,msg.longitude,msg.lon_dir,msg.altitude,msg.altitude_units)
        ref = db.reference('LastLocations')
	lat = msg.latitude
	lang = msg.longitude
	lastlocation1=str(lat)+","+str(lang)
        ref.update({
            'location1': lastlocation1
            })
def parseGPS2(strt):
    if strt.find('GGA') > 0:
        msg = pynmea2.parse(strt)
        #print "Timestamp: %s -- Lat: %s %s -- Lon: %s %s -- Altitude: %s %s" % (msg.timestamp,msg.latitude,msg.lat_dir,msg.longitude,msg.lon_dir,msg.altitude,msg.altitude_units)
        ref = db.reference('LastLocations')
	lat = msg.latitude
	lang = msg.longitude
	lastlocation1=str(lat)+","+str(lang)
        ref.update({
            'location2': lastlocation1
            })
def parseGPS3(strt):
    if strt.find('GGA') > 0:
        msg = pynmea2.parse(strt)
        #print "Timestamp: %s -- Lat: %s %s -- Lon: %s %s -- Altitude: %s %s" % (msg.timestamp,msg.latitude,msg.lat_dir,msg.longitude,msg.lon_dir,msg.altitude,msg.altitude_units)
        ref = db.reference('LastLocations')
	lat = msg.latitude
	lang = msg.longitude
	lastlocation1=str(lat)+","+str(lang)
        ref.update({
            'location3': lastlocation1
            })
serialPort = serial.Serial("/dev/serial0", 9600, timeout=0.5)

while True:
    strt = serialPort.readline()
    parseGPS(strt)
    time.sleep(2)
    strt = serialPort.readline()
    parseGPS2(strt)
    time.sleep(2)
    strt = serialPort.readline()
    parseGPS3(strt)
    time.sleep(2)
