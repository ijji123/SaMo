# file: rfcomm-server.py
# auth: Albert Huang <albert@csail.mit.edu>
# desc: simple demonstration of a server application that uses RFCOMM sockets
#
# $Id: rfcomm-server.py 518 2007-08-10 07:20:07Z albert $
import RPi.GPIO as GPIO
import time
from bluetooth import *

server_sock=BluetoothSocket( RFCOMM )
server_sock.bind(("",PORT_ANY))
server_sock.listen(1)

port = server_sock.getsockname()[1]

uuid = "94f39d29-7d6d-437d-973b-fba39e49d4ee"

advertise_service( server_sock, "SampleServer",
                   service_id = uuid,
                   service_classes = [ uuid, SERIAL_PORT_CLASS ],
                   profiles = [ SERIAL_PORT_PROFILE ], 
#                   protocols = [ OBEX_UUID ] 
                    )
                   
print("Waiting for connection on RFCOMM channel %d" % port)

client_sock, client_info = server_sock.accept()
print("Accepted connection from ", client_info)

GPIO.setmode(GPIO.BOARD)
#GPIO.setwarnings(False)

ControlPin = [7,11,13,15]
 
# Set all pins as output
for pin in ControlPin:
    GPIO.setup(pin,GPIO.OUT)
    GPIO.output(pin, 0)

def start():
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

            seqPos = 0

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

try:
    while True:
        data = client_sock.recv(1024)
        if len(data) == 0: break
        print("received [%s]" % data)
        
        if data=='start':
            start()
        
        
except IOError:
    pass

print("disconnected")

client_sock.close()
server_sock.close()
print("all done")