import AudioController as play
from omxplayer.player import OMXPlayer
import time

#play.playSystemSound("b'b-1'")
#player = OMXPlayer("Musik/Klip1.wav")

#play.playSystemSound("b'b-3'")

#play.playSingleSound("b'0'")
#play.playSingleSound("b'5'")

#play.playSequence("b's-0'")
#play.playSequence("b's-1-5-3-7-8'")

#simulering af afbrydelse af lydbog
play.playAudioBook("b'a-0'")
time.sleep(5)
play.killAudio()
play.killMotor()
play.playAudioBook("b'a-0'")
play.killAudio()


