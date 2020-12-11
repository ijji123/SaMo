from bluetooth import *
import AudioController as play
from omxplayer.player import OMXPlayer
import time

player = OMXPlayer("SystemLyde/bluetoothtaend1.wav")

server_socket=BluetoothSocket(RFCOMM)
server_socket.bind(("",PORT_ANY))
server_socket.listen(1)
port=server_socket.getsockname()[1]

uuid= "94f39d29-7d6d-437d-973b-fba39e49d4ee"
advertise_service(server_socket, "SaMo", service_id=uuid, service_classes = [uuid,SERIAL_PORT_CLASS],profiles = [SERIAL_PORT_PROFILE])

#client_socket,address = server_socket.accept()
#print("Accepted connection from ",address)

#player = OMXPlayer("SystemLyde/bluetoothforbundet.wav")
#print("ikke crashet endnu")

# while True:
    #client_socket,address = server_socket.accept()
    #print("Accepted connection from ",address)

while True:
    client_socket,address = server_socket.accept()
    print("Accepted connection from ",address)
    #player = OMXPlayer("SystemLyde/bluetoothforbundet.wav")
    print("ikke crashet endnu")
    res = client_socket.recv(1024)
    #client_socket.send(res)
    print(res)
    if res.isdigit(): # Haandterer alle enkelt-krald / preview af lyd uden motorrespons
        play.playSingleSound(res)
    if res.startswith(b"b-"): # Systemlyd
        play.playSystemSound(res)
    if res.startswith(b"s-"): # Sekvens
        play.playSequence(res)
    if res.startswith(b"a-"): # Lydbog
        play.playAudioBook(res)
    if res.startswith(b"q"): # Midlertidig sluk-metode
        #play.killAudio()
        quitCommand = "pkill omxplayer"
        os.system(quitCommand)
        play.killMotor()
    if res == b'kill': # Midlertidig sluk-metode
        client_socket.close()
        server_socket.close()
        break
