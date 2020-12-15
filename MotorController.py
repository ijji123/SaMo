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
        frontHalfRotationB()
        backHalfRotation()
    if fileNumber==1:
        backHalfRotation()
        backHalfRotationB()
    if fileNumber==2:
        backHalfRotation()
        frontHalfRotationB()
    if fileNumber==3:
        frontHalfRotationB()
        backHalfRotationB()
    if fileNumber==4:
        backHalfRotation()
        backHalfRotationB()
    if fileNumber==5:
        backHalfRotationB()
        frontHalfRotation()
    if fileNumber==6:
        frontHalfRotationB()
        backHalfRotation()
    if fileNumber==7:
        backHalfRotation()
        backHalfRotationB()
    if fileNumber==8:
        backHalfRotationB()
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

def frontHalfRotationB():
    GPIO.setmode(GPIO.BOARD)
    #GPIO.setwarnings(False)

    ControlPinB = [16,18,22,37]
    
 
    # Set all pins as output
    for pin in ControlPinB:
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
                GPIO.output(ControlPinB[pin], seqA[halfstep] [pin])
                time.sleep(0.0002)

def frontFullRotation():
    frontHalfRotation()
    frontHalfRotation()

def frontFullRotationB():
    frontHalfRotationB()
    frontHalfRotationB()
    

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

def backHalfRotationB():
    GPIO.setmode(GPIO.BOARD)
    #GPIO.setwarnings(False)

    ControlPinB = [16,18,22,37]
 
    # Set all pins as output
    for pin in ControlPinB:
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
                    GPIO.output(ControlPinB[pin], seqB[halfstep] [pin])
                    time.sleep(0.0002)

#def cleanUp():
#    GPIO.cleanup()


def backFullRotation():
    backHalfRotation()
    backHalfrotation()

def backFullRotationB():
    backHalfRotationB()
    backHalfrotationB()

# def TBBRotation():
#    frontHalfRotation() # "De tre bukke bruse"
#    backHalfRotation() # Introduktion
#    frontHalfRotation() # "Lille bukke bruse"
#    frontHalfRotation() # "Mellemste bukke bruse"
#    backHalfRotation() # "Store bukke bruse"
#    backHalfRotation() # "Gammel gnaven trold"
#    backHalfRotation()
    
def TBBRotation(): #seneste
   frontHalfRotation() # "De tre bukke bruse"
   backHalfRotationB() # Introduktion
   time.sleep(9)
   frontHalfRotation() # "Lille bukke bruse"
   frontHalfRotationB() # "Mellemste bukke bruse"
   backHalfRotation() # "Store bukke bruse"
   time.sleep(10)
   backHalfRotation() # "Gammel gnaven trold"
   backHalfRotationB()
   time.sleep(4)
   frontHalfRotationB() # "Trip"
   frontHalfRotation() # "Trip, trip"
   time.sleep(2)
   frontHalfRotation() #"Hvem er det der tramper paa min bro"
   time.sleep(6)
   frontHalfRotationB() #"Det er mig, den lille bukke bruse"
   time.sleep(3)
   frontHalfRotation() # "Jeg er saa sulten!"
   time.sleep(2)
   frontHalfRotation() # "Nu kommer jeg og spiser dig!"
   time.sleep(4.5)
   frontHalfRotationB() # "Jeg er saa lille og tynd"
   backHalfRotationB()
   time.sleep(3)
   frontHalfRotation() # "Saa lad gaa!"
   time.sleep(4)
   frontHalfRotationB() # tramp tramp
   frontHalfRotation() # tramp
   time.sleep(5)
   backHalfRotationB() # "Trip"
   backHalfRotation() # "Trap, trip"
   time.sleep(2)
   frontHalfRotation() # "Hvem er det der tramper paa min bro"
   time.sleep(2)
   backHalfRotationB() # "Det er mig, den mellemste bukke bruse"
   time.sleep(5)
   frontHalfRotation() # "Nu kommer jeg og spiser dig"
   time.sleep(10)
   backHalfRotationB() # "Nej, det er ikke mig!"
   frontHalfRotationB()
   time.sleep(5)
   frontHalfRotationB() #tramp 
   frontHalfRotation() #tramp tramp // Herfra er det ikke 100p skarpt, fortsaet med tests. Denne version er ikke testet! 
   time.sleep(3.5)
   frontHalfRotationB() #"Tramp,
   frontHalfRotation() #"Tramp, tramp"
   time.sleep(0.2)
   frontHalfRotation() # "Hvem er det der tramper paa min bro!"
   time.sleep(3)
   frontHalfRotationB() # "Det er mig, den store bukke bruse"
   time.sleep(2.5)
   frontHalfRotation() # "Endeligt!"


def startThread():
    TBB_thread = Thread(target=TBBRotation)
    TBB_thread.start()