## Section 1.5 - Question 28f


**Statement**: ∃x∀y(y ≠ 0 → xy = 1) where x and y are real numbers.

**Truth Value**: False

## Section 1.5 - Question 31

Express the negations of each of these statements so that all negation symbols immediately precede predicates.

### a

> ∀x∃y∀zT(x, y, z)

The negation is: ∃x∀y∃z¬T(x, y, z)

### b

> ∀x∃yP(x, y) &or; ∀x∃yQ(x, y)

The negation is: ∃x∀y¬P(x, y) &and; ∃x∀y¬Q(x, y)

### c 

> ∀x∃y(P(x, y) &and; ∃zR(x, y, z))

The negation is: ∃x∀y(¬P(x, y) &or; ∀z¬R(x, y, z))

### d

> ∀x∃y(P(x, y) → Q(x, y))

The negation is: ∃x∀y(P(x, y) &and; ¬Q(x, y))

## Section 1.5 - Question 33

Rewrite each of these statements so that negations appear only within predicates (that is, so that no negation is outside a quantifier or an expression involving logical connectives).

### a

> ¬∀x∀yP(x, y)

∃x∃y¬P(x, y)

### b

> ¬∀y∃xP(x, y)

∃y∀x¬P(x, y)

### c 

> ¬∀y∀x(P(x, y) &or; Q(x, y))

∃y∃x(¬P(x, y) &and; ¬Q(x, y))

### d 

> ¬(∃x∃y¬P(x, y) &and; ∀x∀yQ(x, y))

∀x∀yP(x, y) &or; ∃x∃y¬Q(x, y)


### e 

> ¬∀x(∃y∀zP(x, y, z) &and; ∃z∀yP(x, y, z))

∃x(∃y∀z¬P(x, y, z) &or; ∀z∃y¬P(x, y, z))


## Section 1.5 - Question 39

Find a counterexample, if possible, to these universally quantified statements, where the domain for all variables consists of all integers.

### a 

> ∀x∀y(x^2 = y^2 → x = y)

**Counterexample**: {`x` = 2, `y` = -2}

### b 

> ∀x∃y(y^2 = x)

**Counterexample**: {`x` = -1}

### c 

> ∀x∀y(xy ≥ x)

**Counterexample**: {`x` = 0, `y` = -1}


## Section 1.5 - Question 40


Find a counterexample, if possible, to these universally quantified statements, where the domain for all variables consists of all integers.

### a

> ∀x∃y(x = 1/y)

**Counterexample**: { `x` = 0 }

### b 

> ∀x∃y(y^2 − x < 100)

**Counterexample**: { `x` = -1000 }

### c

> ∀x∀y(x^2 ≠ y^3)

**Counterexample**: { `x` = 1, `y` = 1 }


## Section 1.6 - Question 1

> If Socrates is human, then Socrates is mortal.
> Socrates is human. 
> Therefore, Socrates is mortal.


`P`: Socrates is human


`Q`: Socrates is mortal



> P → Q
> 
> P
> 
> —————————
>  
> &there4; Q


This is a valid argument.


## Section 1.6 - Question 4

**a**: Simplification

- p: Kangaroos live in Australia
- q: Kangaroos are marsupials

> p &and; q
> 
> ———————
> 
> &there4; q

**b**: Disjunctive Syllogism

- p: It is hotter than 100 degrees today
- q: The pollution is dangerous

> p &or; q
> 
> ¬p
> 
> ———————
> 
> &there4; q

**c**: Modus Ponens

- p: Linda is an excellent swimmer
- q: Linda can work as a lifeguard

> p
> 
> p → q
> 
> ———————
> 
> &there4; q

**d**: Addition

- p: Steve will work at a computer company this summer
- q: Steve will be a beach bum

> p
> 
> ———————
> 
> &there4; p &or; q

**e**: Hypothetical Syllogism

- p: I work all night on this homework
- q: I can answer all the exercises
- r: I will understand the material

> p → q
> 
> q → r
> 
> ———————
> 
> &there4; p → r


## Section 1.6 - Question 5

Use rules of inference to show that the hypotheses “Randy works hard,” “If Randy works hard, then he is a dull boy,” and “If Randy is a dull boy, then he will not get the job” imply the conclusion “Randy will not get the job.”

- p: Randy works hard
- q: Randy is a dull boy
- r: Randy will not get the job


> p
> 
> p → q
> 
> q → r
> 
> ———————
> 
> &there4; r


## Section 1.6 - Question 9e

“What is good for corporations is good for the United States.” “What is good for the United States is good for you.” “What is good for corporations is for you to buy lots of stuff.”

- `p`: good for corporations
- `q`: good for the United States
- `r`: good for you
- `s`: buy lots of stuff

> p → q
> 
> q → r
> 
> s → p
> 
> ———————
> 
> &there4; s → r

**Rule of Inference**: Hypothetical Syllogism

"buy lots of stuff is good for you."


## Section 1.6 - Question 11


Show that the argument form with premises p1, p2, …, pn and conclusion q → r is valid if the argument form with premises p1, p2,.…, pn, q, and conclusion r is valid.


- **Argument 1**: p → r
- **Argument 2**: p → (q → r)

If argument 1 is valid, then whenever p is true, r is also true. In argument 2, if q is false, then the conclusion is vacuously true. If q is true, then the statment or q → r is valid only if r is also true or if the premise p is false. But by argument 1, if p is true, then r is also true. Therefore, the argument form with premises p1, p2, …, pn and conclusion q → r is valid if the argument form with premises p1, p2,.…, pn, q, and conclusion r is valid.


## Section 1.6 - Question 16

### a

- `p`: enrolled in the university
- `q`: lived in a dormitory


> p → q
> 
> ¬q
> 
> ———————
> 
> &there4; ¬p

**Correct** Correct by Modus Tollens


### b

- `p`: car is convertible
- `q`: fun to drive

> p → q
> 
> ¬p
> 
> ———————
> 
> &there4; ¬q

**Incorrect** - Denying the Antecedent

### c


- `p`: is action movie
- `q`: Quincy likes the movie

> p → q
> 
> q
> 
> ———————
> 
> &there4; p

**Incorrect** - Affirming the Consequent


### d 

- `p`: is a lobsterman
- `q`: sets at least a dozen traps


> p → q
> 
> p
> 
> ———————
> 
> &there4; q


**Correct** - Modus Ponens



## Section 1.6 - Question 23

**Step 1 Error**: Wrote the premise as a disjunction instead of a conjunction

**Step 5 Error**: Can't use same variable for different existential instantiations because it's not guaranteed that the same variable will satisfy both predicates given the premise

## Section 1.6 - Question 24


**Step 3 Error**: P(c) &or; Q(c) does not infer P(c) by way of simplification. P(c) is not inferred because P(c) may be false and Q(c) true. 

**Step 5 Error**: P(c) &or; Q(c) does not infer Q(c) by way of simplification. Q(c) is not inferred because P(c) may be true and Q(c) false. 

**Step 7 Error**: The correct conjunction from (4) and (6) would be ∀xP(x) &and; ∀xQ(x)

**Step 7 Error**: The conclusion in step 7 is not the same as the supposed conclusion in the original statement.


## Section 1.6 - Question 26

Justify the rule of universal transitivity, which states that if ∀x(P(x) → Q(x)) and ∀x(Q(x) → R(x)) are true, then ∀x(P(x) → R(x)) is true, where the domains of all quantifiers are the same.


| Step | Reason |
|------|--------|
| ∀x(P(x) → Q(x)) &and; ∀x(Q(x) → R(x)) | Premise |
| P(c) → Q(c) &and; Q(c) → R(c) for an arbitrary c | Universal Instantiation |
| P(c) → R(c) | Hypothetical Syllogism |
| ∀x(P(x) → R(x)) | Universal Generalization |


## Section 1.6 - Question 35


If Superman were able and willing to prevent evil, he would do so. If Superman were unable to prevent evil, he would be impotent; if he were unwilling to prevent evil, he would be malevolent. Superman does not prevent evil. If Superman exists, he is neither impotent nor malevolent. Therefore, Superman does not exist.

- `p`: Superman is able to prevent evil
- `q`: Superman is willing to prevent evil
- `r`: Superman is impotent
- `s`: Superman is malevolent
- `t`: Superman exists
- `u`: Superman prevents evil

> p &or; q → u
> 
> ¬p → r
> 
> ¬q → s
> 
> ¬u
> 
> t → ¬r &or; ¬s
> 
> ——————————————
> 
> &there4; ¬t

This argument is **Valid**


## Section 1.7 - Question 1

1. let x and y be two odd integers
2. x = 2k + 1 and y = 2l + 1
3. x + y = 2k + 1 + 2l + 1 = 2(k + l + 1)
4. x + y is even because it is 2 times an integer

## Section 1.7 - Question 5

**Direct Proof**

1. If m + n is even then m + n = 2k for some integer k.
2. If n + p is even then n + p = 2l for some integer l. 
3. Solving for m, we get m = 2k − n and solving for p, we get p = 2l − n.
4. Therefore, m + p = 2k − n + 2l − n = 2(k + l − n).
5.  Because k + l − n is an integer and is multiplied by 2, we conclude that m + p is even. 


## Section 1.7 - Question 7

Use a direct proof to show that every odd integer is the difference of two squares. [Hint: Find the difference of the squares of k + 1 and k where k is a positive integer.]

**Direct Proof**

1. Let k be a positive integer
2. The difference of the squares of k + 1 and k is (k + 1)^2 - k^2 = k^2 + 2k + 1 - k^2 = 2k + 1
3. Therefore, every odd integer is the difference of two squares.

## Section 1.7 - Question 9

Use a proof by contradiction to prove that the sum of an irrational number and a rational number is irrational.

1. Let x be irrational and y be rational
2. Suppose that x + y is rational
3. Then x + y = r for some rational number r
4. Then x = r - y
5. But x is irrational, which is a contradiction
6. Therefore, the sum of an irrational number and a rational number is irrational.

## Section 1.7 - Question 15

Prove that if x is an irrational number and x > 0, then sqrt(x) is also irrational.

1. Let x be an irrational number and x > 0
2. Suppose that sqrt(x) is rational
3. Then sqrt(x) = p/q for some integers p and q
4. Then x = p^2/q^2
5. But x is irrational, which is a contradiction

 