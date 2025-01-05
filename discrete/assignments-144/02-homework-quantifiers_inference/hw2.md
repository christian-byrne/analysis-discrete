## Section 1.3 - Question 3

#### a

|   p   |   q   | p &or; q | q &or; p |
|:-----:|:-----:|:------:|:------:|
|  True |  True |  True  |  True  |
|  True | False |  True  |  True  |
| False |  True |  True  |  True  |
| False | False | False  | False  |

### b

|   p   |   q   | p &and; q | q &and; p |
|:-----:|:-----:|:-------:|:-------:|
|  True |  True |   True  |   True  |
|  True | False |  False  |  False  |
| False |  True |  False  |  False  |
| False | False |  False  |  False  |


## Section 1.3 - Question 7d

> Ibrahim is smart and hard working.

**Negation**: "Ibrahim is not smart or not hard working."

## Section 1.3 - Question 9b

Conditional-Disjunction: `p -> q` &equiv; `!p or q`

| Equivalent Expressions |
|:---:|
| (p &rarr; q) &rarr; r |
| &not;(p &rarr; q) &or; r | 
| &not;(&not;p &or; q) &or; r |
| (p &and; &not;q) &or; r |

## Section 1.3 - Question 12d

|   p   |   q   |   r   | p &or; q | p &rarr; r | q &rarr; r | (p &or; q) &and; (p &rarr; r) &and; (q &rarr; r) | ((p &or; q) &and; (p &rarr; r) &and; (q &rarr; r)) &rarr; r |
|:-----:|:-----:|:-----:|:--------:|:----------:|:----------:|:------------------------------------------------:|:-----------------------------------------------------------:|
|  T |  T |  T |   T   |    T    |    T    |                       T                       |                             T                            |
|  T |  T | F |   T   |   F    |   F    |                      F                       |                             T                            |
|  T | F |  T |   T   |    T    |    T    |                       T                       |                             T                            |
|  T | F | F |   T   |   F    |    T    |                      F                       |                             T                            |
| F |  T |  T |   T   |    T    |    T    |                       T                       |                             T                            |
| F |  T | F |   T   |    T    |   F    |                      F                       |                             T                            |
| F | F |  T |  F   |    T    |    T    |                      F                       |                             T                            |
| F | F | F |  F   |    T    |    T    |                      F                       |                             T                            |


## Section 1.3 - Question 15b

| Expression | Transformation/Reason |
|:----------:|:----------------------:|
| p &rarr; (p &or; q) | |
| &not;p &or; (p &or; q) | Conditional-Disjunction Equivalence |
| (&not;p &or; p) &or; q | Associative Law for Disjunction |
| True &or; q | Negation Law |
| True | Domination Law |

## Section 1.3 - Question 15e

| Expression | Transformation/Reason |
|:----------:|:----------------------:|
| &not;(p &rarr; q) &rarr; p | |
| &not;(&not;p &or; q) &rarr; p | Conditional-Disjunction Equivalence |
| p &and; &not;q &rarr; p | 2nd De Morgan's Law and Double Negation Law |
| &not;(p &and; &not;q) &or; p | Conditional-Disjunction Equivalence |
| &not;p &or; q &or; p | 1st De Morgan's Law |
| &not;p &or; p &or; q | Commutative Law for Disjunction |
| True &or; q | Negation Law |
| True | Domination Law |

## Section 1.3 - Question 17

### a


|   p   |   q   | p &and; q | p &or; (p &and; q) |
|:-----:|:-----:|:---------:|:------------------:|
|  True |  True |    True   |        True        |
|  True | False |   False   |        True        |
| False |  True |   False   |       False        |
| False | False |   False   |       False        |

p &equiv; p &or; (p &and; q)

### b

|   p   |   q   | p &or; q | p &and; (p &or; q) |
|:-----:|:-----:|:--------:|:------------------:|
|  True |  True |   True   |        True        |
|  True | False |   True   |        True        |
| False |  True |   True   |       False        |
| False | False |  False   |       False        |

p &equiv; p &and; (p &or; q)


## Section 1.3 - Question 19

| Expression | Transformation/Reason |
|:----------:|:----------------------:|
| (&not;q &and; (p &rarr; q)) &rarr; &not;p | |
| (&not;q &and; (&not;p &or; q)) &rarr; &not;p | Conditional-Disjunction Equivalence |
| (&not;q &and; &not;p) &or; (&not;q &and; q) &rarr; &not;p | Distributive Law |
| (&not;q &and; &not;p) &or; False &rarr; &not;p | Negation Law |
| &not;q &and; &not;p &rarr; &not;p | Domination Law |
| &not;(&not;q &and; &not;p) &or; &not;p | Conditional-Disjunction Equivalence |
| q &or; p &or; &not;p | De Morgan's Law and Double Negation Law |
| q &or; True | Negation Law |
| True | Domination Law |

**(&not;q &and; (p &rarr; q)) &rarr; &not;p is a tautology.**

## Section 1.3 - Question 65a

| Expression | Transformation/Reason |
|:----------:|:----------------------:|
| (p &or; &not;q) &and; (&not;p &or; q) &and; (&not;p &or; &not;q) | |
| (p &or; &not;q) &and; ((&not;p &or; q) &and; (&not;p &or; &not;q)) | Associative Law for Conjunction |
| (p &or; &not;q) &and; (&not;p &or; (q &and; &not;q)) | Distributive Law |
| (p &or; &not;q) &and; (&not;p &or; False) | Negation Law |
| (p &or; &not;q) &and; &not;p | Domination Law |
| (p &and; &not;p) &or; (&not;q &and; &not;p) | Distributive Law |
| False &or; (&not;q &and; &not; p) | Negation Law |
| &not;q &and; &not;p | Domination Law |

**Solution**: {`p`: False, `q`: False}

## Section 1.3 - Question 66b

**Expression**: (&not;p &or; &not;q &or; r) &and; (&not;p &or; q &or; &not;s) &and; (p &or; &not;q &or; &not;s) &and; (&not;p &or; &not;r &or; &not;s) &and; (p &or; q &or; &not;r) &and; (p &or; &not;r &or; &not;s)

The expression is a conjunction of six 3-variable disjunctions. There is at least one negated variable in each of the six disjunctions. Therefore, when all variables are `False`, all six disjunctions will be `True`, and their conjunction (the expression) will be `True`. So, the expression has at least one solution and is therefore satisfiable.

**Solution**: {`p`: False, `q`: False, `r`: False, `s`: False}


## Section 1.4 - Question 1

a) True

b) True

c) False

## Section 1.4 - Question 2

a) True

b) False

c) False

d) True

## Section 1.4 - Question 5b

Every student spends more than five hours every weekday in class

## Section 1.4 - Question 7

a) For every person, if they are a comedian, then they are funny.

b) Every person is both a comedian and funny.

c) There exists a person that is not funny if they are a comedian.

d) There exists a person that is both a comedian and funny.

## Section 1.4 - Question 8b

Translate these statements into English, where R(x) is "x is a rabbit" and H(x) is "x hops" and the domain consists of all animals.

`Ax(R(x) and H(x))` = Every animal is both a rabbit and hops.

## Section 1.4 - Question 9

a) &exist;x(P(x) &and; Q(x))

b) &exist;x(P(x) &and; &not;Q(x))

c) &forall;x(P(x) &or; Q(x))

d) &not;&exist;x(P(x) &or; Q(x))

## Section 1.4 - Question 13

a) True (n + 1 > n &equiv; 1 > 0)

b) True (witnes: n = 0)

c) True (witness: n = 0)

d) False (counter-example: n = -1)

## Section 1.4 - Question 17c

&not;P(0) &or; &not;P(1) &or; &not;P(2) &or; &not;P(3) &or; &not;P(4)

## Section 1.4 - Question 25b

&not;&forall;xP(x) where P(x) is "x is perfect" and the domain is everyone.

## Section 1.4 - Question 33a

**Statement**: &exist;xP(x) where P(x) is "x can learn new tricks" and the domain is old dogs.

**Negation**: &forall;x&not;P(x) where P(x) is "x can learn new tricks" and the domain is old dogs.


## Section 1.4 - Question 36c

**Statement**: &exist;x(-4 <= x <= 1)

**Negation**: &not;&exist;((-4 <= x) &and; (x <= 1)) &equiv; &forall;x&not;((-4 <= x) &and; (x <= 1)) &equiv; &forall;x((-4 > x) &or; (x > 1))

**Without Negation Symbol**: &forall;x((-4 > x) or (x > 1))


## Section 1.4 - Question 37

a) No counterexample

b) countereaxmple: x = 0

c) counterexample: x = 0

## Section 1.4 - Question 38b

**Statement**: &forall;x(x^2 &ne; 2) where the domain is all real numbers.

**Counterexample**: x = &radic;2

## Section 1.4 - Question 43a

> At least one mail message, among the nonempty set of messages, can be saved if there is a disk with more than 10 kilobytes of free space

&exist;x((x &isin; M) &and; (&exist;yF(y) &rarr; S(x))) where M is the set of mail messages, y's domain is the set of disks, S(x) is "x can be saved", and F(y) is "y has more than 10 kilobytes of free space".

## Section 1.4 - Question 53

1. Let P(x) be "x is even" and Q(x) be "x is odd" and the domain of x be all numbers.
2. &exist;xP(x) &and; &exist;xQ(x) is true because there exists an even number and there exists an odd number.
3. &exist;x(P(x) &and; Q(x)) is false because there is no number that is both even and odd.
4. Therefore, &exist;xP(x) &and; &exist;xQ(x) is not equivalent to &exist;x(P(x) &and; Q(x)).

## Section 1.4 - Question 55

a) &exist;!xP(x) &rarr; &exist;xP(x) &equiv; `True`

b) &forall;xP(x) &rarr; &exist;!xP(x) &equiv; `False` when the domain's size is <= 1 

c) &exist;!x&not;P(x) &rarr; &not;&forall;xP(x) &equiv; `True`

## Section 1.4 - Question 62c

- `P(x)`: x is a clear explanation
- `Q(x)`: x is satisfactory
- `R(x)`: x is an excuse
- x &isin; the set of all English text

> "Some excuses are not clear explanations"

**Statement**: &exist;x(R(x) &and; &not;P(x))

## Section 1.4 - Question 63c

- `P(x)`: x is a baby
- `Q(x)`: x is logical
- `R(x)`: x is able to manage a crocodile
- `S(x)`: x is despised
- x &isin; the set of all people

> "Illogical persons are despised"

**Statement**: &forall;x(&not;Q(x) &rarr; S(x))

## Section 1.5 - Question 1

a) For every real number x, there exists a real number y such that x is less than y.

b) For every real numbers x and y, if both x and y are greater than or equal to zero, then their product xy is greater than or equal to zero.

c) For all real numbers x and y, there exists a real number z such that the product of x and y is equal to z.

## Section 1.5 - Question 4a

- `P(x, y)`: student x has taken class y
- x &isin; the set of all students in your class
- y &isin; the set of all computer science classes at your school

**Quantification**: ∃x∃yP(x, y)

> There exists a student in your class who has taken a computer science class at your school.

## Section 1.5 - Question 6b

- `C(x, y)`: student x is enrolled in class y
- x &isin; the set of all students in your school
- y &isin; the set of all classes being given at your school

**Quantification**: ∃xC(x, Math 695)

> There exists a student in your school who is enrolled in the class Math 695.

## Section 1.5 - Question 11

- `S(x)`: x is a student
- `F(x)`: x is a faculty member
- `A(x, y)` x has asked y a question
- x, y &isin; the set of all people associated with your school


a) Lois has asked Professor Michaels a question: A(Lois, Michaels)

b) Every student has asked Professor Gross a question: ∀x(S(x) &rarr; A(x, Gross))

c) Every faculty member has either asked Professor Miller a question or been asked a question by Professor Miller: ∀x(F(x) &rarr; (A(x, Miller) &or; A(Miller, x)))

d) Some student has not asked any faculty member a question: ∃x(S(x) &and; ∀y(F(y) &rarr; &not;A(x, y)))

e) There is a faculty member who has never been asked a question by a student: ∃x(F(x) &and; ∀y(S(y) &rarr; &not;A(y, x)))

f) Some student has asked every faculty member a question: ∃x(S(x) &and; ∀y(F(y) &rarr; A(x, y)))

g) There is a faculty member who has asked every other faculty member a question: ∃x(F(x) &and; ∀y(F(y) &rarr; A(x, y)))

h) Some student has never been asked a question by a faculty member: ∃x(S(x) &and; ∀y(F(y) &rarr; &not;A(y, x)))

## Section 1.5 - Question 21

> Every positive integer is the sum of the squares of four integers.

- `P(x)`: x is a positive integer
- `Q(x)`: x is the sum of the squares of four integers

**Quantification**: &forall;x(P(x) &rarr; Q(x)) where x &isin; the set of all integers

## Section 1.5 - Question 23

a) &forall;x&forall;y((P(x) &and; P(y)) &rarr; R(x, y)) where x, y &isin; the set of all real numbers and P(x) is "x is a negative" and R(x, y) is "xy is positive"

b) &forall;x(x - x = 0) where x &isin; the set of all real numbers

c) &forall;x(P(x) &rarr; Q(x)) where x &isin; the set of all real numbers and P(x) is "x is positive" and Q(x) is "x has exactily two square roots"

d) &forall;x(P(x) &rarr; Q(x)) where x &isin; the set of all real numbers and P(x) is "x is a negative" and Q(x) is "x does not have a square root that is a real number"

## Section 1.5 - Question 24d

**Quantification**: ∀x∀y((x ≠ 0) ∧ (y ≠ 0) ↔ (xy ≠ 0))

> For every real number x and for every real number y, the product of x and y is non-zero if and only if both x and y are non-zero.