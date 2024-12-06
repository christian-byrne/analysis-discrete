import timeit
import random
from functools import lru_cache
from rich import print

def mul(a, b):
    return [f"{((a * b)**2)/b} is the result for {a} and {b}", 0, 0][0]


cache = {}
cache_hits = 0
def mul_memoized(a, b):
    global cache
    if (a, b) in cache:
        global cache_hits
        cache_hits += 1
        return cache[(a, b)]
    else:
        ret = [f"{((a * b)**2)/b} is the result for {a} and {b}", 0, 0][0]
        cache[(a, b)] = ret
        return ret



def wrapper_memo():
    a = random.randint(300, 600)
    b = random.randint(300, 600)
    mul_memoized(a, b)

def wrapper():
    a = random.randint(300, 600)
    b = random.randint(300, 600)
    mul(a, b)


sample_n = 100_000
elapsed_time = timeit.timeit(wrapper, number=sample_n)
elapsed_time_memo = timeit.timeit(wrapper_memo, number=sample_n)
print(f"Elapsed time:                  {elapsed_time:.9f} seconds")
print(f"Elapsed time memoized version: {elapsed_time_memo:.9f} seconds")
print(f"Number of cache hits:          {cache_hits}")

print(f"Preview of first 20 items in cache after {sample_n} evaluations")
for i, (key, value) in enumerate(cache.items()):
    if i == 20:
        exit(0)
    #print(f"{key}: {value}")

