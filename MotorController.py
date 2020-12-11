import RPi.GPIO as GPIO
import time
import sys
from threading import Thread

def setup():
    GPIO.setmode(GPIO.BOARD)
    #GPIO.setwarnings(False)

    ControlPin = [7,11,13,15]
 
    # Set all pins as output
    for pin in ControlPin:
        GPIO.setup(pin,GPIO.OUT)
        GPIO.output(pin, 0)

        seqA = [
            [1,0,0,0],
            [1,1,0,0],
            [0,1,0,0],
            [0,1,1,0],
            [0,0,1,0],
            [0,0,1,1],
            [0,0,0,1],
            [1,0,0,1]
         ]

    seqB = seqA[::-1]

def act(fileNumber):
    if fileNumber ==0:
        frontHalfRotation()
        backHalfRotation()
    if fileNumber==1:
        backHalfRotation()
        backHalfRotation()
    if fileNumber==2:
        backHalfRotation()
        frontHalfRotation()
    if fileNumber==3:
        frontHalfRotation()
        backHalfRotation()
    if fileNumber==4:
        backHalfRotation()
        backHalfRotation()
    if fileNumber==5:
        backHalfRotation()
        frontHalfRotation()
    if fileNumber==6:
        frontHalfRotation()
        backHalfRotation()
    if fileNumber==7:
        backHalfRotation()
        backHalfRotation()
    if fileNumber==8:
        backHalfRotation()
        frontHalfRotation()

        

def frontHalfRotation():
    GPIO.setmode(GPIO.BOARD)
    #GPIO.setwarnings(False)

    ControlPin = [7,11,13,15]
 
    # Set all pins as output
    for pin in ControlPin:
        GPIO.setup(pin,GPIO.OUT)
        GPIO.output(pin, 0)

        seqA = [
            [1,0,0,0],
            [1,1,0,0],
            [0,1,0,0],
            [0,1,1,0],
            [0,0,1,0],
            [0,0,1,1],
            [0,0,0,1],
            [1,0,0,1]
         ]
 
    for i in range(256):
        for halfstep in range(8):
            for pin in range(4):
                GPIO.output(ControlPin[pin], seqA[halfstep] [pin])
                time.sleep(0.0002)


def frontFullRotation():
    frontHalfRotation()
    frontHalfRotation()
    

def backHalfRotation():
    GPIO.setmode(GPIO.BOARD)
    #GPIO.setwarnings(False)

    ControlPin = [7,11,13,15]
 
    # Set all pins as output
    for pin in ControlPin:
        GPIO.setup(pin,GPIO.OUT)
        GPIO.output(pin, 0)

        seqA = [
            [1,0,0,0],
            [1,1,0,0],
            [0,1,0,0],
            [0,1,1,0],
            [0,0,1,0],
            [0,0,1,1],
            [0,0,0,1],
            [1,0,0,1]
         ]

    seqB = seqA[::-1]

    for i in range(256):
            for halfstep in range(8):
                for pin in range(4):
                    GPIO.output(ControlPin[pin], seqB[halfstep] [pin])
                    time.sleep(0.0002)
#GPIO.cleanup()

def backFullRotation():
    backHalfRotation()
    backHalfrotation()


def TBBRotation(): #seneste
   frontHalfRotation()
   backHalfRotation()
   time.sleep(1)
   frontHalfRotation()
   backHalfRotation()
   time.sleep(1)
   frontHalfRotation()
   frontHalfRotation()
   time.sleep(0.2)
   backHalfRotation()
   time.sleep(0.2)
   backHalfRotation()
   time.sleep(0.2)
   backHalfRotation()
   
def startThread():
    TBB_thread = Thread(target=TBBRotation)
    TBB_thread.start()

