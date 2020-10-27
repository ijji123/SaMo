import RPi.GPIO as GPIO
from time import sleep

GPIO.setmode(GPIO.BCM)

sleepTime = .1

#GPIO Pin of the component
LedPin = 18
BtnPin = 17

GPIO.setup(LedPin, GPIO.OUT)
GPIO.setup(BtnPin, GPIO.IN, pull_up_down=GPIO.PUD_UP)
GPIO.output(LedPin, False)

try:
    while True:
        GPIO.output(LedPin, GPIO.input(BtnPin))
        sleep(.1)
finally:
    GPIO.output(LedPin, False)
    GPIO.cleanup()
    

