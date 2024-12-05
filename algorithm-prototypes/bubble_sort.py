import big_o

# Define the bubble_sort function
def bubble_sort(li):
    for i in range(len(li)):
        for j in range(len(li) - i - 1):
            if li[j] > li[j + 1]:
                li[j], li[j + 1] = li[j + 1], li[j]
    return li

# Use a smaller input generator with reduced size
input_generator = lambda n: big_o.datagen.integers(n, 0, 100)

# Set a lower maximum size and fewer measures to speed things up
best, others = big_o.big_o(
    bubble_sort, 
    input_generator, 
    # n_repeats=5,  # Reduce repetitions to speed up benchmarking
    max_n=1000  # Reduce max input size to keep things fast
)

# Print the best fitting time complexity
print(best)

# RESULT: Quadratic: time = -0.00015 + 5.9E-08*n^2 (sec)
