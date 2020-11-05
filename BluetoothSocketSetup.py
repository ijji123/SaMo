import bluetooth
import MotorTurn as motor

server_socket=bluetooth.BluetoothSocket(bluetooth.RFCOMM)
port = 1
server_socket.bind(("",port))
server_socket.listen(1)
client_socket,address = server_socket.accept()
print("Accepted connection from ",address)

while True:
    res = client_socket.recv(1024)
    client_socket.send(res)
    if res == b's':
        motor.turnMotor()
    if res == b'q':
        print("Goodnight SaMo")
        client_socket.close()
        server_socket.close()
        break
    else:
        print("Nope, not this")
    