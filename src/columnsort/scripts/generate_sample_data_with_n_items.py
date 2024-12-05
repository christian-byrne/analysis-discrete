
OUTPUT_FILENAME = 'sample_data'
UPPER_BOUND = 5000
LOWER_BOUND = -5000
SIZE = 1_000_000

index = 1

import os

filepath = os.path.join(os.path.dirname(__file__), OUTPUT_FILENAME + '.txt')

# create file if it doesn't exist
if not os.path.exists(filepath):
    with open(filepath, 'w') as f:
        f.write('')

import random

# generate SIZE random numbers between LOWER_BOUND and UPPER_BOUND
data = [random.randint(LOWER_BOUND, UPPER_BOUND) for i in range(SIZE)]

# write data to file, one number per line
with open(filepath, 'w') as f:
    for number in data:
        f.write(str(number) + '\n')

print('Sample data written to ' + filepath)
    