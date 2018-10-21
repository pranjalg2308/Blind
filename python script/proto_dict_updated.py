import time
import itertools
from collections import OrderedDict
import socket

def information(element, item):
    if item in element:
        element[item] = element[item] + 1
    else:
        element[item] = 0
    element = OrderedDict(sorted(element.items(), key=lambda x: x[1], reverse=True))
    return element

def timeInfo():
    element = {}
    s = socket.socket()
    s.bind(('', 1250))
    s.listen(5)
    f = open("wholeFile.log", "r")
    c, addr = s.accept()
    print("got connection")
    initialTime = time.time()
    while True:
        item = str(f.readline())[0:-1]
        element = information(element, item)
        if(time.time() >= (initialTime + 2)):
            element = information(element, item)

            final = (dict(itertools.islice(element.items(),1, 4)))
            hel = list(final.keys())
            try:
                if(final[hel[0]] !=0):
                    c.send(b'')
                elif(final[hel[1]] != 0):
                    c.send(b'There are ' + hel[0].encode(encoding='utf-8') + b" " +hel[1].encode(encoding='utf-8') + b' ahead.\n')
                elif(final[hel[2]] != 0):
                    c.send(b'There are ' + hel[0].encode(encoding='utf-8') + b" " + hel[1].encode(encoding='utf-8') + b" " + hel[2].encode(encoding='utf-8') + b' ahead.\n')
                c.send(b'There are ' + hel[0].encode(encoding = 'utf-8') + b" " +hel[1].encode(encoding = 'utf-8') + b" " + hel[2].encode(encoding = 'utf-8') + b' ahead.\n')
            except:
                print()

            element = {}
            initialTime = time.time()
    c.close()
    s.close()



if __name__ == '__main__':
    timeInfo()