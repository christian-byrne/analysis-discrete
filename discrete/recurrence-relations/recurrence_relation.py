


def n_bitstrings(n):
  if isinstance(n, int):
    n = str(n) + ":"

  count = int(n.split(":")[0])
  cur_string = n.split(":")[1]
  if count == 0:
    return cur_string
  
  return n_bitstrings(str(count-1) + ":" + cur_string + "0") + "\n" + n_bitstrings(str(count-1) + ":" + cur_string + "1")



print(n_bitstrings(5))
print(len(n_bitstrings(5).split("\n")))