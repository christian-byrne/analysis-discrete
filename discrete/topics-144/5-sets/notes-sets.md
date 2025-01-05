
# Sets

**Set**: an unordered collection of objects, called *elements* or *members* of the set

*Remark*: Note that the concept of a datatype, or type, in computer science is built upon the concept of a set. In particular, a datatype or type is the name of a set, together with a set of operations that can be performed on objects from that set. For example, boolean is the name of the set {0, 1}, together with operators on one or more elements of this set, such as AND, OR, and NOT.

Because many mathematical statements assert that two differently specified collections of objects are really the same set, we need to understand what it means for two sets to be equal.


## Properties of Sets


- defined by the elements in contains
- no order
- no multiplicity

## Notation

- x &isin; A means set A contains element x
- x &notin; is used to indicate something is not a member of the set
- N = {0,1,2,3,...}
    - The natural numbers
- Z = {..., -3, -2, 1, 0, 1, ...}
    - The integers
- Q = {a/b | a, b &isin; Z, b =/= 0}
    - The rational numbers
- R
    - The real numbers
- C
    - the complex numbers
- putting a plus or minus superscript indicates positives or negatives
    - Z+ = {1,2,3,...}
        - The positive integers


### Large or Infinite Sets

`{1, 3, 5, 7, ...}` may indicate all odd integers


### Set Builder Notation

{x &isin; Z | x is divisible by 7}

> "All integers that are divisible by 7"


### Indicating Range

[a, b] = {x | a <= x <= b}

[a, b) = {x | a <= x < b}


## Equality of Sets

> Two sets are equal if they have exactly the same elemtns 


Sets A and B are equal iff Ax(x &isin; A <-> x &isin; B)


Showing Two Sets are Equal To show that two sets A and B are equal, show that A ⊆ B and B ⊆ A.


## Empty Sets

{} or zero with cross through it


## Finite Sets

> *Finite* if it is empty or its memebrs can be numbered from 1 to n for some positive integer n

A set that is not finite is said to be infinite

## The Universal Set

> Anytime we talk about sets we have to have a state or understood well defined *universal set* which contains all the elemetns we're interested in. We usually use the variable U for the universal set


U might be R (real numbers), integers, etc.



## Subsets

### Subset Definition

A subset is a set where all of its elements are also members of another set. 

Showing that A is a Subset of B To show that A ⊆ B, show that if x belongs to A then x also belongs to B.

Showing that A is Not a Subset of B To show that A ⊈ B, find a single x ∈ A such that x ∉ B.

Theorem 1 shows that every nonempty set S is guaranteed to have at least two subsets, the empty set and the set S itself, that is, ∅ ⊆ S and S ⊆ S.

> THEOREM 1: For every set S, (i) ∅ ⊆ S and (ii) S ⊆ S.


### Proper Subset

A proper subset is a subset that is not equal to the set that contains it. 

For example, {1, 2, 3} is a subset of {1, 2, 3}, but it is not a proper subset. 

Some mathematicians use A⊂B to mean that A is a subset of B and A⊊B to mean that A is a proper subset of B. 

## Cardinality

**Cardinality**: the cardinality of a finite set S, written |S| is the number of elements in it. (Sometimes called size.)

An empty set is the only set who has a cardinality of 0.


if A is finite, then B ⊂ A -> |B| < |A|


Let S be a set. If there are exactly n distinct elements in S where n is a nonnegative integer, we say that S is a finite set and that n is the cardinality of S. The cardinality of S is denoted by |S|.


#### Infinite Cardinality

if A and B are both infnite, we still cannot say that |A| = |B|


## Power Sets

**Power Set**: Given a set S, the *power set* of S, written P(S), is the set of all possible subsets of S


Example: S = {a, b, c}

Then P(S) = {{}, {a}, {b}, {c}, {a, b}, {b, c}, {a, c}, {a,b,c}}

If S is a finite set, then |P(S)| = 2^|S|

In other words, the set of all permutations of a set, is 2 to the power of the size of the set


In other words, everytime you add a variable, you double the set of permutations


#### Power Set of the Empty Set

The empty set has a cardinality of 0 -- it is empty

However, the cardinality of the Power Set of an Empty Set is 1. 

Recall that |P(S)| = 2^|S| = 2^0 = 1

> What is the power set of the empty set? What is the power set of the set {∅}?

Solution:
- The empty set has exactly one subset, namely, itself. Consequently,
- The set {∅} has exactly two subsets, namely, ∅ and the set {∅} itself. Therefore,
- P(∅) = {∅} and P({∅}) = {∅, {∅}}.
- In general, if S is a set with n elements, then P(S) has 2^n elements.

##### Helpful Explanation - Power Set with Empty Sets

The power set of {∅, {∅}} is `{∅, {∅}, {{∅}}, {∅, {∅}}}`

just like the power set of {a, {b}} is `{∅, {a}, {{b}}, {a, {b}}}`

The numbers dont change when elements are the empty set




## Ordered n-Tuples

The ordered n-tuple (a1, a2, …, an) is the ordered collection that has a1 as its first element, a2 as its second element, …, and an as its nth element.


## Ordered PAir

An **ordered pair** is a pair of elements or numbers written in a specific and fixed order.

On a Cartesian plane, an ordered pair (x, y) is defined as the coordinates of a point such that “x” is the x-coordinate and “y” is the y-coordinate. 

Ordered pair examples: (1,2), (-4, 5), (-9, -8)


## Cartesian PRoduct

The *Cartesian Product* of set A and set B, written A x B, is the set of all ordered pairs (a, b) where a &isin; A and b &isin; B

Example: A = {a, b, c}, B = {1,2}

A x B = {(a, 1), (a, 2), (b, 1), (b, 2), (c, 1), (c, 2)}


B x A = {(1, a), (2, a), (1, b), (2, b), (1, c), (2, c)}


*Remark*: Note that when A, B, and C are sets, (A × B) × C is not the same as A × B × C 

A subset R of the Cartesian product A × B is called a relation from the set A to the set B. The elements of R are ordered pairs, where the first element belongs to A and the second to B. For example, R = {(a, 0), (a, 1), (a, 3), (b, 1), (b, 2), (c, 0), (c, 3)} is a relation from the set {a, b, c} to the set {0, 1, 2, 3}, and it is also a relation from the set {a, b, c, d, e} to the set {0, 1, 3, 4). (This illustrates that a relation need not contain a pair (x, y) for every element x of A.) A relation from a set A to itself is called a relation on A.

### Cartesian Product of Empty Set

A x {} = {}


{} x A = {}


## Relation

A relation from the set A to the set B is defined to be any subset of A x B


## Using Set Notation with Quantifiers

Sometimes we indicate the domain of a quantified statement using set notation

- Ax &isin; Rx^2 >= 0 
- ∀x ∈ S(P(x)) denotes the universal quantification of P(x) overall elements in the set S. 
  - In other words, ∀x ∈ S(P(x)) is shorthand for ∀x(x ∈ S → P(x)).
- Similarly, ∃x ∈ S(P(x)) denotes the existential quantification of P(x) over all elements in S. 
  - That is, ∃x ∈ S(P(x)) is shorthand for ∃x(x ∈ S ∧ P(x)).
- The statement ∀x ∈ R(x2 ≥ 0) states that for every real number x, x2 ≥ 0.
  - This statement can be expressed as “The square of every real number is nonnegative.” This is a true statement.
- The statement ∃x ∈ Z(x2 = 1) states that there exists an integer x such that x2 = 1. 
  - This statement can be expressed as “There is an integer whose square is 1.” This is also a true statement because x = 1 is such an integer (as is −1).

## Truth Sets and Predicates

Given a predicate P and a domain D, we define the *truth set* of P to be the set of all elements x of P such that P(x) is true

*Note* that ∀xP(x) is true over the domain U if and only if the truth set of P is the set U. Likewise, ∃xP(x) is true over the domain U if and only if the truth set of P is nonempty.

## Union and Intersection

### Union 

Union A U B is the set of all elements that are in A or in B

### Intersection


Intersection A U B is the set of all elements that are in A and in B 


### Disjoint Sets

Two sets *disjoint* if their intersection is empty 

A !U B = {}


### Cardinality 

|A U B| = |A| + |B| - |A !U B|



## Set Difference


The difference of A and B, denoted `A - B` is the set of all set of eleemnts of A that are notin B



## Symmetric Difference

A &crosscirlce; is the set of all set of elements that are in A or B, but not in the intersection (not in both)


Thus, A &crosscirlce; B = {A U B} - {A !U B}


## Complement of a Set


Given a set A and an agreed upon "universal" set U which contains A, we define the complement of A, denoted cross bar A, as U - A


The complement can be thought of as the negation

-----------------------------

## Set Identities

### Complementation 

!!(A) = A


### Identity

A !U U = A

A U {} = A

### Complement

A U !A = U

A !U !A = {}

### Idempotent

A U A = A

A !U A = A

### Domination 

A U U = U 

A !U {} = {}

### Associativity

(A U B) U C = A U (B U C)

### Commutativity

A U B = B U A

### Distributivity

A U (B !U C) = (A U B) !U (A U C)


A !U (B U C) = (A !U B) U (A !U C)

### De Morgan

!(A !U B) = !A U !B

!(A U B) = !A !U !B


### Absorption 

A U (A !U B) = A

A !U (A U B) = A


---------------------------


## Multiset

if A = {a, b, a, b, c, c, c} is a multiset, than A =/= {a, b, c}

Multiplicity matters. but order still doesn't matter


Sometimes written as {2 * a, 2 * b, 3 * c}


### Subsets of Multisets

Multiplicty matters in order to be a subset


### Intersections of Multisets


The lwoest number of occurrences of every element

### Unions of Multisets


The greatest number of occurrences of every element










