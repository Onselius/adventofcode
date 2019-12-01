#! /usr/bin/python3
#File: 2dec.py
#Author: Lars Onselius
#Description: Find lines with either 2 repeating characters or 3.
#Arguments: input2dec

#Loop through line. Assign a dictionary with every letter and 0 if first
#time the character is seen. Increase with 1 after that.
def check_characters(line, number):
    my_dict = {}
    for char in line:
        my_dict.setdefault(char, 0)
        my_dict[char] = my_dict[char] + 1
    if number in my_dict.values():
        return 1
    return 0

data = open("input2dec").readlines()

chars_2 = 0
chars_3 = 0

for line in data:
    chars_2 += check_characters(line, 2)
    chars_3 += check_characters(line, 3)

print(chars_2)
print(chars_3)
print(chars_2 * chars_3)

#Part 2
#Desc: Find 2 lines with only 1 character difference 
#Loop through all lines. Compare letter for letter with all other lines.
#If only one different character, return line

for first in data:
    first = first.rstrip("\n")
    for second in data:
        second = second.rstrip("\n")
        if first == second:
            continue
        diff = 0
        for i in range(len(first)):
            if first[i] != second[i]:
                diff += 1
            if diff > 1:
                break
        if diff == 1:
            print(first)
