

def how_many_consecutive_zeros(length: int):
  """
  Returns the number of possible bit strings of length `length` that at least 1 pair of consecutive zeros.
  """
  return 2 ** length - 2 ** (length - 1)



def bit_string_perms(length: int) -> list:
  """
  Returns the list of all possible bit strings of length `length`.
  """
  res = [bin(i)[2:].zfill(length) for i in range(2 ** length)]
  assert len(res) == 2 ** length
  print(f"Number of bit strings of length {length}: {len(res)}")
  return res

def count_all_consecutive_zeros(length: int) -> int:
  """
  Returns the number of possible bit strings of length `length` that have at least 1 pair of consecutive zeros.
  """
  res = 0
  for bit_string in bit_string_perms(length):
    if "000" in bit_string:
      res += 1
  
  print(res)
  return res



if __name__ == "__main__":

  length = 7

  count_all_consecutive_zeros(length)
  