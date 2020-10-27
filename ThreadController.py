from bluetooth import *
import MotorTurn as motor
import PlaySound as play

server_socket=BluetoothSocket(RFCOMM)
server_socket.bind(("",PORT_ANY))
server_socket.listen(1)
port=server_socket.getsockname()[1]

uuid= "94f39d29-7d6d-437d-973b-fba39e49d4ee"
advertise_service(server_socket, "SaMo", service_id=uuid, service_classes = [uuid,SERIAL_PORT_CLASS],profiles = [SERIAL_PORT_PROFILE])

while True:
    client_socket,address = server_socket.accept()
    print("Accepted connection from ",address)
    res = client_socket.recv(1024)
    client_socket.send(res)
    if res == b'start':
        motor.turnMotor()
    if res == b'1':
        play.playSingleSound(res)
    if res == b'q':
        print("Goodnight SaMo")
        client_socket.close()
        server_socket.close()
        break
    else:
        print("Nope, not this")
