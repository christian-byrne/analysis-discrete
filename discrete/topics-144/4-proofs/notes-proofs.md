
# Proofs and Theorems

- **Theorem**: a statement that can be shown to be true/proved
- **Proof**: A sound argument that establishes the truth of a theorem
    - The key to remember is a proof is just an explanation demonstrating the truth of some theorem (argument)
    - However, a proof must be *complete*.
        - This means we can't leave any possibilities unaddressed
-**Axioms**: Statements that are assumed to be true
    - We can't prove anything withotu having some axioms defined
-**Lemma**: A theorem used to prove another theorem
-**Corollary**: A theorem that follows directly from another theorem
    - Which theorems are corollaries and lemmas is somewhat subjective
-**Conjecture**: A statement which is proposed to be true
    - hopefuly based on some kind of evidence), but has not been proved

## Proofs

### Format, Requirements and Definition

- A proof must be **clear**. Even though we often use mathematical symbols, a proof should still read like a paragraph.
- a proof must be **correct** which means it must **logically valid** and **sound**
    - **sound**: any hypothesis you use must be true
- formal logic doesnt by itself tell you anything about the real world, only how to reason from one point to another
- you cant derive anyhing logically without establishing axioms first
- no one ever uses formal logic when it would just be unecessarily wordy/verbose. Just use written language -- the point that it is clear, complete, valid, and sound (not that it exactly compleis with some style guide or template)
    - you would never write 2 pages of logical transformations in formal logic. 
    - many small steps can be inferred by the reader if they are intuitive and obvious, that is okay to allow within the formualtion of your proof


*A proof does not have to be in a particular format or use a particular vocabulary or set of sumbols. A proof is a communication of a sound argument that shows something is true. Symbols and mathematical notation can help you save time, however a perfectly correct proof could write everything out in words.*

### Some Mathematical Definitions to Remember when Doing Proofs

- An integer n is even iff n = 2k for some integer k
- an integer n is odd iff n = 2k + 1 for some integer k
- A  number q is a rational nmber iff q = a/b where a and b are integers
    - It is also true that every rational number q = a/b where a and b are integers that have no common factors

### Proof Types

#### Direct Proof

> to prove if p, then q

- Start with hypothesis, end with conclusion
- Assume p is true and use axioms and rules of inference to show that q must be true
    - assume the hypothesis p and prove the conclusino q


#### Proof by Contraposition

- Recall the contrapositive of `P -> Q` is `!Q -> !P`
    - Also, `(!Q -> !P) <-> (P -> Q)`
    - This can be asily shown with a truth table, or by simplifying
- Thus, you can prove something by way of contraposition
- Careful not to confuse with contrapositive with converse
    - The converse is not always equivalent
- Sometimes it is very difficult to prove something directly, but another method like proof by contraposition is easy (relatively)
    - Therefore, you should use these strategies if you ever get stuck



##### Example

1. Prove that if the square of an integer is odd, then the integer must be off
2. hence, prove n^2 odd -> n odd
3. To do a proof by contraposition, prove n even -> n^2 even

#### Proof by Contradiction

> Assume hypothesis is true and conclusion is false, then evaluate/simplify/plug-in

1. Let 0 be any contgradiction (always false)
2. then `(p and !q -> 0) -> (p -> q)` is a tautology
3. Hence, proving `p and !q -> 0` implies proving a conjecture


**Another way of Thinking About it**: If you can that `(p and !q)` can;t happen, then if P is true, Q must be true. Therefore, `p -> q`


##### Example

1. The sum of even integers is even
2. Assume even integers, and when they are summed we get an odd integer
3. Let x = 2m, y = 2n for integers m and n and assume that x + y is odd
4. Then x + y = 2m + 2n = 2k + 1 for some integer k
5. Hence, 2(m + n - k) = 1, where m + n + k is some integer
6. This is a contradiction since 1 is not even


#### Vacuous Proofs

1. Suppose ` Ax(P(x) -> Q)`.
2. If there is no x that makes P(x) true, then the implication is true.
3. Showing that no such exists is said to be a vacuous proof. 

For example, every negative integer n which is a perfect square is equal to 4 when multiplied by 3. It's techncially true because there are no negative numbes that are perfect squares. It is vacuously true.

#### Trivial Proofs

1. Suppose we have `Ax(P -> Q(x))` and Q(x) is true for all x
2. Then the statement is true. We say showing that Q is always true is a trivial proof

"If every student is 5 feet tall, then they are all human."

#### Proofs of Equivalence

1. Prove a biconditional `p <-> q`
2. Usually written as "p if an donly if q" or "p iff q"
3. Recall that `p <-> q` is equivalent to `(p -> q) and (q -> p)`
4. So, a proof of a biconditional, it's a proof of two parts

##### Basic Example

> Prove if n is an int, then n is odd iff n^2 is odd

**Part 1**

2. let n be odd
3. therefore, n = 2k + 1 for some integer k
4. n^2 = (2k + 1)^2 = 4k^2 + 4k + 1 = 2(2k^2 + 2k) + 1
5. n^2 is ood, since 2k^2 + 2k is a member of the set of real numbers

**Part 2**

1. Let n^2 be odd
2. therefore, n^2 = 2k + 1 for some integer k

*We get stuck here, so let's try to prove by contradiction*

1. assume contrapositive?
1. asssume n^2 is odd and n is even  
1. n = 2k for some integer k
3. n^2 = (2k)^2 = 4k^2 = 2(2k)^2
4. n^2 is even -><- (we assumed n^2 is odd)
5. proof by contradiction


##### Chains of Equivalence

- Suppose we're trying to prove `p <-> q <-> r <-> s`
- We don't need to prove all equivalence, because we know that biconditional are transitive


#### Proof by Counter Example

- Given a conjecture `p -> q`, suppose we want to show it isn't true
- To disprove, look for a case where P is true but Q is false


##### Example

> Disprove that "For every positive integer n, n! <= n^2"

1. start by testing some cases
2. going up, we eventually find a value of n which serves as a counter example, therefore disproving the conjecture

##### Common Mistakes

- Diving by a variable whose domain includes 0, because it necessitates that you are dividing by 0 in some cases
- Begginging the Question

#### Proof by Cases

Segment into cases


> If we have `p1 or p2 or p3 or ... pn -> q`

We can prove it by cases by showing that 

`(p1 -> q) and (p2 -> q) and ... (pn -> q)`

Because if any p are true, then q has to be true (because the entire disjunctive would be true), otherwise there is a case where the first statement is false.


#### Exhaustive Proof

- Only possible when the domain is finite
- Examines every possible set of values for the variables


#### Existence Proof

- prove something of the form `ExP(x)`
- find some x that makes P(x) true
    - Such an x is called a witness
- You can also show a proof by, instead of finding a witness, proving that a witness necessarily must exist
    - show there exists irrational numbers x and y such that x^y is rational
    - consider sqrt(2). If this is rational, then were done (x = y = sqrt(2))
    - if it is irrational, we can let x = sqrt(2)^sqrt(2) and y = sqrt(2) (both irrational)
    - then x^y = sqrt(2)^sqrt(2)^sqrt(2) = sqrt(2)^2 = 2
    This proof is nonconstructive

#### Uniqueness Proofs

To prove that there is a unique elements that makes something true

##### Example

Prove that for every integer a, there is exactly one integer b such that a+b = 0

1. let a be an integer
2. then -a is an integer (-0 = 0 ) and a + (-a) = 0 
    Existence proved
3. Suppose b is an integer such that a+n=0
4. a + b - a = 0 - a
5. b = - a 
    Uniqueness proved


-----------------------------------------

### WLOG (Without Loss of Generality)


WLOG is sometimes used in a proof by cases where the proof of one case is the same as the other


#### Example

1. Prove that if x and y are ints that are not both even, then xy or x + y is odd
2. Assume x and y are integers that are not both even
3. WLOG assume x is odd
4. x = 2k + 1 for some integer k
5. case 1: y is even
    y = 2j for some integer j
    x + y = 2k + 1 + 2j = 2(k + j) + 1, so x + y is odd
6. case 2: y is odd
    y = 2j + 1 for some integjer j
    xy = (2k +1)(2j + 1) = 4kj + 2k + 2j + 1 = 2(2kj + k + j) + 1
    xy is odd


-----------------------------------------


### Proof Strategies/Checklist

1. try some examples
    see if you find a counter example
2. if the examples work, are the number of possibilitieis finite and small?
    if so, find exhaustive proof
3. If not, see if you can break the conjecture into different parts
    if so, try to prove each of those cases individually (or find an exception or counterexample)
4. If not, look at doing a direct proof
5. If you get stuck there,
6. If not, contraposition
7. if not, contradiction
8. if not, try [backward reasoning](### Backward Reasoning) (especially useful if you're trying to show two expressions are equal)
    if you're trying to prove `p -> q`, you might get some insight by start with q and working backward
9. If not, try to look at existing proofs that have a similar, interchangeable, or compositable pattern    

#### Backward Reasoning

Prove that the arithmetic mean > the geometric mean for all positive, distinct real numbers

1. Start with the conclusion: (x+y)/2 > sqrt(xy)
2. Simplify algebraically to get (x - y)^2 > 0
3. This is true as long as x and y are distinct. Because a non-zero number squared will always be positive (> 0)
4. We still haven't proved the original argument, but we can create a proof by checking if each step in (2) will always be true when done in reverse
    When we see the algebra in reverse, it's much easier to see how you can start with some relatively arbitrary premises and end up at the desired conclusion
5. So start with `(x - y)^2 > 0` as the first premise/step
    We can start with this because it's a true premise if x and y are distinct (which is specified in the problem)

**Be Careful**: `q -> p` does not *neccessitate* that `p -> q`, it is just meant as a tool to find proofs, not establish them


-----------------------------------------




