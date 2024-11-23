def selection_sort_with_swap_count(lst):
    swap_count = 0
    comparison_count = 0
    n = len(lst)
    
    for i in range(n - 1):
        # Assume the minimum is the first element
        min_index = i
        for j in range(i + 1, n):
            if lst[j] < lst[min_index]:
                comparison_count += 1
                min_index = j
        
        # Swap if the found minimum is not already in place
        if min_index != i:
            lst[i], lst[min_index] = lst[min_index], lst[i]
            swap_count += 1
            print(f"After swap {swap_count}: {lst}")
    

    print(f"Total swaps: {swap_count}")
    return lst

# Worst-case input for a list of length 5
worst_case_list = [2, 4, 5, 3, 1]
sorted_list = selection_sort_with_swap_count(worst_case_list)
