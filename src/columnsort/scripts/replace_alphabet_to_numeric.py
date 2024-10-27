input_filepath = "/home/c_byrne/school/courses/csc345-analysis_discrete_structures/algorithm-analysis/src/columnsort/expected-steps-output.md"

output_filepath = "/home/c_byrne/school/courses/csc345-analysis_discrete_structures/algorithm-analysis/src/columnsort/expected-steps-output-numeric.md"

# read the input file
with open(input_filepath, 'r') as f:
    lines = f.readlines()


outlines = []

for line in lines:
    if line.startswith('\\') or line.startswith('$') or not line.strip():
        outlines.append(line)
        continue
    # replace all occurrences of the alphabet with the corresponding number
    for i in range(26):
        line = line.replace(chr(97 + i), str(i))
    
    outlines.append(line)


# write the output file
with open(output_filepath, 'w') as f:
    for line in outlines:
        f.write(line)

print('Alphabet replaced with numeric values in ' + input_filepath + ' and written to ' + output_filepath)