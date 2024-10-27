import itertools
import struct

def find_factors_pairs(n):
    result = {}

    for num in range(1, n + 1):
        # Generate factor pairs (p, s) for the current number
        factors = [(p, s) for p in range(2, num + 1) if num % p == 0 for s in range(2, num + 1) if p * s == num]
        
        # Filter pairs based on conditions: p >= 2 * (s - 1)^2 and s divides p
        filtered_factors = [
            (p, s) for p, s in factors 
            if p >= 2 * (s - 1) ** 2 and p % s == 0
        ]
        
        # Sort by the first element (p) in ascending order
        filtered_factors.sort(key=lambda x: x[0])
        
        # Select the first pair if available and add to result dictionary
        if filtered_factors:
            result[num] = filtered_factors[0]

    return result


TOTAL = 65536
x = find_factors_pairs(TOTAL)

final = {
    0: (1, 0, 0)
}
def find_closest_key(num, pairs):
    closest = None
    cur = None
    for key in pairs:
        
        if key <= num:
            distance = abs(key - num)
            if closest is None or distance < closest:
              closest = distance 
              cur = key
        
    if cur is None:
        final[num] = (1, num, 0)
        return

    final[num] = pairs[cur]
    final[num] = (final[num][0], final[num][1], abs(closest))


for i in range(1, TOTAL):
    find_closest_key(i, x)

assert len(final) == TOTAL
assert all(v >= 0 for _, _, v in final.values())
assert all(v <= TOTAL/2 and v2 <= TOTAL/2 for v, v2, _ in final.values())

# Write the data to a binary file
with open("../lookupTable.bin", "wb") as file:
    # Write the size of the lookup table
    # file.write(struct.pack("I", TOTAL))  # Number of entries in the table

    for key in range(TOTAL):
        # Ensure we write exactly 8192 entries, filling any missing keys if necessary
        p, s, closest = final.get(key, (1, 0, 0))
        file.write(struct.pack(">iii", p, s, closest))    

print("Lookup table successfully written to 'lookupTable.bin'")
