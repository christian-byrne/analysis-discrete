
OUTPUT_FILENAME = 'sample_data'
UPPER_BOUND = 5000
LOWER_BOUND = -5000
SIZE = 1_000_000

index = 1

import os

while os.path.exists(OUTPUT_FILENAME + str(index) + '.txt'):
    index += 1

filepath = os.path.join(os.path.dirname(__file__), '..', 'data', OUTPUT_FILENAME + str(index) + '.txt')

import random

# generate SIZE random numbers between LOWER_BOUND and UPPER_BOUND
data = [random.randint(LOWER_BOUND, UPPER_BOUND) for i in range(SIZE)]

# write data to file, one number per line
with open(filepath, 'w') as f:
    for number in data:
        f.write(str(number) + '\n')

print('Sample data written to ' + filepath)
    