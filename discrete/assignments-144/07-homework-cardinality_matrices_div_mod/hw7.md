## Section 2.5 - Question 3 

Determine whether each of these sets is countable or uncountable. For those that are countably infinite, exhibit a one-to-one correspondence between the set of positive integers and that set.

#### a)

> all bit strings not containing the bit 0

**Countable**

```python
def positive_int_to_bit_string(n: int) -> str: return "1" * (n - 1)
```


#### b)

> all positive rational numbers that cannot be written with denominators less than 4


**Countable**

```python
def rational_to_positive_int(numerator: int, denominator: int) -> int: 
    return .5 * (numerator + denominator) 
        * (numerator + denominator + 1) + denominator
```


#### c)

> the real numbers not containing 0 in their decimal representation

**Uncountable**


#### d)

> the real numbers containing only a finite number of 1s in their decimal representation

**Uncountable**


## Section 2.5 - Question 5 

> Show that a finite group of guests arriving at Hilbert’s fully occupied Grand Hotel can be given rooms without evicting any current guest.

The n guests can be given rooms 1 through n. Every guest currently in the hotel can be moved to the room number n greater than their current room number.

## Section 2.5 - Question 7 

> Suppose that Hilbert’s Grand Hotel is fully occupied on the day the hotel expands to a second building which also contains a countably infinite number of rooms. Show that the current guests can be spread out to fill every room of the two buildings of the hotel.

Define a function f that maps the rooms numbers in the first building to the original room numbers in the first building: f(n) = n // 2. Define a function g that maps the room numbers in the second building to the original room numbers in the first building: g(n) = n // 2 + 1. The current guests can be moved to the room number f(n) in the first building and the room number g(n) in the second building. 

## Section 2.5 - Question 11

Give an example of two uncountable sets A and B such that A ∩ B is

#### a)

> finite.

A = {x | x ∈ R, x > 0} 

B = {x | x ∈ R, x < 0}

A ∩ B = ∅.


#### b)

> countably infinite.


Let C = {x | x ∈ Z+, x > 0}

A = {x | x ∈ R, x < -5} ∪ C

A = {x | x ∈ R, -5 < x < 0} ∪ C

A ∩ B = {x | x ∈ Z+, x > 0}.




#### c)

> uncountable.

Let A = B = {x | x ∈ R, x > 0}.

A ∩ B = A = {x | x ∈ R, x > 0}.


## Section 2.5 - Question 15

> Show that if A and B are sets, A is uncountable, and A ⊆ B, then B is uncountable.

If A is uncountable, then there is no one-to-one correspondence between A and the positive integers. If A ⊆ B, then there is a one-to-one correspondence between A and a subset of B. Since there is no one-to-one correspondence between A and the positive integers, there is no one-to-one correspondence between B and the positive integers. Therefore, B is uncountable.


## Section 2.5 - Question 19

> Show that if A, B, C, and D are sets with |A| = |B| and |C| = |D|, then |A × C| = |B × D|.

The cardinality of the Cartesian product of two sets is the product of the cardinalities of the two sets. Since |A| = |B| and |C| = |D|, the cardinality of A × C = |A| * |C| = |B| * |C| = |B| |D| = the cardinality of B × D. 

## Section 2.5 - Question 23

> Show that if A is an infinite set, then it contains a countably infinite subset.

If it were the case that an infinite set A had a cardinality less than the cardinality of a countably infinite set, it would have to be the image of an infinite subset of the natural numbers. But any such infinite subset is an image of the whole set. As such, there is no infinite set with a cardinality less than the cardinality of the positive integers. Therefore, if A is an infinite set, then |A| ≥ |Z+|. Since |Z+| is countably infinite, A contains a countably infinite subset.


## Section 2.5 - Question 27

> Show that the union of a countable number of countable sets is countable.

- Create a sequence of the sets' elements
- Start by iterating over all the finite sets in the union and adding their elements to the sequence
- Then alternate between the countably infinite sets in the union, adding one element from each set to the sequence in each iteration. Because these infinite sets are countable, there is a 1-to-1 correspondence between their elements

## Section 2.5 - Question 29

> Show that the set of all finite bit strings is countable.


```python
def positive_int_to_bit_string(n: int) -> str:
    while n > 0:
        bit_string = str(n % 2) + bit_string
        n //= 2
    return bit_string
```

This function maps positive integers to bit strings, demonstrating that the set of all finite bit strings is countable.

## Section 2.5 - Question 33

> Use the Schröder-Bernstein theorem to show that (0, 1) and [0, 1] have the same cardinality.


Let f: (0, 1) -> [0, 1] be defined by f(x) = x/2 + 1/2. Let g: [0, 1] -> (0, 1) be defined by g(x) = x. The function f is a one-to-one correspondence between (0, 1) and [0, 1]. The function g is a one-to-one correspondence between [0, 1] and (0, 1). Therefore, (0, 1) and [0, 1] have the same cardinality.



## Section 2.6 - Question 1 

Let A = 

```
| 1 1 1 3 |
| 2 0 4 6 |
| 1 1 3 7 |
```

#### a)

> What size is A?

3 × 4

#### b)

> What is the third column of A?

[1, 4, 3]

#### c)

> What is the second row of A?

[2, 0, 4, 6]


#### d)

> What is the element of A in the (3, 2)th position?

1

#### e)

> What is A^t ?


```
| 1 2 1 |
| 1 0 1 |
| 1 4 3 |
| 3 6 7 |
```


## Section 2.6 - Question 3a 

Let A = 

```
| 2 1 |
| 3 2 |
```

Let B = 

```
| 0 4 |
| 1 3 |
```

AB =

```
| 1 11 |
| 2 18 |
```


## Section 2.6 - Question 11 

> What do we know about the sizes of the matrices A and B if both of the products AB and BA are defined?

If both AB and BA are defined, then the number of columns in A is equal to the number of rows in B, and the number of columns in B is equal to the number of rows in A. 


## Section 2.6 - Question 15 

Let A = 

```
| 1 1 |
| 0 1 |
```

When n is a positive integer, what is A^n?

```
| 1 n |
| 0 1 |
```


## Section 2.6 - Question 17a 

> Let A and B be two n × n matrices. Show that (A + B)^t = A^t + B^t.

- Adding matrices is finding the the sum of each term (a_ij + b_ij)
- In the case of (A + B)^t, we find each term as (a_ij + b_ij) then transpose the matrix such that the resulting matrix's terms are (a_ji + b_ji)
- In the case of A^t + B^t, we transpose each matrix first, so that matrix A's terms go from a_ij to a_ji, and matrix B's terms go from b_ij to b_ji. Then we add these, resulting in the matrix with terms (a_ji + b_ji)


## Section 2.6 - Question 19 

If A and B are n × n matrices with AB = BA = I_n, then B is called the inverse of A (this terminology is appropriate because such a matrix B is unique) and A is said to be invertible. The notation B = A^−1 denotes that B is the inverse of A.

Let A = 

```
| a b |
| c d |
```

Show that if A is invertible, then the inverse of A is 

```
| d/(ad - bc) -b/(ad - bc) |
| -c/(ad - bc) a/(ad - bc) |
```

Proof:

- (ad - bc) is the determinant of A
- The inverse of A is 1/det(A) times the adjugate of A
- (a * d/(ad - bc) + b * -c/(ad - bc)) = 1
- (c * -b/(ad - bc) + d * a/(ad - bc)) = 1
- (a * -c/(ad - bc) + b * a/(ad - bc)) = 0
- (c * d/(ad - bc) + d * -b/(ad - bc)) = 0



## Section 2.6 - Question 27 

**a)** A v B = 

```
| 1 1 1 |
| 1 1 1 |
| 1 0 1 |
```

**b)** A ^ 2 = 

```
| 0 0 1 |
| 1 0 0 |
| 0 0 0 |
```

**c)** Element-wise multiplication of A and B =

```
| 1 1 1 |
| 1 1 1 |
| 1 0 1 |
```

## Section 2.6 - Question 29 

**a)**

A^2 = 

```
| 1 1 1 |
| 1 1 0 |
| 1 0 1 |
```

**b)**

A^3 = 

```
| 1 1 1 |
| 1 1 1 |
| 1 1 1 |
```

**c)**

A v A^2 v A^3 = 

```
| 1 1 1 |
| 1 1 1 |
| 1 1 1 |
```


## Section 2.6 - Question 33 

We will establish distributive laws of the meet over the join operation in this exercise. Let A, B, and C be m × n zero–one matrices. Show that

#### a)

> A ∨ (B ∧ C) = (A ∨ B) ∧ (A ∨ C).

A ∨ (B ∧ C) = A ∨ [b_ij AND c_ij] = [a_ij OR (b_ij AND c_ij)]

#### b)

> A ∧ (B ∨ C) = (A ∧ B) ∨ (A ∧ C).


A ∧ (B ∨ C) = A ∧ [b_ij OR c_ij] = [a_ij AND (b_ij OR c_ij)]



## Section 4.1 - Question 1 

Does 17 divide each of these numbers?

#### a)

> 68

Yes, 17 divides 68 because 68 = 17 * 4.

#### b)

> 84

No

#### c)

> 357

Yes

#### d)

> 1001

No



## Section 4.1 - Question 7 

> Show that if a, b, and c are integers, where a ≠ 0 and c ≠ 0, such that ac | bc, then a | b.

- If ac | bc, then there exists an integer k such that bc = k(ac).
- b = k(a)
- Thus, there is an integer k such that b = ka.
- Therefore, a | b.


## Section 4.1 - Question 9 

- If a divides b, then there exists an integer j such that b = aj.
- If a is even, then aj is even, and b is even.
- Given that a can only be odd or even, by proving that when a is odd, then b is even, we have proved that in all cases, a is odd or b is even -- i.e., at least one of those premises is true


## Section 4.1 - Question 13 

What are the quotient and remainder when

a) 19 / 7 = 2 remainder 5

b) -111 / 11 = -11 remainder 10

c) 789 / 23 = 34 remainder 7

d) 1001 / 13 = 77 remainder 0

e) 0 / 19 = 0 remainder 0

f) 3 / 5 = 0 remainder 3

g) -1 / 3 = -1 remainder 2

h) 4 / 1 = 4 remainder 0




## Section 4.1 - Question 15 


What time does a 12-hour clock read

#### a)

> 80 hours after it reads 11:00?

(80 - 1) % 12 = 7

7:00

#### b)

> 40 hours before it reads 12:00?

40 % 12 = 4

40 - (4 * 12) = 40 - 48 = -8

8:00

#### c)

> 100 hours after it reads 6:00?

(100 - 6) % 12 = 10

10:00

## Section 4.1 - Question 17 


Suppose that a and b are integers, a ≡ 4 (mod 13), and b ≡ 9 (mod 13). Find the integer c with 0 ≤ c ≤ 12 such that

a)c ≡ 9a (mod 13): 9 * 4 = 36 % 13 = 10

b)c ≡ 11b (mod 13): 11 * 9 = 99 % 13 = 8

c)c ≡ a + b (mod13): 4 + 9 = 13 % 13 = 0

d)c ≡ 2a + 3b (mod 13): 2 * 4 + 3 * 9 = 8 + 27 = 35 % 13 = 9

e)c ≡ a^2 + b^2 (mod 13): 4^2 + 9^2 = 16 + 81 = 97 % 13 = 6

f)c ≡ a^3 − b^3 (mod 13): 4^3 - 9^3 = 64 - 729 = -665 % 13 = 11


## Section 4.1 - Question 29 

Find a div m and a mod m when

#### a)

> a = 228, m = 119.

228 // 119 = 1

228 % 119 = 109


#### b)

> a = 9009, m = 223.

9009 // 223 = 40

9009 % 223 = 9


#### c)

> a = −10101, m = 333.

-10101 // 333 = -31

-10101 % 333 = 0



#### d)

> a = −765432, m = 38271.


-765432 // 38271 = -20

-765432 % 38271 = 0



## Section 4.1 - Question 35 

Decide whether each of these integers is congruent to 5 modulo 17.

#### a)

80 = 5 + k * 17, 75 = k * 17

k is not an integer, so 80 is not congruent to 5 modulo 17.


#### b)

103 = 5 + k * 17, 98 = k * 17

k is not an integer, so 103 is not congruent to 5 modulo 17.



#### c)

−29 = 5 + k * 17, -34 = k * 17

k is an integer, so −29 is congruent to 5 modulo 17.



#### d)

−122 = 5 + k * 17, -127 = k * 17

k is not an integer, so −122 is not congruent to 5 modulo 17.



## Section 4.1 - Question 37 


Find each of these values.

#### a)

> (−133 mod 23 + 261 mod 23) mod 23

```
>>> (-133 % 23 + 261 % 23) % 23
13
```

#### b)

> (457 mod 23 · 182 mod 23) mod 23

```
>>> (457 % 23 * 182 % 23) % 23
6
```



## Section 4.1 - Question 43


Find counterexamples to each of these statements about congruences.

#### a)

> If ac ≡ bc (mod m), where a, b, c, and m are integers with m ≥ 2, then a ≡ b (mod m).

When m and c are the same number, then ac and bc mod m will be the same, but a and b may not be the same number. For example, 2 * 3 ≡ 4 * 3 (mod 3), but 2 ≡ 4 (mod 3) is false.

#### b)

> If a ≡ b (mod m) and c ≡ d (mod m), where a, b, c, d, and m are integers with c and d positive and m ≥ 2, then a^c ≡ b^d (mod m).

- Set c and d as even/odd multiples of an odd number m 
  - E.g., c = 21, d = 24, m = 3
- Let a and b be any integers that are congruent modulo m
  - E.g., a = 11, b = 5
- a^c % m = 11^21 % 3 = 2
- b^d % m = 5^24 % 3 = 1
