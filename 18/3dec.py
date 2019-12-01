#! /usr/bin/python3
#File: 3dec.py
#Author: Lars Onselius
#Description: https://adventofcode.com/2018/day/3
#Arguments: input3dec

import re

data = open("input3dec").readlines()
grid = [[0 for x in range(1000)] for y in range(1000)]
pos_start_regex = re.compile(r'(\d+),(\d+).*\s(\d+)x(\d+)')
regex = re.compile(r'\d+')
# data = ["1,3 4x4", "3,1 4x4", "5,5 2x2"]
ids = set()
claims = set()
count = 0
for line in data:
    nr, x, y, xend, yend = regex.findall(line)
    match = pos_start_regex.search(line)
    startx = int(match.group(1))
    starty = int(match.group(2))
    endx = int(match.group(3))
    endy = int(match.group(4))
    for y in range(starty, (starty+endy)):
        for x in range(startx, (startx+endx)):
            if grid[y][x] == 1:
                claims.add(nr)
                ids.add((x, y))
                count += 1
            grid[y][x] = 1

for line in data:
    nr, x, y, xend, yend = regex.findall(line)
    x = int(x)
    y = int(y)
    xend = int(xend)
    yend = int(yend)
    claim = True
    for i in range(y, y + xend):
        if claim:
            for j in range(x, x + xend):
                if grid[i][j] == 1:
                    claim = False
                    break
    if claim:
        print("wohooo")
        claim = nr
        break
print(claim)
for line in data:
    match = pos_start_regex.search(line)
    startx = int(match.group(1))
    starty = int(match.group(2))
    endx = int(match.group(3))
    endy = int(match.group(4))
    claim = True
    for y in range(starty, (starty+endy)):
        for x in range(startx, (startx+endx)):
            print("test")
            if (x, y) in ids:
                print("hmmmmm")
                break
            if grid[y][x] == 0:
                claim = False
                break
    if claim:
        claim = nr
        print(claim)
        break

print(data[0])
print(len(ids))

print("latest")
for line in data:
    nr, x, y, xend, yend = regex.findall(line)
    if nr in claims:
        continue
    else:
        pass
for y in range(1000):
    for x in range(1000):
        print(grid[y][x], end="")
    print()
