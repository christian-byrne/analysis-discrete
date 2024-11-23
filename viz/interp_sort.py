
import math

def get_by_index(i):
  """Element found at probe index i is equal to (i**2)/2"""
  return (i**2)/2

def get_interp_sort_index(target, low, high):
  arr_low = get_by_index(low)
  if high == 999_999:
    arr_high = 499_999_000_000
  else:
    arr_high = get_by_index(high)


  fraction = (target - arr_low) / (arr_high - arr_low)
  fraction *= (high - low)
  fraction = math.floor(fraction) + low
  print("\n$$\n")
  print(f"{low} +" + "\\lfloor \\left( \\frac{" + f"{target} - {arr_low}" + "}{" + f"{arr_high} - {arr_low}" + "} \\times (" + str(high) + " - " + str(low) + ") \\right) \\rfloor =" +  f"{fraction}")
  print("\n$$\n")

  return fraction, math.floor(get_by_index(fraction))
  

# new_i, new_mid = get_interp_sort_index(8_675_309, 0, 999_999)

# print(f"Index: {new_i}, Value: {new_mid}")

# new_i, new_mid = get_interp_sort_index(8_675_309, 17, 999_999)

# print(f"Index: {new_i}, Value: {new_mid}")

new_i, new_mid = get_interp_sort_index(8_675_309, 34, 999_999)

print(f"Index: {new_i}, Value: {new_mid}")