from omxplayer.player import OMXPlayer
import MotorController as motor
import RPi.GPIO as GPIO
import time
import os
from threading import Thread
import multiprocessing

SongList = ["Musik/Klip1.wav", "Musik/Klip2.wav","Musik/Klip3.wav","Musik/Klip4.wav","Musik/Klip5.wav","Musik/Klip6.wav","Musik/Klip7.wav","Musik/Klip8.wav","Musik/Klip9.wav" ]# Faerdig
SystemAudioList = ["SystemLyde/taend.wav", "SystemLyde/bluetoothforbundet.wav", "SystemLyde/bluetoothtaend1.wav", "SystemLyde/bluetoothsluk.wav", "sluk.wav"] # Faerdig
AudioBookList= ["Lydboeger/DTBBAudio3Seffekter.mp3"]

#motor_thread = Thread(target=motor.TBBRotation, daemon=True)
motorprocess = multiprocessing.Process(target=motor.TBBRotation)

def playSystemSound(command): # Her skal der ikke motor respons til, faerdig as is
    string = str(command)
    string = string.replace("b","")
    string = string.replace("'","")
    string = string.replace("-","")
    number = int(string)
    player = OMXPlayer(SystemAudioList[number])

def playSingleSound(command): # Her skal der ikke motor respons til, faerdig as is
    string = str(command)
    string = string.replace("b","")
    string = string.replace("'","")
    number = int(command)
    player = OMXPlayer(SongList[number])
    
def playSequence(seq):
    string = str(seq)
#   motor_seqThread = Thread(target=motor.act, args=(string), daemon=True)
#     motor_seqThread.start()
    string = str(seq)
    string = string.replace("'", "")
    seqList = string.split("-")
    seqList.pop(0)
    for i in seqList:
        file = i
        fileNumber = int(file)
        motor_thread =Thread(target=motor.act, args=(fileNumber,), daemon=True)
        motor_thread.start()
        
        player = OMXPlayer(SongList[fileNumber])
        #motor.act(fileNumber)
       # motor_thread.join()
        time.sleep(4)
        
def playAudioBook(command):
    string = str(command)
    string = string.split("-")
    string = string.pop(1)
    string = string.replace("'", "")
    number = int(string)
    player = OMXPlayer(AudioBookList[number])
    motorprocess.start()
    #motor.TBBRotation() #seneste
#   motor_thread = Thread(target=motor.TBBRotation)
    #motor_thread.start()
    #motor_thread.join()


def killAudio():
    quitCommand = "pkill omxplayer"
    os.system(quitCommand)

def killMotor():
    #quitCommand = "pkill -f MotorController.py"
    #os.system(quitCommand)
    #print("reached audio")
    #motor.kill()
    #sys.exit(0)
    #raise SystemExit(0)
    #quit()
    #return
    motorprocess.terminate()