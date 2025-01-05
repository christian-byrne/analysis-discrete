

def f3(n):
  if n == 1:
    return 1
  
  return 1 + f3(n/3)

# print(f3(3))
# print(f3(27))
# print(f3(729))


def f4(n):
  if n == 1:
    return 1
  
  return (2 * f4(n/3)) + 4


print(f4(3))
print(f4(9))
print(f4(27))