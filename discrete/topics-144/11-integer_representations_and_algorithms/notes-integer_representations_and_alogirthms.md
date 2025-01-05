
# Notes - Chapter 4.2 - Integer Representations and Algorithms

- Integers can be expressed using any integer greater than 1 as a base
- The most common bases are:
  - Base 2 (binary)
  - Base 8 (octal)
  - Base 10 (decimal)
  - Base 16 (hexadecimal)
- Theorem:
    - Let b be an integer greater than $1$
    - Let $n$ be an integer
    - Then $n$ can be expressed as:
    - $n = a_k b^k + a_{k-1} b^{k-1} + \ldots + a_1 b + a_0$
    - where $a_k, a_{k-1}, \ldots, a_1, a_0$ are integers in the range $0 \leq a_i < b$
    - $a_k$ is the most significant digit and $a_0$ is the least significant digit
    - $k$ is the number of digits in the base $b$ representation of $n$
- The base $b$ expansion/representation of $n$ is:
  - $n = (a_k a_{k-1} \ldots a_1 a_0)_b$
  - where $a_i$ is the $i$-th digit in the base $b$ representation of $n$
  - For example:
    - The base $8$ representation of $165$ is $(2 4 5)_8$
    - $(245)_8 = 2 \times 8^2 + 4 \times 8^1 + 5 \times 8^0 = 165$
- The base $b$ representation of $n$ is unique
- Typically, a subscript of 10 is ommited for base 10 expansions of integers because base 10 (decimal expansions) are the most common

## Binary Expansions

- Each digit in a binary expansion is called a bit
- Thus, a binary expansion of an integer is just a bit string
- The base $2$ representation of $n$ is:
  - $n = (a_k a_{k-1} \ldots a_1 a_0)_2$
  - where $a_i$ is the $i$-th bit in the binary representation of $n$
  - For example:
    - The binary representation of $165$ is $(1 0 0 1 0 0 1 0 1)_2$
    - $(10010010)_2 = 1 \times 2^7 + 0 \times 2^6 + 0 \times 2^5 + 1 \times 2^4 + 0 \times 2^3 + 0 \times 2^2 + 1 \times 2^1 + 0 \times 2^0 = 165$
- Binary expansions (and related expansinos that are variants of binary expansions) are used in computer systems to represent and do arithmetic on integers

## Hexadecimal Expansions

- **Bytes**, which are groups of $8$ bits, are often represented using hexadecimal expansions because they can be represented by two hexadecimal digits


## Base Conversion

- Conversion between binary and octal and between binary and hexadecimal expansions is extremely easy because each octal digit corresponds to a block of three binary digits and each hexadecimal digit corresponds to a block of four binary digits,

### Algorithm - From Integer to Base $b$ Expansion

To construct the base $b$ expansion of an integer $n$ from its base $a$ expansion:

1. Convert the base $a$ expansion of $n$ to an integer
2. Divide the integer by $b$ to get the remainder and the quotient
   1. $n = q_1 b + r_1$, where $0 \leq r_1 < b$
3. Repeat step $2$ with the quotient to get the next remainder and quotient
   1. $q_1 = q_2 b + r_2$, where $0 \leq r_2 < b$
   2. $q_2 = q_3 b + r_3$, where $0 \leq r_3 < b$
   3. $\ldots$
4. Continue until the quotient is $0$
5. The base $b$ expansion of $n$ is the sequence of remainders in reverse order
   1. $n = (r_k r_{k-1} \ldots r_1 r_0)_b$

```python
def base_conversion(integer: int, base: str):
    result = ''
    while integer > 0:
        # Get the remainder
        remainder = integer % base
        
        # Get the quotient
        quotient = integer // base

        # Update the integer to be the quotient
        integer = quotient
        
        # Prepend the remainder to the result
        result = str(remainder) + result

    return result
```

### Algorithm - From Base $b$ Expansion to Integer

```python
def base_conversion(base_expansion: str, base: str):
    result = 0
    for i, digit in enumerate(reversed(base_expansion)):
        result += int(digit) * base ** i
    return result
```

## Algorithms for Integer Operations

### Addition

```python
def add(number1: str, number2: str, base: int) -> str:
    digits = []
    carry = 0

    for i in range(max(len(number1), len(number2))):
        
        # Get the digits of the numbers at the i-th position
        digit1 = int(number1[-(i + 1)]) if i < len(number1) else 0
        digit2 = int(number2[-(i + 1)]) if i < len(number2) else 0

        # If the current digit sum is less than the base, 
        #   then it remains unchanged and there is no carry
        # If the current digit sum is greater than or equal to the base, 
        #   then the digit is the remainder of the sum and the base and 
        #   the carry is the quotient of the sum and the base 
        #   (because every 1 increment in the NEXT digit location will be like an 
        #    entire added base in this location)
        digits.prepend((digit1 + digit2 + carry) % base)
        carry = (digit1 + digit2 + carry) // base

    # If the leading digit has a carry, prepend it to the digits 
    #   (going up a digit from the max digit of the two inputs)
    if carry > 0:
        digits.prepend(carry)
    
    return ''.join(map(str, digits))
```


### Subtraction

```python
def subtract(number1: str, number2: str, base: int) -> str:
    digits = []
    borrow = 0

    for i in range(max(len(number1), len(number2))):
        
        # Get the digits of the numbers at the i-th position
        digit1 = int(number1[-(i + 1)]) if i < len(number1) else 0
        digit2 = int(number2[-(i + 1)]) if i < len(number2) else 0

        # If the current digit difference is greater than or equal to 0, 
        #   then it remains unchanged and there is no borrow
        # If the current digit difference is less than 0, 
        #   then the digit is the sum of the base and the difference and 
        #   the borrow is 1 (because every 1 decrement in the NEXT digit location will be like an 
        #    entire base subtracted in this location)
        digits.prepend((digit1 - digit2 - borrow) % base)
        borrow = 1 if digit1 - digit2 - borrow < 0 else 0

    return ''.join(map(str, digits))
```

### Multiplication

```python
def multiply(number1: str, number2: str, base: int) -> str:
    # Convert the numbers to binary expansions
    number1 = base_conversion(number1, 2)
    number2 = base_conversion(number2, 2)

    # Initialize the result
    result = []

    for i in range(len(number1)):
        if number1[-(i + 1)] == "1":
            # If the i-th bit of number1 is 1, then add number2 shifted by i bits
            result.prepend(number2 + "0" * i)
        else:
            # If the i-th bit of number1 is 0, then add 0
            result.prepend("0" * len(number2))

    result_sum = ""
    # Sum the results using the add function from above
    for i in range(1, len(result)):
        result_sum = add(result_sum, result[i], 2)

    return result_sum
```

#### Complexity

To add the integers abj from j = 0 to j = n − 1 requires the addition of an n-bit integer, an (n + 1)-bit integer, …, and a (2n)-bit integer. We know from Example 9 that each of these additions requires O(n) additions of bits. Consequently, a total of O(n2) additions of bits are required for all n additions.

Surprisingly, there are more efficient algorithms than the conventional algorithm for multiplying integers. One such algorithm, which uses O(n1.585) bit operations to multiply n-bit numbers, will be described in Section 8.3.


### Div and Mod


```python
def div_and_mod(divisor: str, dividend: str, base: int) -> Tuple[str, str]:
    # Initialize the quotient and remainder
    quotient = "0"
    remainder = dividend

    while len(remainder) >= len(divisor):
        # Find the most significant digit of the divisor
        divisor_shift = len(remainder) - len(divisor)
        divisor_shifted = divisor + "0" * divisor_shift

        # Find the most significant digit of the quotient
        quotient_shift = len(remainder) - len(divisor)
        quotient_shifted = "1" + "0" * (len(remainder) - len(divisor) - 1)

        # If the divisor is less than or equal to the remainder, 
        #   then subtract the divisor from the remainder and 
        #   add the quotient_shifted to the quotient
        if remainder >= divisor_shifted:
            remainder = subtract(remainder, divisor_shifted, 2)
            quotient = add(quotient, quotient_shifted, 2)
        else:
            # If the divisor is greater than the remainder, 
            #   then shift the divisor and quotient to the right
            divisor_shift -= 1
            divisor_shifted = divisor + "0" * divisor_shift
            quotient_shifted = "1" + "0" * (len(remainder) - len(divisor) - 1)
```


```python
def div_and_mod(divisor: str, dividend: str, base: int) -> Tuple[str, str]:
    # Convert the numbers to binary expansions
    divisor = base_conversion(divisor, 2)
    dividend = base_conversion(dividend, 2)

    # Initialize the quotient and remainder
    quotient = "0"
    remainder = dividend

    for i in range(len(dividend) - len(divisor), -1, -1):
        if remainder >= divisor << i:
            remainder = subtract(remainder, divisor << i, 2)
            quotient = add(quotient, "1" << i, 2)

    return base_conversion(quotient, base), base_conversion(remainder, base)
```

## Modular Exponentiation

- In cryptography it is important to be able to find b^n mod m efficiently without using an excessive amount of memory, where b, n, and m are large integers. 
- It is impractical to first compute bn and then find its remainder when divided by m, because bn can be a huge number and we will need a huge amount of computer memory to store such numbers. 
- Instead, we can avoid time and memory problems by using an algorithm that employs the binary expansion of the exponent n

```python
def modular_exponentiation(base_num1: str, exponent_num1: str, modulus: str, original_base: int) -> str:
    # Convert the numbers to binary expansions
    base_num1 = base_conversion(base_num1, 2)
    exponent = base_conversion(exponent, 2)
    modulus = base_conversion(modulus, 2)

    result = "1"
    power = base_num1 % modulus

    for i in range(len(exponent)):
        # If the i-th bit of the exponent is 1, then multiply the result by the base
        if exponent[-(i + 1)] == "1":
            result = multiply(result, power, 2) % modulus
        
        # Square the base
        power = multiply(power, power, 2) % modulus

    return base_conversion(result, original_base)
```