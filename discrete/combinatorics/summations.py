
from math import factorial

def summation(i: int, n: int, func: callable) -> float:
    """
    This function calculates the sum of the function `func` from `i` to `n`.
    """
    return sum(func(x) for x in range(i, n + 1))


def combinations(r: int) -> int:
    """
    This function calculates the number of combinations of `n` things taken `r` at a time.
    """
    n = 8
    return factorial(n) / (factorial(r) * factorial(n - r))


x = summation(0, 8, combinations)

print(x)

combinations_ = 1
for i in range(9):
    term = factorial(8) / (factorial(max(i -1, 0)) * factorial(8 - max(i - 1, 0)))
    # term = factorial(8) / (factorial(8 - i))
    print(f"i = {i}, 8 - i = {8 - i}")
    print(f"Term: {term}")
    combinations_ *= term
    print(f"Combinations: {combinations_}")


print(combinations_)
    