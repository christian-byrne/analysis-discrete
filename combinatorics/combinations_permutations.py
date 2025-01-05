
from math import factorial

def combinations(n: int, r: int) -> int:
    """
    This function calculates the number of combinations of `n` things taken `r` at a time.
    """
    res = factorial(n) / (factorial(r) * factorial(n - r))
    # assert isinstance(res, int)
    print(f"Combinations of {n} things taken {r} at a time: {res}")


combinations(4, 3)
combinations(4, 2)