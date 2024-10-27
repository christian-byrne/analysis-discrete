import os

FILENAME = 'prog03b.dat'
FILEPATH = os.path.join(os.path.dirname(__file__), '..', 'data', FILENAME)

with open(FILEPATH, 'r') as f:
    data = f.readlines()

converted_filename = FILENAME.replace('.dat', '-positive-only.dat')

with open(os.path.join(os.path.dirname(__file__), '..', 'data', converted_filename), 'w') as f:
    for line in data:
        f.write(str(abs(int(line))) + '\n')