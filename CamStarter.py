import math

import cv2

MAX_COLOR_DIFFERENCE = 30 # the max color difference when traversing through the screen
MAX_PIXEL_SEARCH = 100000 # the max amount of pixels it can traverse through

COLOR_RED = [0, 0, 255]

def mouseClick(event,x,y,flags,param):
    if event == cv2.EVENT_LBUTTONDOWN:
        #img[y,x] = [255,0,0]
       # print(img[y,x])
        BFT(x,y) # calls BFT (breadth first traversal)
        cv2.imshow("Screen Grab", img) # updates the image after traversing

def BFT(x,y): # BFT
    visited = [[False] * width for _ in range(height)] # visited that sets each pixel to false
    visited[y][x] = True # sets the first pixel to true
    toVisit = [(x, y)] # to visit is the first pixel/location
    count = 0; # count to keep track of the amount of pixels traversed

    while len(toVisit) > 0 and count < MAX_PIXEL_SEARCH: # while we still have things to visit and we have not reached the max amount of pixels we can search through
        curr = toVisit[0] # curr is set as toVisit[0]
        toVisit.remove(toVisit[0]) #removes the first element in toVisit
        neighbors = getNeighbor(curr) # gets the neighbors of curr
        for neighbor in neighbors: # for each neighbor
            if not visited[neighbor[1]][neighbor[0]]: # if we have not visited the neighbor and its location
                if colorMatch(img[y,x], img[neighbor[1], neighbor[0]]) < MAX_COLOR_DIFFERENCE: # and if the color difference between the pixels is less than the max color difference
                    img[neighbor[1], neighbor[0]] = COLOR_RED # set the pixel to the color red
                   # print(img[neighbor[1], neighbor[0]])
                    count = count + 1 # count plus one
                    toVisit.append(neighbor) # adds the neighbor to toVisit
                visited[neighbor[1]][neighbor[0]] = True # sets the neighbor in visited to true

       # print(count)



def getNeighbor(curr): # get neighbors method
    neighbors = []
    for x in range(-1, 2):
        for y in range(-1, 2):
            if 0 <= curr[0]+x < width and 0 <= curr[1]+y < height: # gets the neighbor of the method by getting the piexels around curr
                neighbors.append((curr[0]+x, curr[1]+y)) # adds the pixels around curr as its neighbors

    return neighbors # returns neighbors

def colorMatch(color, nColor): # sees if the colors match
    r1 = int(color[0]) # gets each value of the rgb for the first and second color
    g1 = int(color[1])
    b1 = int(color[2])
    r2 = int(nColor[0])
    g2 = int(nColor[1])
    b2 = int(nColor[2])

    distance=math.sqrt((r2-r1)**2+(g2-g1)**2+(b2-b1)**2) # finds the distance using the formula
    #print(distance)
    return distance # returns the distance / color match value between the two colors

cap = cv2.VideoCapture(0)
cv2.namedWindow("Screen Grab")

img = cap.read()[1]
height, width, channels = img.shape

while(True):

    img = cap.read()[1]

    cv2.setMouseCallback("Screen Grab", mouseClick)

    cv2.imshow("Screen Grab", img)

    if cv2.waitKey(0) & 0xFF == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()
