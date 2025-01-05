partial_products = []
base = 8
for i, digit in enumerate(reversed([7, 6, 3])):
    carry = 0
    for j, digit2 in enumerate(reversed([1, 4, 7])):
        product = digit * digit2 + carry
        carry = product // base
        if i + j >= len(partial_products):
            partial_products.append(product % base * 10**)
        else:
            partial_products[i + j] += product % base
        
print(partial_products)