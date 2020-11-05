import pygame

pygame.mixer.init(frequency =44100, size = -16, channels =1, buffer = 2**12)
channel1 = pygame.mixer.Channel(0)
channel2 = pygame.mixer.Channel(1)

pygame.mixer.music.load("bluetoothtaend1.wav")
pygame.mixer.music.play(-1)
print("Played the sound lol")