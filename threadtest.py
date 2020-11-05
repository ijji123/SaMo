from threading import Thread
from omxplayer.player import OMXPlayer
import RPi.GPIO as GPIO
import PlaySound as sound
import time
import sys

def playSingleSong(number): # Her skal der ikke motor respons til
    SongAudioList = ["bluetoothtaend1.wav", "bluetoothtaend2.wav"] # Skal udfyldes med musikstykker, de her er placeholders
    player = OMXPlayer(SongAudioList[number])
    
def halfRotation():    
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
                time.sleep(0.0002)
                
halfRotation()
playSingleSong(1)