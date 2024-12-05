

def insertion_sort(arr):
    for i in range(1, length(arr)):
        cur = arr[i]
        j = i - 1

        while j >= 0 and cur < arr[j]:
            arr[j + 1] = arr[j]
            j -= 1

        arr[j + 1] = cur

