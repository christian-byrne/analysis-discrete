
import os
OUTPUT_DIR = os.path.join(os.path.dirname(__file__), '..', 'tests', 'testcases')

# Generate a random number between 1 and 256
import random
for i in range(129, 256):
    # count = random.randint(1, 32)
    count = i
    # Genreate count number of random ints between -32 and 99
    data = [random.randint(-32, 8) for _ in range(count)]
    # Write the data to a file, one int per line
    filename = os.path.join(OUTPUT_DIR, f'testcase_{i}.dat')
    while os.path.exists(filename):
        i += 1
        filename = os.path.join(OUTPUT_DIR, f'testcase_{i}.dat')
    with open(filename, 'w') as f:
        for d in data:
            f.write(f'{d}\n') 