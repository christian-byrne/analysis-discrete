def list_divisors(n):
    divisors = []
    for i in range(1, n + 1):
        if n % i == 0:
            divisors.append(i)
    return list(set(divisors))


def print_divisors(n):
    divisors = list_divisors(n)
    print(f"Divisors of {n}: {divisors}")


def brute_force_gcd(n1, n2):
    divisors1 = list_divisors(n1)
    divisors2 = list_divisors(n2)
    divisors1.sort(reverse=True)
    divisors2.sort(reverse=True)
    print(f"Divisors of {n1}: {divisors1}\n")
    print(f"Divisors of {n2}: {divisors2}\n")
    print(f"Common divisors: {set(divisors1) & set(divisors2)}\n")
    print(f"GCD of {n1} and {n2} is: {max(set(divisors1) & set(divisors2))}\n")


# brute_force_gcd(20, 52)
# def mode(num_list):
#     num_list.sort()

#     def mode_helper(nums, leader_stack, candidate_stack):
#         if len(nums) == 0:
#             return leader_stack[-1]

#         cur_num = nums[0]
#         if len(leader_stack) > 0 and cur_num == leader_stack[-1]:
#             leader_stack.append(cur_num)
#             return mode_helper(nums[1:], leader_stack, candidate_stack)
#         else:
#             if len(candidate_stack) > 0 and cur_num == candidate_stack[-1]:
#                 candidate_stack.append(cur_num)
#                 if len(candidate_stack) > len(leader_stack):
#                     leader_stack = candidate_stack
#                     candidate_stack = []
#             else:
#                 candidate_stack = [cur_num]
#         return mode_helper(nums[1:], leader_stack, candidate_stack)
#     return mode_helper(num_list[1:], [num_list[0]], [])

def mode(num_list):
    num_list.sort()

    def mode_helper(nums, leader_deque):
        if len(nums) == 0:
            return leader_deque[-1]

        cur_num = nums.pop(0)
        print(f"Leader stack: {leader_deque} | Current number: {cur_num}")
        if len(leader_deque) == 0:
            return mode_helper(nums, [cur_num])
        if cur_num == leader_deque[-1]:
            return mode_helper(nums, leader_deque + [cur_num])
        if cur_num == leader_deque[0]:
            leader_deque = [cur_num] + leader_deque
            if leader_deque[len(leader_deque) // 2] == cur_num:
                return mode_helper(nums, leader_deque[::-1])
        if len(set(leader_deque)) == 1:
            return mode_helper(nums, [cur_num] + leader_deque)
        else:
            return mode_helper(nums, [cur_num] + leader_deque[leader_deque.count(leader_deque[0]):])
    return mode_helper(num_list, [])


x = mode([1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1])
print(x)

assert mode(
    [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1]
) == 1, "Test case 1 failed"

assert mode(
    [1, 2, 2, 3]
) == 2, "Test case 2 failed"

assert mode(
    [1, 1, 2]
) == 1, "Test case 3 failed"

assert mode(
    [1, 1]
) == 1, "Test case 4 failed"

assert mode(
    [1]
) == 1, "Test case 5 failed"


assert mode(
    [2, 43, 45, 4, 2, 6, 2]
) == 2, "Test case 6 failed"


assert mode(
    [89, 23, 100, 67, 3, 7, 89]
) == 89, "Test case 7 failed"

print("All test cases passed!")
