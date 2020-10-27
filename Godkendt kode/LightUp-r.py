import RPi.GPIO as GPIO
import time
GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)
GPIO.setup(18, GPIO.OUT)
GPIO.output(18, True)
print("LED on")
time.sleep(3)
GPIO.output(18, False)
print("LED off")

