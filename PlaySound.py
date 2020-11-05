from omxplayer.player import OMXPlayer
import time 
# import MotorTurn as motor

def playSystemSounds(command): # Her skal der ikke motor respons til, færdig as is
    SystemAudioList = ["taend.wav", "bluetoothtaend1.wav", "bluetoothforbundet.wav", "bluetoothsluk.wav", "sluk.wav"] # Færdig
    command = command.split("-")
    number = command.pop(0)
    
    player = OMXPlayer(SystemAudioList[number])

def playSingleSong(number): # Her skal der ikke motor respons til
    SongAudioList = ["bluetoothtaend1.wav", "bluetoothtaend2.wav"] # Skal udfyldes med musikstykker, de her er placeholders
  
    player = OMXPlayer(SongAudioList[number])
    
    
def playSequence(seq):
    seqList = seq.split("-")
    seqList.pop(0)
    
    for i in range(seqList):        
        fileNumber = seqList[i]
        
         # Der skal nok en tråd-funktion heromkring
        player = OMXPlayer(SongAudioList[fileNumber])
        motor.act(fileNumber)
        
def playAudioBook(command):
    AudioBookList("TBB.wav")
    identifier = command.split("-")
    idenftifier.pop(0)

    # Potentiel også en tråd
    player = OMXPlayer(AudioBookList[identifier])
    motor.act(identifier)