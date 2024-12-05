comparisons_count = 0
merge_calls_count = 0
N = 0

def three_way_merge_sort(li: list, low: int, high: int) -> list:
    """Sorts a list using three-way merge sort."""
    if low < high:
        mid1 = low + (high - low) // 3
        mid2 = low + 2 * (high - low) // 3
        three_way_merge_sort(li, low, mid1)
        three_way_merge_sort(li, mid1 + 1, mid2)
        three_way_merge_sort(li, mid2 + 1, high)
        merge(li, low, mid1, mid2, high)
    return li

def merge(li: list, low: int, mid1: int, mid2: int, high: int) -> list:
    """Merges three sorted sublists into one sorted list. Counts comparisons for the purpose of algorithm analysis."""
    global comparisons_count
    global merge_calls_count
    # merge_calls_count += 2 * N
    merge_calls_count += 2 * (high - low + 1)
    temp = []
    i, j, k = low, mid1 + 1, mid2 + 1

    # Merge three sorted sublists
    while i <= mid1 and j <= mid2 and k <= high:
        comparisons_count += 2
        if li[i] <= li[j] and li[i] <= li[k]:
            temp.append(li[i])
            i += 1
        elif li[j] <= li[i] and li[j] <= li[k]:
            temp.append(li[j])
            j += 1
        else:
            temp.append(li[k])
            k += 1

    # Merge remaining two sublists
    while i <= mid1 and j <= mid2:
        comparisons_count += 1
        if li[i] <= li[j]:
            temp.append(li[i])
            i += 1
        else:
            temp.append(li[j])
            j += 1

    while j <= mid2 and k <= high:
        comparisons_count += 1
        if li[j] <= li[k]:
            temp.append(li[j])
            j += 1
        else:
            temp.append(li[k])
            k += 1

    while i <= mid1 and k <= high:
        comparisons_count += 1
        if li[i] <= li[k]:
            temp.append(li[i])
            i += 1
        else:
            temp.append(li[k])
            k += 1

    # Collect remaining elements from each sublist
    while i <= mid1:
        temp.append(li[i])
        i += 1

    while j <= mid2:
        temp.append(li[j])
        j += 1

    while k <= high:
        temp.append(li[k])
        k += 1

    # Place sorted elements back into the original list
    for idx in range(len(temp)):
        li[low + idx] = temp[idx]

# Testing on lists of increasing size from 1 to 27
for size in range(1, 28):
    N = size
    test_list = list(range(size, 0, -1))  # Create a reversed list of given size
    comparisons_count = 0  # Reset comparisons count
    merge_calls_count = 0  # Reset merge calls count
    three_way_merge_sort(test_list, 0, size - 1)
    print(f"- Size {size}: {comparisons_count} comparisons")
    print(f"  {merge_calls_count} merge calls")
