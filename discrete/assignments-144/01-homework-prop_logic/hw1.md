
# Assignment 1

## Section 1.1
    
### 1

a) `True`

b) `False`

c) `True`

d) `False`

e) Not a proposition

f) Not a proposition

### 5

a) It is not the case that "Mei has an MP3 player."

b) It is not the case that "There is no pollution in New Jersey."

c) It is not that case that `2 + 1 = 3`

d) The summer in Maine is not hot or not sunny.

### 7

a) It is not the case that "Steve has more than 100 GB free disk space on his laptop."

b) Zach does not block e-mails from Jennifer or does not block texts from Jennifer.

c) It is not the case that `7 * 11 * 13 = 999`

d) It is not the case that "Diane rode her bicylce 100 miles on Sunday."

### 8

| Smartphone   | RAM    | ROM    | Camera  |
|--------------|--------|--------|---------|
| A | 256MB  | 32GB   | 8MP     |
| B | 288MB  | 64GB   | 4MP     |
| C | 128MB  | 32GB   | 5MP     |

a) `true`

b) `true`

c) `false`

d) `false`

e) `false`


### 9

| Companny | Annual Revenue | Net Profit |
|----------|----------------|------------|
| Acme Computer | $138b | $8b |
| Nadir Software | $87b | $5b |
| Quixote Media | $111b | $13b |

a) `false`

b) `true`

c) `true`

d) `true`

e) `true`

### 11h

`p`: Swimming at the New Jersey shore is allowed

`q`: Sharks have been spotted near the shore

| Expression | Transformation/Reason |
|------------------------------------|-|
| &not;`p` &and; (`p` &or; &not;`q`) | |
| (&not;`p` &and; `p`) &or; &not;`q` | Associative Law |
| (`false`) &or; &not;`q` | Negation Law |
| &not;`q` | Identity Law |

***It is not the case that sharks have been spotted near the shore.***

### 13

`p`: It is below freezing

`q`: It is snowing

a) `p` &and; `q`

b) `p` &and; &not;`q`

c) &not; `p` &and; `q`

d) `p` &or; `q`

e) `p` &rarr; `q`

f) `p` &or; `q` &and; (`p` &rarr; &not;`q`)

g) `p` &harr; `q`

### 16d

| Variable | Definition |
|----------|------------|
| `p` | You get an A on the final exam |
| `q` | You do every exercise in this book
| `r` | You get an A in this class

"You get an A on the final, but you don't do every exercise in this book; neverthless, you get an A in this class." &equiv; `p` &and; &not;`q` &and; `r`


### 17

a) `r` &and; &not;`p`

b) &not;`p` &and; `q` &and; `r`

c) `r` &rarr; (`q` &harr; `p`)

d) &not;`q` &not;`p` &and; `r`

e) (&not;`r` &and; &not;`p`) &rarr; `q`

### 19

a) `false`

b) `true`

c) `true`

d) `true`


### 25h

"Jan will go swimming unless the water is too cold" &equiv; "If the water is too cold, Jan will not go swimming and if the water is not too cold, Jan will go swimming" &equiv; "Jan will go swimming if and only if the water is not too cold" &equiv; `p` &harr; &not;`q`

### 29a

**Converse**: If I will ski tomorrow, then it snowed today.

**Inverse**: If it did not snow today, then I will not ski tomorrow.

**Contrapositive**: If I will not ski tomorrow, then it did not snow today.

### 30c

`p`: I stay up late

`q`: I sleep until noon

| | | |
|-|-|:-|
| Conditional | `p` &rarr; `q` | When I stay up late, it is necessary that I sleep until noon.
| Converse | `q` &rarr; `p` | If I sleep until noon, then I stay up late.
| Inverse | &not;`p` &rarr; &not;`q` | If I do not stay up late, then I do not sleep until noon.
| Contrapositive | &not;`q` &rarr; &not;`p` | If I do not sleep until noon, then I do not stay up late.

Because the if-clause and main-clause of the original conditional are both present-tense, it is a real-conditional and therefore the statement has no implied timeframe -- it only expresses a general, overarching relationship between the two clauses. So, the tenses of both clauses of the converse, inverse, and contrapositive should be present-tense as well. 

### 32c

Variables: `p`, `r`, `s`, `t`, `u`, `v`

Truth Table Rows = `2^Nvariables` = `2^6` = 64

### 33d

| `p` | `q` | (`p` &or; `q`) | (`p` &and; `q`) | (`p` &or; `q`) &rarr; (`p` &and; `q`) |
|-----|-----|---------------|----------------|---------------------------------------|
| `T` | `T` | `T`           | `T`            | `T`                                   |
| `T` | `F` | `T`           | `F`            | `F`                                   |
| `F` | `T` | `T`           | `F`            | `F`                                   |
| `F` | `F` | `F`           | `F`            | `T`                                   |


### 35f

| `p` | `q` | `p` &oplus; `q` | `p` &oplus; &not;`q` | (`p` &oplus; `q`) &rarr; (`p` &oplus; &not;`q`) |
|-----|-----|-----------------|----------------------|-------------------------------------------------|
| `T` | `T` | `F`             | `T`                  | `T`                                             |
| `T` | `F` | `T`             | `F`                  | `F`                                             |
| `F` | `T` | `T`             | `F`                  | `F`                                             |
| `F` | `F` | `F`             | `T`                  | `T`                                             |


### 37a

| `p` | `q` | &not;`q` | `p` &rarr; &not;`q` |
|-----|-----|----------|---------------------|
| `T` | `T` | `F`      | `F`                 |
| `T` | `F` | `T`      | `T`                 |
| `F` | `T` | `F`      | `T`                 |
| `F` | `F` | `T`      | `T`                 |


### 39a

| `p` | `q` | `r` | &not;`q` | &not;`q` &or; `r` | `p` &rarr; (&not;`q` &or; `r`) |
|-----|-----|-----|----------|-------------------|--------------------------------|
| `T` | `T` | `T` | `F`      | `T`               | `T`                            |
| `T` | `T` | `F` | `F`      | `F`               | `F`                            |
| `T` | `F` | `T` | `T`      | `T`               | `T`                            |
| `T` | `F` | `F` | `T`      | `T`               | `T`                            |
| `F` | `T` | `T` | `F`      | `T`               | `T`                            |
| `F` | `T` | `F` | `F`      | `F`               | `T`                            |
| `F` | `F` | `T` | `T`      | `T`               | `T`                            |
| `F` | `F` | `F` | `T`      | `T`               | `T`                            |


### 48a

1 1000 ∧ (0 1011 ∨ 1 1011) &equiv; 1 1000 ∧ 1 1011 &equiv; **1 1000**

## Section 1.2

### 1

&not;`e` &rarr; &not;`a`

### 3

(`r` &and; &not;`m` &and; &not;`b`) &rarr; `g`

### 5

(`a` &and; (`b` &or; `p`) &and; `r`) &rarr; `e`

### 11

`p`: The router can send packets to the edge system

`q`: The router supports the new address space

`r`: The latest software release is installed

1. The router can send packets to the edge system only if it supposrts the new address space

    `p` &rarr; `q`

2. For the router to support the new address space, it is necessary that the latest software release is installed

    `q` &rarr; `r`

3. The router can send packets to the edge system only if the latest software release is installed

    `p` &rarr; `r`

4. The router does not support the new adress space

    &not;`q`

(`p` &rarr; `q`) &and; (`q` &rarr; `r`) &and; (`p` &rarr; `r`) &and; &not;`q`

This is satisfiable when `p` is `true`, `q` is `false`, and `r` is `true`, so the specifications are consistent.

### 17

The two trunks that do not hold the treasure are empty. To win, you must select the correct trunk. 

- Trunk 1 Inscription: The treasure is in Trunk 3
- Trunk 2 Inscription: The treasure is in Trunk 1
- Trunk 3 Inscription: This trunk is empty

`A`: The treasure is in Trunk 1

`B`: The treasure is in Trunk 2

`C`: The treasure is in Trunk 3

The Queen, who never lies says

a) "All the inscriptions are false."

&not;`C` &and; &not;`A` &and; `C`

The Queen cannot say this

b) "Exactly one of the inscriptions is true."

`C` &oplus; (`A` &oplus; &not;`C`)

Satisfiable when `C` is true

c) "Exactly two of the inscriptions are true."

Satifiable when `A` is true

d) "All three inscriptions are true."

`C` &and; `A` &and; &not;`C`

The Queen cannot say this

### 30

`A`: A is the knave

`B`: B is the knave

`C`: C is the knave

A says "I am the knave" and B says "I am the knave" and C says "I am the knave"

(`A` &rarr; &not;`A`) &and; (`B` &rarr; &not;`B`) &and; (`C` &rarr; &not;`C`) 

**There are no solutions**

### 31

A says "I am the knight" and B says "A is telling the truth" and C says "I am the spy"

Satisfiable when A is the knight, B is the spy, and C is the knave.

### 35

A says "I am not the spy" and B says "I am not the spy" and C says "I am not the spy"

The spy has to be lying, so there will be two people lying, implying there are two spies, so there are no solutions.

### 39

- If the butler is telling the truth, then so is the cook
- the cook and the gardner cannot both be telling the truth
- the gardner and the handyman are not both lying
- if the handyman is telling the truth, then the cook is lying

`A`: The butler is telling the truth

`B`: The cook is telling the truth

`C`: The gardner is telling the truth

`D`: The handyman is telling the truth

`A` &rarr; `B`

&not;`B` &or; &not;`C`

&not;(&not;`C` &or; &not;`D`) &equiv; `C` &and; `D`

`D` &rarr; &not;`B`

Satifiable when `A` is true or false, `B` is false, `C` is true, and `D` is true.
