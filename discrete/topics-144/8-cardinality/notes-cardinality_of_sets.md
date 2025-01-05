# Chapter 2.5 - Cardinality of Sets

A function is called uncomputable when there is no algorithm that can compute its value for every input, even with unlimited time and memory. The set of all computable functions is countable, but the set of all functions is uncountable. This is a direct result of Cantor's diagonalization argument.

Previously we showed that there is a 1-to-1 correspondence between any two finite sets of the same size. We use this observation to extend the concept of cardinality to all sets, both finite and infinite.


## Definition: Equal Cardinality

> Two sets $A$ and $B$ have the same cardinality *iff* there is a bijection between them. We write $|A| = |B|$ to denote that $A$ and $B$ have the same cardinality. If $A$ and $B$ do not have the same cardinality, we write $|A| \neq |B|$.

For infinite sets, this definition provides more of a relative comparison than an absolute measure. For example, the set of natural numbers $\mathbb{N}$ is a proper subset of the set of integers $\mathbb{Z}$, but both sets are infinite and have the same cardinality. This is because there is a bijection between $\mathbb{N}$ and $\mathbb{Z}$, namely the function $f(n) = n - 1$.

## Definition: Greater/Less Than or Equal Cardinality

> If there is a 1-to-1 function from set $A$ to set $B$, then $|A| \leq |B|$. If there is a 1-to-1 function from set $A$ to set $B$ but no 1-to-1 function from set $B$ to set $A$, then $|A| < |B|$. Moreover, if $|A| \leq |B|$ and $|A| \neq |B|$, then $|A| < |B|$.

These definitions do not give any separate meaning to the symbols $|A|$ and $|B|$ when A and B are arbitrary infinite sets.


## Countable Sets

### Definition: Countable Set

> A set is called countable if it is finite or has the same cardinality as the set of positive integers $\mathbb{Z+}$. When an infinite set S is countable, we write $|S| = |\mathbb{N}|$, or we use an aleph number to denote the cardinality of S, $\aleph_0$ and say that S has cardinality aleph-null.

### Proving Countability

To prove that a set is countable, we must exhibit a bijection between the set and the set of positive integers. This is often done by listing the elements of the set in a sequence, and then defining a function that maps each element to its position in the sequence.

#### Examples

##### Even Integers

- Prove the set of even integers is countable.
- First, list the elements of the set in a sequence: $0, -2, 2, -4, 4, -6, 6, \ldots$
- Or, define a function $f(n) = 2n$
  - This maps each positive integer to an even integer, and is a bijection.
- Or, demonstrate with a code function:
  - Python:
    ```python
    >>> def f(n): return 2*n
    ... 
    >>> [f(1), f(2), f(3), f(4), f(5)]
    [2, 4, 6, 8, 10]
    ```
  - Bash:
    ```bash
    $ f() { echo $((2*$1)); }
    $ f 1; f 2; f 3; f 4; f 5
    2
    4
    6
    8
    10
    ```
  - Since we have provided a Python/Bash function that maps each positive integer to a unique positive even integer, we have established a bijection between the set of positive integers and the set of positive even integers. Therefore, the set of positive even integers is countable.


##### Odd Positive Integers

- Prove the set of odd positive integers is countable.
- First, list the elements of the set in a sequence: $1, 3, 5, 7, 9, \ldots$
- Define a function $f(n) = 2n - 1$
- Prove this is a 1-to-1 correspondence by showing that it is both 1-to-1 and onto.
  - 1-to-1: $f(n) = f(m) \Rightarrow 2n - 1 = 2m - 1 \Rightarrow n = m$
  - Onto: For any odd positive integer $k$, there is a positive integer $n$ such that $f(n) = k$. Specifically, $n = (k+1)/2$.
  - Therefore, the set of odd positive integers is countable.
- Or, use code:
    - Python:
    ```python
    >>> def f(n: int) -> int: return 2*n - 1
    >>> [f(1), f(2), f(3), f(4), f(5)]
    [1, 3, 5, 7, 9]
    ```
    - Bash:
    ```bash
        $ f() { echo $((2*$1 - 1)); }
        $ f 1; f 2; f 3; f 4; f 5
        1
        3
        5
        7
        9
    ```
    - JavaScript:
    ```javascript
    > function f(n) { return 2*n - 1; }
    > [f(1), f(2), f(3), f(4), f(5)]
    [ 1, 3, 5, 7, 9 ]
    ```
    - TypeScript:
    ```typescript
    function f(n: number): number { return 2*n - 1; }
    console.log([f(1), f(2), f(3), f(4), f(5)]);
    [ 1, 3, 5, 7, 9 ]
    ```
    - C++:
    ```cpp
    #include <iostream>
    using namespace std;

    int f(int n) { return 2*n - 1; }

    int main() {
        cout << f(1) << " " << f(2) << " " << f(3) << " " << f(4) << " " << f(5) << endl;
        return 0;
    }
    ```
- Note that the set of odd positive integers is a proper subset of the set of positive integers, but both sets have the same cardinality.

##### All Integers


- Prove the set of all integers is countable.
- One way to prove this would be to list its members in a sequence: $0, 1, -1, 2, -2, 3, -3, 4, -4, \ldots$
- Alternatively, we can define a function $f(n) = \begin{cases} n/2 & \text{if } n \text{ is even} \\ -(n+1)/2 & \text{if } n \text{ is odd} \end{cases}$
- Or, with python:
    ```python
    >>> def f(n: int) -> int: return n//2 if n % 2 == 0 else -(n+1)//2
    >>> [f(0), f(1), f(2), f(3), f(4), f(5)]
    [0, -1, 1, -2, 2, -3]
    ```

##### Rational Numbers

- Prove the set of rational numbers is countable.
- First, list the elements of the set in a sequence: $0, 1, -1, 1/2, -1/2, 2, -2, 3, -3, 1/3, -1/3, 2/3, -2/3, 3/2, -3/2, 4, -4, \ldots$
- Diagonalization argument: We can list the rational numbers in a table, and then construct a new number that is not in the table by changing the diagonal elements. This shows that the set of rational numbers is countable.
- Use a code function:
    - Python:
    ```python
    def integer_to_rational(n):
        w = int(((8*n + 1)**0.5 - 1) / 2)
        t = w*(w+1) // 2
        y = n - t
        x = w - y
        return f"{x}/{y}" if w % 2 == 0 else f"{y}/{x}"
    ```

#### For Infinite Sets

An infinite set is countable *iff* there is a 1-to-1 correspondence between the set and the set of positive integers. This is a stronger condition than simply being able to list the elements of the set in a sequence. For example, the set of rational numbers is countable, but the set of real numbers is not.


## Uncountable Sets

### The Real Numbers

The set of real numbers is uncountable. This was first proven by Cantor using a diagonalization argument. The proof is by contradiction: Assume that the set of real numbers is countable, and list them in a table. Then construct a new number that is not in the table by changing the diagonal elements. This new number is different from every number in the table, so the set of real numbers is uncountable.


Every real number has a unique decimal expansion (when the possibility that the expansion has a tail end that consists entirely of the digit 9 is excluded). Therefore, the real number r is not equal to any of r1, r2, … because the decimal expansion of r differs from the decimal expansion of ri in the ith place to the right of the decimal point, for each i.

Because there is a real number r between 0 and 1 that is not in the list, the assumption that all the real numbers between 0 and 1 could be listed must be false. Therefore, all the real numbers between 0 and 1 cannot be listed, so the set of real numbers between 0 and 1 is uncountable. Any set with an uncountable subset is uncountable (see Exercise 15). Hence, the set of real numbers is uncountable.


## Theorems about Countability

### Theorem 1 - Union of Countable Sets

> The union of a countable number of countable sets is countable.

#### Proof

- Assume A and B are disjoint
  - If they are not, we can make them so by replacing each element of B with a new element that is not in A.
- **Case 1**: A and B are finite
  - The union of A and B is finite, and therefore countable.
- **Case 2**: A is infinite and B is finite
  - Since A is countable, we can list its elements in a sequence: $a_1, a_2, a_3, \ldots$
  - We can list the elements of B in a sequence: $b_1, b_2, b_3, \ldots$
  - Then we can list the elements of A union B in a sequence: $a_1, b_1, a_2, b_2, a_3, b_3, \ldots$
  - This shows that the union of A and B is countable.
  - The same argument applies if A is finite and B is infinite.
- **Case 3**: A and B are both infinite
  - Since A and B are countable, we can list their elements in sequences: $a_1, a_2, a_3, \ldots$ and $b_1, b_2, b_3, \ldots$
  - We can list the elements of A union B in a sequence: $a_1, b_1, a_2, b_2, a_3, b_3, \ldots$
  - This shows that the union of A and B is countable.

#### Postulate - Schroder-Bernstein Theorem

> If A and B are sets with $|A| \leq |B|$ and $|B| \leq |A|$, then $|A| = |B|$. I.e., if there are 1-to-1 functions from A to B and from B to A, then there is a bijection between A and B.

##### Example

Show that |(0, 1)| = |(0, 1]|

- Define a 1-to-1 function from (0, 1) to (0, 1]: $f(x) = x$
- Define a 1-to-1 function from (0, 1] to (0, 1): $g(x) = x/2$
- Therefore, |(0, 1)| = |(0, 1]|.


## Proving Uncomputability

- We say a function is computable if there is an algorithm that can compute its value for every input, even with unlimited time and memory.
- To prove that a function is uncomputable, show that:
  - The set of all computer programs is countable.
    - This is because each program can be represented as a finite string of characters, and there are countably many finite strings of characters.
  - There are uncountaby many different functinos from a particular countably infinite set to itself
    - This is because there are uncountably many different subsets of a countably infinite set, and each subset can be used to define a different function.
    - Therefore, there are uncountably many functions from a countably infinite set to itself, and only countably many computer programs.
  - Therefore, there are uncomputable functions.

## The Zermelo-Fraenkel Axioms

The Zermelo-Fraenkel axioms are a set of axioms for set theory that provide a foundation for mathematics. They are named after mathematicians Ernst Zermelo and Abraham Fraenkel, who developed them in the early 20th century. The axioms are intended to avoid the paradoxes that plagued earlier versions of set theory, such as Russell's paradox.


1. **Axiom of Extensionality**: Two sets are equal if they have the same elements.
2. **Axiom of Regularity**: Every nonempty set has an element that is disjoint from the set.
3. **Axiom of Pairing**: For any two sets A and B, there is a set that contains exactly A and B.
4. **Axiom of Union**: For any set A, there is a set that contains all the elements of the sets in A.
5. **Axiom of Power Set**: For any set A, there is a set that contains all the subsets of A.
6. **Axiom of Infinity**: There is a set that contains the natural numbers.
7. **Axiom of Replacement**: If F is a function and A is a set, then there is a set that contains the image of A under F.
8. **Axiom of Choice**: For any set of nonempty sets, there is a set that contains exactly one element from each set.
9. **Axiom of Foundation**: Every nonempty set has an element that is disjoint from the set.