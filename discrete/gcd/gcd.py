from itertools import permutations
from sympy import simplify

def gcd(a: int, b: int) -> int:
    if b == 0:
        return a
    return gcd(b, a % b)



def is_set_relative_prime(int_set: set) -> bool:
    perms = list(permutations(int_set, 2))
    perms = list(set(tuple(sorted(perm)) for perm in perms))
    print(f"\nPermutations (n = {len(perms)})")
    print(perms)
    for index, (a, b) in enumerate(perms):
        # print(f"Permutation {index}: ({a}, {b})")
        if gcd(a, b) != 1:
            # print(f"gcd({a}, {b}) != 1")
            print(f"gcd({a}, {b}) = {gcd(a, b)}")
            return False
        else:
            print(f"gcd({a}, {b}) = 1")
    
    return True

# a)21, 34, 55  

# b)14, 17, 85

# c)25, 41, 49, 64

# d)17, 18, 19, 23

def test_int_sets_rel_prime():
    set_a = {21, 34, 55}
    set_b = {14, 17, 85}
    set_c = {25, 41, 49, 64}
    set_d = {17, 18, 19, 23}

    print(f"\nSet A: {set_a}")
    print(f"Is set A relatively prime? {is_set_relative_prime(set_a)}")
    print()
    print(f"\nSet B: {set_b}")
    print(f"Is set B relatively prime? {is_set_relative_prime(set_b)}")
    print()
    print(f"\nSet C: {set_c}")
    print(f"Is set C relatively prime? {is_set_relative_prime(set_c)}")
    print()
    print(f"\nSet D: {set_d}")
    print(f"Is set D relatively prime? {is_set_relative_prime(set_d)}")
    print()

def find_gcd_prime_factorizations(a: str, b: str):
    def string_to_int(s: str) -> int:
        factors = s.split("*")
        result = 1
        for factor in factors:
            factor = factor.strip()
            if "^" in factor:
                base, exponent = factor.split("^")
                result *= int(base) ** int(exponent)
            else:
                result *= int(factor)
        return result

    print(f"gcd(${a}$, ${b}$) = ${gcd(string_to_int(a), string_to_int(b))}$".replace("*", " \\cdot "))

# a)22 · 33 · 55, 25 · 33 · 52

# b)2 · 3 · 5 · 7 · 11 · 13, 211 · 39 · 11 · 1714

# c)17, 1717

# d)22 · 7, 53 · 13

# e)0, 5

# f)2 · 3 · 5 · 7, 2 · 3 · 5 · 7

def test_gcd_prime_factorizations():
    prime_factorization_pair_1 = ("2^2 * 3^3 * 5^5", "2^5 * 3^3 * 5^2")
    prime_factorization_pair_2 = ("2 * 3 * 5 * 7 * 11 * 13", "2^11 * 3^9 * 11 * 17^14")
    prime_factorization_pair_3 = ("17", "17^17")
    prime_factorization_pair_4 = ("2^2 * 7", "5^3 * 13")
    prime_factorization_pair_5 = ("0", "5")
    prime_factorization_pair_6 = ("2 * 3 * 5 * 7", "2 * 3 * 5 * 7")

    for index, (a, b) in enumerate([prime_factorization_pair_1, prime_factorization_pair_2, prime_factorization_pair_3, prime_factorization_pair_4, prime_factorization_pair_5, prime_factorization_pair_6]):
        print(f"\nPair {index + 1}: $({a}, {b})$".replace("*", " \\cdot "))
        find_gcd_prime_factorizations(a, b)


def test_gcd_pairs():
    # a)gcd(12, 18).
    # b)gcd(111, 201).
    # c)gcd(1001, 1331).
    # d)gcd(12345, 54321).
    # e)gcd(1000, 5040).
    # f)gcd(9888, 6060).
    print(f"\nPair A: gcd(12, 18) = {gcd(12, 18)}")
    print(f"Pair B: gcd(111, 201) = {gcd(111, 201)}")
    print(f"Pair C: gcd(1001, 1331) = {gcd(1001, 1331)}")
    print(f"Pair D: gcd(12345, 54321) = {gcd(12345, 54321)}")
    print(f"Pair E: gcd(1000, 5040) = {gcd(1000, 5040)}")
    print(f"Pair F: gcd(9888, 6060) = {gcd(9888, 6060)}")
    print()


def steps_in_euclidean_algorithm(a: int, b: int):
    steps = []
    while b != 0:
        steps.append(
            f"{a} % {b} = {a % b}."
        )
        a, b = b, a % b
    return steps

def test_euclidean_algorithm():
    # gcd(21, 34)

    steps = steps_in_euclidean_algorithm(34, 21)
    for index, step in enumerate(steps):
        print(f"{index + 1}. {step}")
    print(f"\nTotal divisions required: {len(steps)}")


def linear_combination(a: int, b: int):
    steps = []
    x = 0
    y = 1
    x_prev = 1
    y_prev = 0
    while b != 0:
        steps.append(
            f"${a} = {a // b} \\cdot {b} + {a % b}$"
        )
        a, b = b, a % b
        if b == 0:
            break
        steps.append(
            f"\t1. $x = {x_prev} - (({a} // {b}) \\cdot {x}) = {x_prev - (a // b) * x}$"
        )
        x, x_prev = x_prev - (a // b) * x, x
        steps.append(
            f"\t2. $y = {y_prev} - (({a} // {b}) \\cdot {y}) = {y_prev - (a // b) * y}$"
        )
        y, y_prev = y_prev - (a // b) * y, y
    return steps, x_prev, y_prev

def test_linear_combination():
    int1 = 3454
    int2 = 4666

    steps, x, y = linear_combination(int1, int2)
    index = 1
    for i, step in enumerate(steps):
        if step.startswith("\t") or step.startswith("  2."):
            print(f"{step}")
        else:
            print(f"{index}. {step}")
            index += 1

    print(f"\nGCD: {gcd(int1, int2)}")
    if int1 * x + int2 * y == gcd(int1, int2):

        print(f"\nLinear combination: ${x} \\cdot {int1} + {y} \\cdot {int2} = {int1 * x + int2 * y}$")
    else:
        print(f"\nLinear combination: ${x} \\cdot {int2} + {y} \\cdot {int1} = {int2 * x + int1 * y}$")
test_linear_combination()