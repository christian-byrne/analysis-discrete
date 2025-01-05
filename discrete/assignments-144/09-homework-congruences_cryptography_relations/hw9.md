## Section 4.5 - Question 1 

Which memory locations are assigned by the hashing function h(k) = k mod 97 to the records of insurance company customers with these Social Security numbers?

#### a


> 034567981

$h(34567981) = 34567981 \mod 97 = 81$

#### b


> 183211232

$h(183211232) = 183211232 \mod 97 = 61$


#### c


> 220195744

$h(220195744) = 220195744 \mod 97 = 0$


#### d


> 987255335

$h(987255335) = 987255335 \mod 97 = 0$



## Section 4.5 - Question 3a 

> A parking lot has 31 visitor spaces, numbered from 0 to 30. Visitors are assigned parking spaces using the hashing function h(k) = k mod 31, where k is the number formed from the first three digits on a visitor’s license plate. Which spaces are assigned by the hashing function to cars that have these first three digits on their license plates:


- $h(317) = 317 \mod 31 = 7$
- $h(918) = 918 \mod 31 = 19$
- $h(007) = 7 \mod 31 = 7$
- $h(100) = 100 \mod 31 = 7$
- $h(111) = 111 \mod 31 = 18$
- $h(310) = 310 \mod 31 = 0$


> Describe a procedure visitors should follow to find a free parking space, when the space they are assigned is occupied.

If the space is occupied, increment until a free space is found.


## Section 4.5 - Question 19b 

> The United States Postal Service (USPS) sells money orders identified by an 11-digit number $x_1x_2 … x_{11}$. The first ten digits identify the money order; $x_11$ is a check digit that satisfies $x_{11} = x_1 + x_2 + … + x_{10} \mod 9$. 
> 
> Determine whether `88382013445` is a valid USPS money order identification number.


$x_{11} = 8 + 8 + 3 + 8 + 2 + 0 + 1 + 3 + 4 + 4 \mod 9 = 41 \mod 9 = 5$


The number is valid.



## Section 4.5 - Question 20b 

> The United States Postal Service (USPS) sells money orders identified by an 11-digit number $x_1x_2 … x_{11}$. The first ten digits identify the money order; $x_11$ is a check digit that satisfies $x_{11} = x_1 + x_2 + … + x_{10} \mod 9$.
> 
> One digit in the postal money order `6702120Q988` is smudged. Can you recover the smudged digit?

- $x_1 + x_2 + … + x_{10} = 6 + 7 + 0 + 2 + 1 + 2 + 0 + Q + 9 + 8 = 35 + Q$
- $x_{11} = 8$
- $35 + Q \equiv 8 \mod 9$ 
- $Q \equiv 8 - 35 \equiv 8 - (-1) \equiv 9 \mod 9$
- $Q \equiv 9 \mod 9$
- The solutions to the congruence are the integers x such that $x \equiv 9 \mod 9$ and $0 \leq x \leq 9$ (because it's a digit)
    - $Q = 0, 9$
- With multiple valid solutions, it is not possible to determine the correct digit.



## Section 4.5 - Question 21b 

> The United States Postal Service (USPS) sells money orders identified by an 11-digit number $x_1x_2 … x_{11}$. The first ten digits identify the money order; $x_11$ is a check digit that satisfies $x_{11} = x_1 + x_2 + … + x_{10} \mod 9$.
> 
> One digit in the postal money order `850Q9103858` is smudged. Can you recover the smudged digit?


- $x_1 + x_2 + … + x_{10} = 8 + 5 + 0 + Q+ 9 + 1 + 0 + 3 + 8 + 5 = 39 + Q$
- $x_{11} = 8$
- $39 + Q \equiv 8 \mod 9$ 
- $Q \equiv 8 - 39 \equiv 8 - (-6) \equiv 14 \equiv 5 \mod 9$
- $Q \equiv 5 \mod 9$
- The solutions to the congruence are the integers x such that $x \equiv 5 \mod 9$ and $0 \leq x \leq 9$ (because it's a digit)
    - $Q = 5$
- The smudged digit is 5.

## Section 4.5 - Question 24b 

> The United States Postal Service (USPS) sells money orders identified by an 11-digit number $x_1x_2 … x_{11}$. The first ten digits identify the money order; $x_{11}$ is a check digit that satisfies $x_{11} = x_1 + x_2 + … + x_{10} \mod 9$.
>
> Determine the check digit for the UPC that has these initial 11 digits: `63623991346`


- $x_1, x_2, …, x_{10} = 6, 3, 6, 2, 3, 9, 9, 1, 3, 4$
- $x_{11} = 6$
- ***Answer*: The check digit is $6$**
- $x_1 + x_2 + … + x_{10} = 6 + 3 + 6 + 2 + 3 + 9 + 9 + 1 + 3 + 4 = 46$
- $46 \mod 9 = 1$
- $1 \neq 6$ &rarr; invalid UPC 
- Corrected: `636239913461`


## Section 4.5 - Question 28a 

> Some airline tickets have a 15-digit identification number $a_1a_2 … a_{15}$, where $a_{15}$ is a check digit that equals $a_1a_2 … a_{14} \mod 7$.
>
> Find the check digit $a_{15}$ that follows these initial 14 digits of an airline ticket identification number: `10237424413392`


- $a_1a_2…a_{14} = 10237424413392$
- $a_1a_2 … a_{14} \mod 7 = 10237424413392 \mod 7 = 1$
- $a_{15} = 1$
- **Check Digit**: $1$


## Section 4.5 - Question 33 

> Periodicals are identified using an International Standard Serial Number (ISSN). An ISSN consists of two blocks of four digits. The last digit in the second block is a check digit. This check digit is determined by the congruence $d_8 \equiv 3d_1 + 4d_2 + 5d_3 + 6d_4 + 7d_5 + 8d_6 + 9d_7 \mod 11$. When $d_8 \equiv 10 \mod 11$, we use the letter `X` to represent $d_8$ in the code.
>
> Are each of these eight-digit codes possible ISSNs? That is, do they end with a correct check digit?

#### a) 1059−1027

- $3d_1 + 4d_2 + 5d_3 + 6d_4 + 7d_5 + 8d_6 + 9d_7 =$ 
- $3(1) + 4(0) + 5(5) + 6(9) + 7(1) + 8(0) + 9(2)=$ 
- $3 + 0 + 25 + 54 + 7 + 0 + 18 = 107$
- $107 \mod 11 = 8$
- Not a valid ISSN


#### b) 0002−9890

- $3d_1 + 4d_2 + 5d_3 + 6d_4 + 7d_5 + 8d_6 + 9d_7 =$
- $3(0) + 4(0) + 5(0) + 6(0) + 7(9) + 8(8) + 9(9)=$
- $0 + 0 + 0 + 0 + 63 + 64 + 81 = 208$
- $208 \mod 11 = 10$
- Not a valid ISSN



#### c) 1530−8669

- $3d_1 + 4d_2 + 5d_3 + 6d_4 + 7d_5 + 8d_6 + 9d_7 =$
- $3(1) + 4(5) + 5(3) + 6(0) + 7(8) + 8(6) + 9(6)=$
- $3 + 20 + 15 + 0 + 56 + 48 + 54 = 196$
- $196 \mod 11 = 9$
- Valid ISSN



#### d) 1007−120X

- $3d_1 + 4d_2 + 5d_3 + 6d_4 + 7d_5 + 8d_6 + 9d_7 =$
- $3(1) + 4(0) + 5(0) + 6(7) + 7(1) + 8(2) + 9(0)=$
- $3 + 0 + 0 + 42 + 7 + 16 + 0 = 68$
- $68 \mod 11 = 2$
- Not a valid ISSN


## Section 4.6 - Question 1 

> Encrypt the message `DO NOT PASS GO` by translating the letters into numbers, applying the given encryption function, and then translating the numbers back into letters.

#### a) 

> f(p) = (p + 3) mod 26 (the Caesar cipher)

- [3, 14, ' ', 13, 14, 19, ' ', 15, 0, 18, 18, ' ', 6, 14]
- [6, 17, ' ', 16, 17, 22, ' ', 18, 3, 21, 21, ' ', 9, 17]
- GR QRW SDVV JR


#### b) 

> f(p) = (p + 13) mod 26

- [3, 14, ' ', 13, 14, 19, ' ', 15, 0, 18, 18, ' ', 6, 14]
- [16, 1, ' ', 0, 1, 6, ' ', 2, 13, 5, 5, ' ', 19, 1]
- QB ABG CNFF TB



#### c) 

> f(p) = (3p + 7) mod 26


- [3, 14, ' ', 13, 14, 19, ' ', 15, 0, 18, 18, ' ', 6, 14]
- [16, 23, ' ', 20, 23, 12, ' ', 0, 7, 9, 9, ' ', 25, 23]
- QX UXM AHJJ ZX



## Section 4.6 - Question 3 


> Encrypt the message WATCH YOUR STEP by translating the letters into numbers, applying the given encryption function, and then translating the numbers back into letters.

#### a) 

> f(p) = (p + 14) mod 26

- [22, 0, 19, 2, 7, ' ', 24, 14, 20, 17, ' ', 18, 19, 4, 15]
- [10, 14, 7, 16, 21, ' ', 12, 2, 8, 5, ' ', 6, 7, 18, 3]:
- KOHQV MCIF GHSD


#### b) 

> f(p) = (14p + 21) mod 26


- [22, 0, 19, 2, 7, ' ', 24, 14, 20, 17, ' ', 18, 19, 4, 15]
- [17, 21, 1, 23, 15, ' ', 19, 9, 15, 25, ' ', 13, 1, 25, 23]:
- RVBXP TJPZ NBZX

#### c) 

> f(p) = (−7p + 1) mod 26

- [22, 0, 19, 2, 7, ' ', 24, 14, 20, 17, ' ', 18, 19, 4, 15]
- [3, 1, 24, 13, 4, ' ', 15, 7, 17, 12, ' ', 5, 24, 25, 0]:
- DBYNE PHRM FYZA



## Section 4.6 - Question 4 

> Decrypt these messages that were encrypted using the Caesar cipher.


#### a)

- EOXH MHDQV
- [4, 14, 23, 7, ' ', 12, 7, 3, 16, 21]
- [1, 11, 20, 4, ' ', 9, 4, 0, 13, 18]:
- BLUE JEANS


#### b)


- WHVW WRGDB
- [22, 7, 21, 22, ' ', 22, 17, 6, 3, 1]
- [19, 4, 18, 19, ' ', 19, 14, 3, 0, 24]:
- TEST TODAY


#### c)

- HDW GLP VXP
- [7, 3, 22, ' ', 6, 11, 15, ' ', 21, 23, 15]
- [4, 0, 19, ' ', 3, 8, 12, ' ', 18, 20, 12]:
- EAT DIM SUM




## Section 4.6 - Question 6 

> Suppose that when a long string of text is encrypted using a shift cipher $f(p) = (p + k) \mod 26$, the most common letter in the ciphertext is `X`. What is the most likely value for $k$, assuming that the distribution of letters in the text is typical of English text?

Page 312: *"The nine most common letters in English text and their approximate relative frequencies are E 13%, T 9%, A 8%, O 8%, I 7%, N 7%, S 7%, H 6%, and R 6%"*

- The most common letter in English text is `E` with a frequency of 13%.
- The most common letter in the ciphertext is `X`.
- E's index is 4.
- X's index is 23.
- $4 + k \equiv 23 \mod 26$
- $k \equiv 23 - 4 \equiv 19 \mod 26$
- **The most likely value for $k$ is 19**
- For example, "E IS VERY COMMON IN ENGLISH TEXT EEEEE" shifted by 19 = "X BL OXKR VHFFHG BG XGZEBLA MXQM XXXXX"
  



## Section 4.6 - Question 8 

> Suppose that the ciphertext DVE CFMV KF NFEUVI, REU KYRK ZJ KYV JVVU FW JTZVETV was produced by encrypting a plaintext message using a shift cipher. What is the original plaintext?


- DVE CFMV KF NFEUVI, REU KYRK ZJ KYV JVVU FW JTZVETV
- [3, 21, 4, ' ', 2, 5, 12, 21, ' ', 10, 5, ' ', 13, 5, 4, 20, 21, 8, ',', ' ', 17, 4, 20, ' ', 10, 24, 17, 10, ' ', 25, 9, ' ', 10, 24, 21, ' ', 9, 21, 21, 20, ' ', 5, 22, ' ', 9, 19, 25, 21, 4, 19, 21]
- Most common number in list is 21
- E's index is 4
- $4 + k \equiv 21 \mod 26$
- $k \equiv 21 - 4 \equiv 17 \mod 26$ 
- Decryption function: $f(p) = (p - 17) \mod 26$
- [12, 4, 13, ' ', 11, 14, 21, 4, ' ', 19, 14, ' ', 22, 14, 13, 3, 4, 17, ',', ' ', 0, 13, 3, ' ', 19, 7, 0, 19, ' ', 8, 18, ' ', 19, 7, 4, ' ', 18, 4, 4, 3, ' ', 14, 5, ' ', 18, 2, 8, 4, 13, 2, 4]:
- MEN LOVE TO WONDER, AND THAT IS THE SEED OF SCIENCE


## Section 4.6 - Question 14 

> Encrypt the message GRIZZLY BEARS using blocks of five letters and the transposition cipher based on the permutation of {0, 1, 2, 3, 4} with σ(0) = 2, σ(1) = 4, σ(2) = 0, σ(3) = 1, and σ(4) = 3. For this exercise, use the letter X as many times as necessary to fill out the final block of fewer then five letters.

- Target = "GRIZZLY BEARS"
- Chunk size = 5
- Padding Lenth = 5 - (len("GRIZZLY BEARS") % 5) = 2
- Padded target = "GRIZZLY BEARS" + 2 * "X" = "GRIZZLY BEARSXX"
- Key: [2, 3, 0, 4, 1]
- "GRIZZLYBEARS".permute([2, 4, 0, 1, 3]) = "IZGZRBELAYXXRXS"
- "GRIZZLY BEARS".permute([2, 4, 0, 1, 3]) = "IZGZR BLEYSXAXR"

## Section 4.6 - Question 15 

> Decrypt the message EABW EFRO ATMR ASIN, which is the ciphertext produced by encrypting a plaintext message using the transposition cipher with blocks of four letters and the permutation σ of {0, 1, 2, 3} defined by σ(0) = 2, σ(1) = 0, σ(2) = 3, and σ(3) = 1.


- Target = "EABW EFRO ATMR ASIN"
- key = [1, 3, 0, 2]
- Inverted key = [key.index(i) for i in range(len(key))] = [2, 0, 3, 1]
- Key is not self-reciprocal
- "EAB WEFR OATM RASIN".permute([2, 0, 3, 1]) = "BEWAF REAOT  MARNSXI"
- "EABWEFROATMRASIN".permute([2, 0, 3, 1]) = "BEWAREOFMARTIANS"



## Section 4.6 - Question 17 


> Suppose you have intercepted a ciphertext message and when you determine the frequencies of letters in this message, you find the frequencies are similar to the frequency of letters in English text. Which type of cipher do you suspect was used?

"Character or monoalphabetic cipher" was likely used (p. 313).



## Section 9.1 - Question 1a 

> List the ordered pairs in the relation R from A = {0, 1, 2, 3, 4} to B = {0, 1, 2, 3}, where(a, b) ∈ R if and only if a = b.


R = {(a, b) ∈ A × B | a = b} = {(0, 0), (1, 1), (2, 2), (3, 3)}

## Section 9.1 - Question 3a 

> On the set {1, 2, 3, 4}, decide whether the relation {(2, 2), (2, 3), (2, 4), (3, 2), (3, 3), (3, 4)} is reflexive, whether it is symmetric, whether it is antisymmetric, and whether it is transitive.

- Reflexive: No, because (1, 1) ∉ R and (4, 4) ∉ R
- Symmetric: No, because (2, 4) ∈ R and (4, 2) ∉ R
- Antisymmetric: No, because (2, 3) ∈ R and (3, 2) ∈ R
- Transitive: Yes, when a R b and b R c, then a R c

## Section 9.1 - Question 5 


#### a) Everyone who has visited a has also visited b.

- Reflexive: Yes, because everyone who has visited a page has visited that page.
- Symmetric: Yes, because if a has same visitors as b, then b has same visitors as a.
- Antisymmetric: No, because two different websites might have had the same visitors.
- Transitive: Yes, because if a has same visitors as b and b has same visitors as c, then a has same visitors as c.


#### b) There are no common links found on both a and b.

- Reflexive: No, because a page has common links with itself.
- Symmetric: Yes, because if a has no common links with b, then b has no common links with a.
- Antisymmetric: No, because there can be two distinct pages with no common links.
- Transitive: No, because a link might be common between a and c and it is still possible that there are no common links between a and b and b and c.


#### c) There is at least one common link on a and b.

- Reflexive: No, because there exists pages with no links whatsoever
- Symmetric: Yes, because if a has a common link with b, then b has a common link with a.
- Antisymmetric: No, because two different pages might have the same link.
- Transitive: No, because a link might be common between a and c and it's still possible that there are no common links between a and b and b and c.


#### d) there is a Web page that includes links to both a and b.

- Reflexive: No, because there may be a page that is not linked to by any other page including itself.
- Symmetric: Yes because if a page includes links to a and b, then there is page that includes links to b and a.
- Antisymmetric: No, because two distinct pages might be linked to by the same page.
- Transitive: No.




## Section 9.1 - Question 6c 

> Determine whether the relation R on the set of all real numbers where (x, y) ∈ R if and only if x − y is a rational number is reflexive, symmetric, antisymmetric, and/or transitive, 


- Reflexive: Yes
  - for every real number, the number minus itself is 0, which is a rational number.
- Symmetric: Yes
  - if (x, y) ∈ R, then x - y is rational
  - if the relation is symmetric, then (y, x) ∈ R, which implies y - x is rational
  - y - x = -1 * (x - y)
  - if x - y = p/q for p, q ∈ Z, then y - x = -p/q
  - y - x = -p/q, which is also a rational number
- Antisymmetric: No
  - counterexample: x = 1, y = 2
  - x - y = 1 - 2 = -1, which is rational
  - y - x = 2 - 1 = 1, which is rational
- Transitive: Yes
  - if (x, y) ∈ R and (y, z) ∈ R, then x - y and y - z are rational
  - x - y = p/q, for p, q ∈ Z, q ≠ 0 
  - x = y + p/q
  - y - z = r/s, for r, s ∈ Z, s ≠ 0
  - z = y + r/s
  - x - z = y + p/q - y - r/s = p/q - r/s, which is a rational number minus a rational number, which is a rational number.

## Section 9.1 - Question 7 

Determine whether the relation R on the set of all integers is reflexive, symmetric, antisymmetric, and/or transitive, where (x, y) ∈ R if and only if

**a)** x ≠ y.

Not reflexive bc x = x. Symmetric bc if x ≠ y, then y ≠ x. Not antisymmetric bc if x ≠ y and y ≠ x, then x ≠ y. Transitive bc if x ≠ y and y ≠ z, then x ≠ z.

**b)** xy ≥ 1.


Not reflexive bc 0 ∈ Z and 0 * 0 = 0 < 1. Symmetric bc if xy ≥ 1, then yx ≥ 1. Not antisymmetric bc 5 * 2 ≥ 10 and 2 * 5 ≥ 10. Transitive bc if xy ≥ 1 and yz ≥ 1, then (xy)(yz) ≥ 1 * 1 = 1.

**c)** x = y + 1 or x = y − 1.

Not reflexive bc x ≠ x + 1 and x ≠ x - 1. Symmetric bc if x = y + 1, then y = x - 1 and if x = y -1, then y = x + 1. Not antisymmetric bc if x = y + 1 and y = x + 1, then x ≠ y. Not transitive bc 2 = 1 + 1 and 1 = 0 + 1, but 2 ≠ 0 + 1 and 2 ≠ 0 - 1.

**d)** x ≡ y (mod 7).

Reflexive bc x ≡ x (mod 7). Symmetric bc if x ≡ y (mod 7), then y ≡ x (mod 7). Not antisymmetric bc 21 ≡ 7 (mod 7) and 7 ≡ 21 (mod 7) and 7 ≠ 21. Transitive bc if x ≡ y (mod 7) and y ≡ z (mod 7), then x ≡ z (mod 7).

**e)** x is a multiple of y.

Reflexive bc every integer is a multiple of itself. Not symmetric bc 21 is a multiple of 7, but 7 is not a multiple of 21. Antisymmetric bc if the numbers are different, then one is a multiple of the other, but not vice versa. Transitive bc if x is a multiple of y and y is a multiple of z, then x is a multiple of z.

**f)** x and y are both negative or both nonnegative.

Reflexive bc x and y are both negative or both nonnegative. Symmetric bc if x and y are both negative or both nonnegative, then y and x are both negative or both nonnegative. Not antisymmetric bc -1 and -2 are both negative and -2 and -1 are both negative, but -1 ≠ -2. Transitive bc the symmetry passes through.

**g)** x = y^2.

Not reflexive bc x ≠ x^2 when x ≠ 0 and x ≠ 1. Not symmetric bc if x = y^2, then y = sqrt(x) and if y = x^2, then x = sqrt(y). Antisymmetric bc x = y^2 and y = x^2 only when x = y = 0 or x = y = 1. Not transitive bc 16 = 4^2 and 4 = 2^2, but 16 ≠ 2^2.

**h)** x ≥ y^2.

Not reflexive because x ≠ x^2 when x ≠ 0 and x ≠ 1. Not symmetric because, for example, if x ≥ y^2, then y^2 ≤ x when x and y are greater than 1. Antisymmetric because if x ≥ y^2 and y ≥ x^2, then x = y = 0 or x = y = 1. Transitive because if x ≥ y^2 and y ≥ z^2, then x ≥ z^2.



## Section 9.1 - Question 8 

> Show that the relation R = ∅ on a nonempty set S is symmetric and transitive, but not reflexive.

- **Symmetric**: Symmetric because ∀x, y ∈ S, if (x, y) ∈ R, then (y, x) ∈ R. Since there are no elements in R, the hypothesis "if (x, y) ∈ R" is always false, and the condition is therefore always vacuously true.
- **Transitive**: Transitive because ∀x, y, z ∈ S, if (x, y) ∈ R and (y, z) ∈ R, then (x, z) ∈ R. Since there are no elements in R, the hypothesis if (x, y) ∈ R and (y, z) ∈ R is always false, and the condition is therefore always vacuously true.
- **Reflexive**: Not reflexive because ∃x ∈ S such that (x, x) ∉ R. In particular, every x ∈ S is such that (x, x) ∉ R since R = ∅.


## Section 9.1 - Question 14 

> Which relations in Exercise 6 are irreflexive?
>
> The relation R on the set of all real numbers where (x, y) ∈ R if and only if


- **a)** x + y = 0.
  - Not irreflexive because 0 + 0 = 0 so (0, 0) ∈ R.
  - If zero is not in the domain, then the relation is irreflexive.
- **b)** x = ±y.
  - Not irreflexive because 0 = ±0 so (0, 0) ∈ R.
  - If zero is not in the domain, then the relation is irreflexive.
- **c)** x − y is a rational number.
  - Not irreflexive because x - x = 0, which is a rational number so ∀x ∈ R, (x, x) ∈ R.
- **d)** x = 2y.
  - Not irreflexive because 0 = 2 * 0 so (0, 0) ∈ R.
  - If zero is not in the domain, then the relation is irreflexive.
- **e)** xy ≥ 0.
  - Not irreflexive because 1 * 1 = 1 and 1 ≥ 1 so (1, 1) ∈ R. 
- **f)** xy = 0.
  - Not irreflexive because 0 * 0 = 0 so (0, 0) ∈ R.
  - If zero is not in the domain, then the relation is irreflexive because !∃x, y ∈ R such that x * y = 0, indicating that R = ∅, which is irrelexive vacuously since for all x ∈ R, (x, x) ∉ R. 
- **g)** x = 1.
  - Not irreflexive because x = 1 and y is unbounded so (1, 1) ∈ R. 
- **h)** x = 1 or y = 1.
  - Not irreflexive because when x = 1 and y = 1, x = 1 or y = 1 so (1, 1) ∈ R.
  - If "or" is supposed to be an exclusive or, then the relation is irreflexive because the only possible symmetrical pair would have been (1, 1) and (1, 1) would not be in the relation.

## Section 9.1 - Question 21 


> Which relations in Exercise 6 are asymmetric?
>
> The relation R on the set of all real numbers where (x, y) ∈ R if and only if

0 is a real number because it is on the number line.

- **a)** x + y = 0.
  - Not asymmetric because 0 + 0 = 0 so (0, 0) ∈ R and (0, 0) ∈ R.
- **b)** x = ±y.
  - Not asymmetric because 0 = ±0 so (0, 0) ∈ R and (0, 0) ∈ R.
- **c)** x − y is a rational number.
  - Not asymmetric because 0 - 0 = 0 so (0, 0) ∈ R and (0, 0) ∈ R.
- **d)** x = 2y.
  - Not asymmetric because 0 = 2 * 0 so (0, 0) ∈ R and (0, 0) ∈ R.
- **e)** xy ≥ 0.
  - Not asymmetric because 1 * 1 = 1 and 1 ≥ 1 so (1, 1) ∈ R and (1, 1) ∈ R.
- **f)** xy = 0.
  - Not asymmetric because 0 * 0 = 0 so (0, 0) ∈ R and (0, 0) ∈ R.
- **g)** x = 1.
  - Not asymmetric because x = 1 and y is unbounded so (1, 1) ∈ R and (1, 1) ∈ R.
- **h)** x = 1 or y = 1.
  - Not asymmetric because when x = 1 and y = 1, x = 1 or y = 1 so (1, 1) ∈ R and (1, 1) ∈ R.



## Section 9.1 - Question 27 


> Let R be the relation R ={(a, b) | a divides b} on the set of positive integers. Find

#### a) 

> $R^{−1}$


$R^{-1} = \{(b, a) | a \text{ divides } b\}$
  

#### b) 

> $\overline{R}$


$\overline{R} = \{(a, b) | a \text{ does not divide } b\}$

