import RPi.GPIO as GPIO
import time
import sys

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

def act1():
    halfRotation()
    time.sleep(4)
    halfRotation()

def act2():
    time.sleep()

def halfRotation():
    setup()
    
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
                GPIO.output(ControlPin[pin], seqA[halfstep] [pin])
                time.sleep(0.0008)
        
    for i in range(256):
        for halfstep in range(8):
            for pin in range(4):
                GPIO.output(ControlPin[pin], seqB[halfstep] [pin])
                time.sleep(0.0008)
    

GPIO.cleanup()