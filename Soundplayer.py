from time import sleep
import scanplayer

print("Hello")

player = scanplayer.ScanPlayer(1)
availableFolders = list(player.tracks.keys())
print(availableFolders)
if len(availableFolders) > 0:
    keyPos = 0
    while True:
        folder = availableFolders[keyPos]
    
        player.playFolder(folder)
        while player.playing():
            sleep(0.1)
        keyPos = (keyPos + 1) %  len(availableFolders)
else:
    print("No available tracks")
    