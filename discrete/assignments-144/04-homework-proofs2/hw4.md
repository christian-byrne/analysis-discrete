
## Section 1.7 - Question 19 

#### a

**Proof by Contradiction**:

- Assume the the negation of the conclusion is false and the hypothesis is true.
  - That is, assume `n^3 + 5` is odd and n is odd.
- if n is odd, then it can be written as `2k + 1` for some integer k.
- Then `n^3 + 5 = (2k + 1)^3 + 5 = 8k^3 + 12k^2 + 6k + 6` = `2(4k^3 + 6k^2 + 3k + 3)`.
- However, this is a contradiction because `n^3 + 5` is odd and `2(4k^3 + 6k^2 + 3k + 3)` is even.
- Therefore, the negation of the conclusion is false and the original statement is true.
- Therefore, the original statement is true.


#### b

**Proof by Contraposition**:

- Assume the contrapositive: if n is odd, then n is not an integer or `n^3 + 5` is even.
- If n is odd, then it can be written as `2k + 1` for some integer k.
- Then `n^3 + 5 = (2k + 1)^3 + 5 = 8k^3 + 12k^2 + 6k + 6` = `2(4k^3 + 6k^2 + 3k + 3)`.
- Since `4k^3 + 6k^2 + 3k + 3` is an integer, `n^3 + 5` is even.
- Therefore, the contrapositive is true and the original statement is true.

## Section 1.7 - Question 23 

`P(n)`: “If a and b are positive real numbers, then (a + b)n ≥ an + bn.” Prove that P(1) is true. What kind of proof did you use?

`P(1)`: “If a and b are positive real numbers, then (a + b)1 ≥ a1 + b1.”

**Direct Proof**:

- Assume a and b are positive real numbers.
- Then `(a + b)1 = a + b` and `a1 + b1 = a + b` by way of the identity property of multiplication.
- Since `a + b = a + b`, the original statement is true.

## Section 1.7 - Question 29

Prove that if n is a positive integer, then n is odd if and only if 5n + 6 is odd.

- Part 1: Prove that if n is odd, then 5n + 6 is odd.
  - Assume n is odd.
  - Then n can be written as `2k + 1` for some integer k.
  - Then `5n + 6 = 5(2k + 1) + 6 = 10k + 5 + 6 = 10k + 11 = 2(5k + 5) + 1`.
  - Since `5k + 5` is an integer, `5n + 6` is odd.
  - Therefore, if n is odd, then 5n + 6 is odd.
- Part 2: Prove that if 5n + 6 is odd, then n is odd.
    - Assume n is odd and 5n + 6 is even.
    - Then n can be written as `2k + 1` for some integer k.
    - Then `5n + 6 = 5(2k + 1) + 6 = 10k + 5 + 6 = 10k + 11 = 2(5k + 5) + 1`.
    - However, this is a contradiction because `5n + 6` is odd and `2(5k + 5) + 1` is even.

## Section 1.7 - Question 33

Show that these statements about the integer x are equivalent:

- (i) ∃x + 2 is even
  - if x + 2 is even, then x + 2 = 2k for some integer k.
  - Then x = 2k - 2 = 2(k - 1)
  - Since k - 1 is an integer, 2(k - 1) is even.
  - Therefore, if x + 2 is even, then x is even.
  - Since x + 2 is even, then x is even.
- (ii) x + 5 is odd
  - if x + 5 is odd, then x + 5 = 2k + 1 for some integer k.
  - Then x = 2k + 1 - 5 = 2k - 4 = 2(k - 2)
  - Since k - 2 is an integer, 2(k - 2) is even.
  - Therefore, if x + 5 is odd, then x is even.
  - Since x + 5 is odd, then x is even.
- (iii) x^2 is even.
  - Assume the contrapositive: if x is odd, then x^2 is odd.
  - If x is odd, then x = 2k + 1 for some integer k.
  - Then x^2 = (2k + 1)^2 = 4k^2 + 4k + 1 = 2(2k^2 + 2k) + 1.
  - Since `2k^2 + 2k` is an integer, `x^2` is odd.
  - Therefore, if x is odd, then x^2 is odd.
  - Since x^2 is even, then x is even.

Therefore, all three statements are stating that x is even. 


## Section 1.7 - Question 43

If these four statements are equivalent, then "n is even" &harr; "n + 1 is odd" &harr; "3n + 1 is odd" &harr; "3n is even" 

 - "n + 1 is odd" &rarr; "n is even"
    - Assume the contrapositive: if n is odd, then n + 1 is even.
    - If n is odd, then n = 2k + 1 for some integer k.
    - Then n + 1 = 2k + 1 + 1 = 2k + 2 = 2(k + 1).
    - Since k + 1 is an integer, 2(k + 1) is even.
    - Therefore, if n is odd, then n + 1 is even.
    - Therefore, if n + 1 is odd, then n is even.
  - "n is even" &rarr; "n + 1 is odd"
    - if n is even, then n = 2k for some integer k.
    - Then n + 1 = 2k + 1.
    - Since k is an integer, 2k + 1 is odd.
    - Therefore, if n is even, then n + 1 is odd.
 - "3n + 1 is odd" &rarr; "n is even".
    - Assume the contrapositive: if n is odd, then 3n + 1 is even.
    - If n is odd, then n = 2k + 1 for some integer k.
    - Then 3n + 1 = 3(2k + 1) + 1 = 6k + 3 + 1 = 6k + 4 = 2(3k + 2).
    - 2(3k + 2) is even since `3k + 2` is an integer.
    - Therefore, if n is odd, then 3n + 1 is even.
    - Therefore, if 3n + 1 is odd, then n is even.
 - "n is even" &rarr; "3n + 1 is odd".
    - if n is even, then n = 2k for some integer k.
    - Then 3n + 1 = 3(2k) + 1 = 6k + 1 = 2(3k) + 1.
    - Since 3k is an integer, 2(3k) is even.
    - Therefore, if n is even, then 3n + 1 is odd.
 - "3n is even" &rarr; "n is even".
    - Assume the contrapositive: if n is odd, then 3n is odd.
    - If n is odd, then n = 2k + 1 for some integer k.
    - Then 3n = 3(2k + 1) = 6k + 3 = 2(3k + 1) + 1.
    - Since `3k + 1` is an integer, `2(3k + 1)` is even. and `2(3k + 1) + 1` is odd.
 - "n is even" &rarr; "3n is even".
    - if n is even, then n = 2k for some integer k.
    - Then 3n = 3(2k) = 6k = 2(3k).
    - Since 3k is an integer, 2(3k) is even.
    - Therefore, if n is even, then 3n is even.


**Conclusion**: Since all four statements are biconditional, they are logically equivalent.


## Section 1.8 - Question 5

Prove that if x and y are real numbers, then max(x, y) + min(x, y) = x + y. [Hint: Use a proof by cases, with the two cases corresponding to x ≥ y and x < y, respectively.]

- Case 1: x ≥ y
  - Then max(x, y) = x and min(x, y) = y.
  - Then max(x, y) + min(x, y) = x + y.
  - Therefore, if x ≥ y, then max(x, y) + min(x, y) = x + y.
- Case 2: x < y
  - Then max(x, y) = y and min(x, y) = x.
  - Then max(x, y) + min(x, y) = y + x.
  - Therefore, if x < y, then max(x, y) + min(x, y) = x + y.

## Section 1.8 - Question 9

Prove the triangle inequality, which states that if x and y are real numbers, then | x| + | y| ≥ | x + y| (where | x| represents the absolute value of x, which equals x if x ≥ 0 and equals −x if x < 0).

- Case 1: x ≥ 0 and y ≥ 0
  - Then | x| = x and | y| = y.
  - Then | x| + | y| = x + y.
  - Then | x + y| = x + y.
  - Therefore, if x ≥ 0 and y ≥ 0, then | x| + | y| = | x + y|.
- Case 2: x < 0 and y < 0
  - Then | x| = −x and | y| = −y.
  - Then | x| + | y| = −x + (−y) = −(x + y).
  - Then | x + y| = −(x + y).
  - Therefore, if x < 0 and y < 0, then | x| + | y| = | x + y|.
- Case 3: x ≥ 0 ⊻ y < 0
  - Absolute value of a positive number plus a negative number is always less than or equal to the positive number.
  - On the other hand, the sum of two numbers that must be positive is always greater than or equal to either of the numbers.

## Section 1.8 - Question 19

Suppose that a and b are odd integers with a ≠ b. Show there is a unique integer c such that |a − c| = |b − c|.

- Possibility 1: c ≥ 0
  - Then |a − c| = a − c or c − a and |b − c| = b − c or c − b.
  - If a − c = b − c, then a = b, which is a contradiction.
  - If a − c = c − b, then a + b = 2c, so c = (a + b)/2.
  - Therefore, if c ≥ 0, then there is a unique integer c such that |a − c| = |b − c|.


## Section 1.8 - Question 21

Show that if n is an odd integer, then there is a unique integer k such that n is the sum of k − 2 and k + 3.

- If n is an odd integer, then n = 2x + 1 for some integer x.
- k − 2 + k + 3 = 2k + 1.
- Therefore, if n is an odd integer, then there is a unique integer k such that n is the sum of k − 2 and k + 3.

## Section 1.8 - Question 31

Prove that there is no positive integer n such that n^2 + n^3 = 100.

- n^2 + n^3 = n^2(n + 1).
- if n^2(n + 1) = 100, then n^2 = 100/(n + 1) = sqrt(100/(n + 1)) = 10/sqrt(n + 1).
- Since n is a positive integer, n + 1 is a positive integer.
- Therefore, 10/sqrt(n + 1) is a positive integer.
- However, 10/sqrt(n + 1) because the only numbers that divide 10 are 1, 2, 5, and 10 and the only way to get 1, 2, 5, 10 from sqrt(n + 1) is if n = 0, 24, 99.
- n cannot be 0 because n is a positive integer. 
- If n = 24, then n^2 + n^3 = 24^2 + 24^3 = 576 + 13824 = 14300.
- If n = 99, then n^2 + n^3 = 99^2 + 99^3 = 9801 + 970299 = 980100.


## Section 1.8 - Question 47


Use a proof by exhaustion to show that a tiling using dominoes of a 4 × 4 checkerboard with opposite corners removed does not exist. [Hint: First show that you can assume that the squares in the upper left and lower right corners are removed. Number the squares of the original checkerboard from 1 to 16, starting in the first row, moving right in this row, then starting in the leftmost square in the second row and moving right, and so on. Remove squares 1 and 16. To begin the proof, note that square 2 is covered either by a domino laid horizontally, which Page 115covers squares 2 and 3, or vertically, which covers squares 2 and 6. Consider each of these cases separately, and work through all the subcases that arise.]

To prove that a tiling using dominoes of a 4 × 4 checkerboard with opposite corners removed does not exist:

- Assume squares 1 and 16 are removed.
- Start with square 2:
    - If covered horizontally: covers squares 2 and 3. Impossible to cover remaining squares.
    - If covered vertically: covers squares 2 and 6. Impossible to cover remaining squares.
 