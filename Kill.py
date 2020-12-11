import os
def KillAudio():
    quitCommand = "pkill -f MotorController.py"
    os.system(quitCommand)
    quitCommand = "pkill omxplayer"
    os.system(quitCommand)
