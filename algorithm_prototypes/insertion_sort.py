import big_o

# Define the bubble_sort function
def insertion_sort(li):
  ret = []
  for element in li:
    i = 0
    while li[i] < element:
      i += 1
    ret.insert(i, element)

  return ret

from collections import deque

def insertion_sort_dequeue(li):
  ret = deque()
  for element in li:
    added = False
    for _ in ret:
      cur = ret.pop()
      if not added and element < cur:
        ret.appendleft(element)
        added = True
      ret.appendleft(cur)


# Use a smaller input generator with reduced size
input_generator = lambda n: big_o.datagen.integers(n, 0, 100)

# Set a lower maximum size and fewer measures to speed things up
best, others = big_o.big_o(
    insertion_sort_dequeue, 
    input_generator, 
    # n_repeats=5,  # Reduce repetitions to speed up benchmarking
    max_n=10000  # Reduce max input size to keep things fast
)

# Print the best fitting time complexity
print(best)

# RESULT using list: 
#   Quadratic: time = 0.0012 + 2.3E-10*n^2 (sec)

# RESULT using deque: 
#   Linear: time = -0.00024 + 1.3E-07*n (sec)


