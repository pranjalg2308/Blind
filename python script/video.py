#Used for video slicing and combining

import cv2
import os

def Slice(path):
    vid = cv2.VideoCapture(path)
    count=0
    success=1
    while success:
        success, image = vid.read()
        cv2.imwrite("/home/amritesh/OOP/animated/frame" + str(count) + ".png", image)
        count = count + 1
    print(count)

def Combine():
    os.system('ffmpeg -r 1 -i /home/amritesh/OOP/animated/frame%0d.png -vcodec mpeg4 -y finalVideo.mp4')

if __name__ == '__main__':
    Slice("/home/amritesh/Videos/video99.mp4")
    Combine()