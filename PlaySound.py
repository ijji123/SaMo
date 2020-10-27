from playsound import playsound
import MotorTurn as motor

def playSingleSound(number):
    AudioList = ["bluetoothtaend1.wav", "bluetoothtaend2.wav"]
    
    playsound(AudioList[number])
    
def playSequence(seq):
    seqList = [seq]
    
    # Sekventeret på baggrund af denominator
    # Kører i en løkke, så for hver bliver den tilføjet til seqList
    # "seqList.add()"
    
    for i in range(seqList)        
        fileNumber = seqList[i]
        playsound(AudioList[fileNumber)
        motor.act(fileNumber)