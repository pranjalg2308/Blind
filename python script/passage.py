import time
import itertools
from collections import OrderedDict

def information(element, item):
    if item in element:
        element[item] = element[item] + 1
    else:
        element[item] = 0
    element = OrderedDict(sorted(element.items(), key=lambda x: x[1], reverse=True))
    return element

def timeInfo():
    initialTime = time.time()
    element = {}
    while True:
        item = input()
        element = information(element, item)
        if(time.time() >= (initialTime + 3)):
            element = information(element, item)
            # print(element)
            print(dict(itertools.islice(element.items(), 3)))
            for item in element:
                element[item] = 0
            initialTime = time.time()



if __name__ == '__main__':
    timeInfo()