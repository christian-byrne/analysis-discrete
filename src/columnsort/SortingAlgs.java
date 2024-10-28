import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Consumer;

public class SortingAlgs {

  private static final int INSERTION_SORT_THRESHOLD = 32;

  private static final int SAMPLE_SIZE = 64;

  /**
   * Shuffle the array using the Fisher-Yates algorithm.
   * 
   * @param array
   */
  public static void shuffleArray(int[] array) {
    for (int i = array.length - 1; i > 0; i--) {
      int index = (int) (Math.random() * (i + 1));
      int temp = array[index];
      array[index] = array[i];
      array[i] = temp;
    }
  }

  /**
   * Run the function SAMPLE_SIZE times, shuffle the array in between each run and
   * without changing the original array. Return the average time taken to run the
   * function.
   * 
   * @param array
   * @param sortingAlgorithm
   * @param funcName
   * @return average time taken to run the function in milliseconds
   */
  public static double getAverageTime(int[] array, Consumer<int[]> sortingAlgorithm) {
    double[] times = new double[SAMPLE_SIZE];
    for (int i = 0; i < SAMPLE_SIZE; i++) {
      int[] arrayCopy = array.clone();
      shuffleArray(arrayCopy);
      double startTime = System.nanoTime();
      sortingAlgorithm.accept(arrayCopy);
      double endTime = System.nanoTime();
      times[i] = (endTime - startTime);
    }
    return Arrays.stream(times).average().orElse(0) / 1_000_000.0;
  }

  public static HashMap<String, Double> benchmarkAlgs(int[] arr) {
    HashMap<String, Double> sortingAlgorithms = new HashMap<>();

    // Insertion sort
    sortingAlgorithms.put("Insertion Sort", getAverageTime(arr, (array) -> insertionSort(array, 0)));

    // Dual-Pivot QuickSort (Builtin Java)
    sortingAlgorithms.put("Dual-Pivot QuickSort", getAverageTime(arr, Arrays::sort));

    // Parallel Sort (Builtin Java)
    sortingAlgorithms.put("Parallel Sort", getAverageTime(arr, Arrays::parallelSort));

    // Merge Sort
    sortingAlgorithms.put("Merge Sort", getAverageTime(arr, SortingAlgs::mergeSort));

    // Merge Sort with Insertion Sort
    sortingAlgorithms.put("Merge Sort with Insertion Sort",
        getAverageTime(arr, SortingAlgs::mergeSortIntoInsertionSort));

    // Radix Sort
    if (!hasNegativeNumbers(arr)) {
      sortingAlgorithms.put("Radix Sort", getAverageTime(arr, SortingAlgs::radixSort));
    } else {
      sortingAlgorithms.put("Radix Sort", 1.0);
    }

    // Bubble Sort
    sortingAlgorithms.put("Bubble Sort", getAverageTime(arr, SortingAlgs::bubbleSort));

    // Selection Sort
    sortingAlgorithms.put("Selection Sort", getAverageTime(arr, SortingAlgs::selectionSort));

    // Quick Sort
    sortingAlgorithms.put("Quick Sort", getAverageTime(arr, (array) -> quickSort(array, 0, array.length - 1)));
    // Merge Sort with Selection Sort
    sortingAlgorithms.put("Merge Sort with Selection Sort",
        getAverageTime(arr, SortingAlgs::mergeSortIntoSelectionSort));

    // Merge Sort with Bubble Sort
    sortingAlgorithms.put("Merge Sort with Bubble Sort", getAverageTime(arr, SortingAlgs::mergeSortIntoBubbleSort));

    // Quick Sort with Insertion Sort
    sortingAlgorithms.put("Quick Sort with Insertion Sort",
        getAverageTime(arr, (array) -> quickSortWithInsertionSort(array, 0, array.length - 1)));

    // Quick Sort with Selection Sort
    sortingAlgorithms.put("Quick Sort with Selection Sort",
        getAverageTime(arr, (array) -> quickSortWithSelectionSort(array, 0, array.length - 1)));

    // Dual-Pivot QuickSort (Custom)
    sortingAlgorithms.put("Dual-Pivot QuickSort (Custom)",
        getAverageTime(arr, (array) -> dualPivotQuickSort2(array, 0, array.length - 1)));

    // Introspective Sort
    sortingAlgorithms.put("Introspective Sort", getAverageTime(arr, SortingAlgs::introspectiveSort));

    // Three-Way Quick Sort
    sortingAlgorithms.put("Three-Way Quick Sort",
        getAverageTime(arr, (array) -> threeWayQuickSort(array, 0, array.length - 1)));

    // Median of Three Quick Sort
    sortingAlgorithms.put("Median of Three Quick Sort",
        getAverageTime(arr, (array) -> medianOfThreeQuickSort(array, 0, array.length - 1)));

    // Heap Sort
    sortingAlgorithms.put("Heap Sort", getAverageTime(arr, SortingAlgs::heapSort));

    // Bucket Sort
    // sortingAlgorithms.put("Bucket Sort", getAverageTime(arr,
    // SortingAlgs::bucketSort));

    // Counting Sort with Insertion Sort
    // sortingAlgorithms.put("Counting Sort with Insertion Sort",
    // getAverageTime(arr, SortingAlgs::countingSortWithPartitionedMergeSort));

    // Bitonic Sort
    // sortingAlgorithms.put("Bitonic Sort", getAverageTime(arr,
    // SortingAlgs::bitonicSort));

    // // Three-Way Quick Sort with Selection Sort
    // sortingAlgorithms.put("Three-Way Quick Sort with Selection Sort",
    // getAverageTime(arr, (array) -> threeWayQuicksortWithSelectionSort(array, 0,
    // array.length - 1)));

    // // Median of Three Quick Sort with Selection Sort
    // sortingAlgorithms.put("Median of Three Quick Sort with Selection Sort",
    // getAverageTime(arr, (array) -> medianOfThreeQuickSortWithSelectionSort(array,
    // 0, array.length - 1)));

    // // Introspective Sort with Selection Sort
    // sortingAlgorithms.put("Introspective Sort with Selection Sort",
    // getAverageTime(arr, SortingAlgs::introspectiveSortWithSelectionSort));

    return sortingAlgorithms;
  }

  private static boolean hasNegativeNumbers(int[] arr) {
    for (int i : arr) {
      if (i < 0) {
        return true;
      }
    }
    return false;
  }

  private static final void insertionSort(int[] arr, int partitionStart) {
    for (int i = partitionStart + 1; i < arr.length; i++) {
      int key = arr[i];
      int j = i - 1;

      while (j >= partitionStart && arr[j] > key) {
        arr[j + 1] = arr[j];
        j--;
      }

      arr[j + 1] = key;
    }
  }

  public static void quickSortWithInsertionSort(int[] array, int low, int high) {
    if (low < high) {
      if (high - low < INSERTION_SORT_THRESHOLD) {
        insertionSort(array, low);
      } else {
        int pi = partition(array, low, high);
        quickSort(array, low, pi - 1);
        quickSort(array, pi + 1, high);
      }
    }
  }

  public static void quickSortWithSelectionSort(int[] array, int low, int high) {
    if (low < high) {
      if (high - low < INSERTION_SORT_THRESHOLD) {
        selectionSort(array);
      } else {
        int pi = partition(array, low, high);
        quickSort(array, low, pi - 1);
        quickSort(array, pi + 1, high);
      }
    }
  }

  private static void quickSort(int[] array, int low, int high) {
    if (low < high) {
      int pi = partition(array, low, high);
      quickSort(array, low, pi - 1);
      quickSort(array, pi + 1, high);
    }
  }

  private static int partition(int[] array, int low, int high) {
    int pivot = array[high];
    int i = low - 1;
    for (int j = low; j < high; j++) {
      if (array[j] < pivot) {
        i++;
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
      }
    }
    int temp = array[i + 1];
    array[i + 1] = array[high];
    array[high] = temp;
    return i + 1;
  }

  public static void mergeSort(int[] array) {
    if (array.length <= 1)
      return;
    int mid = array.length / 2;
    int[] left = Arrays.copyOfRange(array, 0, mid);
    int[] right = Arrays.copyOfRange(array, mid, array.length);
    mergeSort(left);
    mergeSort(right);
    merge(array, left, right);
  }

  public static void mergeSortIntoInsertionSort(int[] array) {
    if (array.length <= INSERTION_SORT_THRESHOLD) {
      insertionSort(array, 0);
      return;
    }
    int mid = array.length / 2;
    int[] left = Arrays.copyOfRange(array, 0, mid);
    int[] right = Arrays.copyOfRange(array, mid, array.length);
    mergeSort(left);
    mergeSort(right);
    merge(array, left, right);
  }

  public static void mergeSortIntoSelectionSort(int[] array) {
    if (array.length <= INSERTION_SORT_THRESHOLD) {
      selectionSort(array);
      return;
    }
    int mid = array.length / 2;
    int[] left = Arrays.copyOfRange(array, 0, mid);
    int[] right = Arrays.copyOfRange(array, mid, array.length);
    mergeSort(left);
    mergeSort(right);
    merge(array, left, right);
  }

  public static void mergeSortIntoBubbleSort(int[] array) {
    if (array.length <= INSERTION_SORT_THRESHOLD) {
      bubbleSort(array);
      return;
    }
    int mid = array.length / 2;
    int[] left = Arrays.copyOfRange(array, 0, mid);
    int[] right = Arrays.copyOfRange(array, mid, array.length);
    mergeSort(left);
    mergeSort(right);
    merge(array, left, right);
  }

  private static void merge(int[] array, int[] left, int[] right) {
    int i = 0, j = 0, k = 0;
    while (i < left.length && j < right.length) {
      if (left[i] <= right[j]) {
        array[k++] = left[i++];
      } else {
        array[k++] = right[j++];
      }
    }
    while (i < left.length)
      array[k++] = left[i++];
    while (j < right.length)
      array[k++] = right[j++];
  }

  public static void radixSort(int[] array) {
    int max = Arrays.stream(array).max().orElse(0);
    for (int exp = 1; max / exp > 0; exp *= 10) {
      countingSortByDigit(array, exp);
    }
  }

  private static void countingSortByDigit(int[] array, int exp) {
    int[] output = new int[array.length];
    int[] count = new int[10];

    for (int value : array)
      count[(value / exp) % 10]++;
    for (int i = 1; i < 10; i++)
      count[i] += count[i - 1];
    for (int i = array.length - 1; i >= 0; i--) {
      output[--count[(array[i] / exp) % 10]] = array[i];
    }
    System.arraycopy(output, 0, array, 0, array.length);
  }

  private static void bubbleSort(int[] array) {
    for (int i = 0; i < array.length - 1; i++) {
      for (int j = 0; j < array.length - i - 1; j++) {
        if (array[j] > array[j + 1]) {
          int temp = array[j];
          array[j] = array[j + 1];
          array[j + 1] = temp;
        }
      }
    }
  }

  private static void selectionSort(int[] array) {
    for (int i = 0; i < array.length - 1; i++) {
      int minIndex = i;
      for (int j = i + 1; j < array.length; j++) {
        if (array[j] < array[minIndex]) {
          minIndex = j;
        }
      }
      int temp = array[minIndex];
      array[minIndex] = array[i];
      array[i] = temp;
    }
  }

  public static void dualPivotQuickSort2(int[] a, int low, int high) {
    while (true) {
      int size = high - low;

      // Use insertion sort for small arrays
      if (size < 44) {
        insertionSort2(a, low, high);
        return;
      }

      // Calculate pivots based on specific points in the array
      int step = (size >> 3) * 3 + 3;
      int e1 = low + step;
      int e5 = high - step - 1;
      int e3 = (low + high) >>> 1;
      int e2 = e1 + ((e3 - e1) >>> 1);
      int e4 = e3 + ((e5 - e3) >>> 1);

      // Sort the five elements to use as pivots
      sortFiveElements(a, e1, e2, e3, e4, e5);

      int pivot1 = a[e1];
      int pivot2 = a[e5];

      // Move pivots to array ends
      swap(a, e1, low);
      swap(a, e5, high - 1);

      int left = low + 1;
      int right = high - 2;
      int k = left;

      while (k <= right) {
        if (a[k] < pivot1) {
          swap(a, k++, left++);
        } else if (a[k] > pivot2) {
          swap(a, k, right--);
        } else {
          k++;
        }
      }

      // Move pivots to their final positions
      swap(a, low, --left);
      swap(a, high - 1, ++right);

      // Sort the partitions
      dualPivotQuickSort2(a, low, left);
      dualPivotQuickSort2(a, left + 1, right);
      dualPivotQuickSort2(a, right + 1, high);
      return;
    }
  }

  // Simple insertion sort for small partitions
  private static void insertionSort2(int[] a, int low, int high) {
    for (int i = low + 1; i < high; i++) {
      int ai = a[i];
      int j = i - 1;
      while (j >= low && a[j] > ai) {
        a[j + 1] = a[j];
        j--;
      }
      a[j + 1] = ai;
    }
  }

  // Sort five specific elements in the array
  private static void sortFiveElements(int[] a, int i1, int i2, int i3, int i4, int i5) {
    if (a[i1] > a[i2])
      swap(a, i1, i2);
    if (a[i3] > a[i4])
      swap(a, i3, i4);
    if (a[i1] > a[i3])
      swap(a, i1, i3);
    if (a[i2] > a[i4])
      swap(a, i2, i4);
    if (a[i1] > a[i2])
      swap(a, i1, i2);
    if (a[i3] > a[i4])
      swap(a, i3, i4);
    if (a[i2] > a[i3])
      swap(a, i2, i3);
    if (a[i4] > a[i5])
      swap(a, i4, i5);
    if (a[i3] > a[i4])
      swap(a, i3, i4);
  }

  // Helper method to swap elements in the array
  private static void swap(int[] a, int i, int j) {
    int temp = a[i];
    a[i] = a[j];
    a[j] = temp;
  }

  private static void heapSort(int[] array) {
    int n = array.length;

    // Build heap (rearrange array)
    for (int i = n / 2 - 1; i >= 0; i--)
      heapify(array, n, i);

    // One by one extract an element from heap
    for (int i = n - 1; i > 0; i--) {
      // Move current root to end
      int temp = array[0];
      array[0] = array[i];
      array[i] = temp;

      // call max heapify on the reduced heap
      heapify(array, i, 0);
    }
  }

  private static void heapify(int[] array, int n, int i) {
    int largest = i; // Initialize largest as root
    int l = 2 * i + 1; // left = 2*i + 1
    int r = 2 * i + 2; // right = 2*i + 2

    // If left child is larger than root
    if (l < n && array[l] > array[largest])
      largest = l;

    // If right child is larger than largest so far
    if (r < n && array[r] > array[largest])
      largest = r;

    // If largest is not root
    if (largest != i) {
      int swap = array[i];
      array[i] = array[largest];
      array[largest] = swap;

      // Recursively heapify the affected sub-tree
      heapify(array, n, largest);
    }
  }

  private static void introspectiveSort(int[] array) {
    int maxDepth = (int) (2 * Math.log(array.length) / Math.log(2));
    introspectiveSort(array, 0, array.length - 1, maxDepth);
  }

  private static void introspectiveSort(int[] array, int low, int high, int maxDepth) {
    if (high - low < INSERTION_SORT_THRESHOLD) {
      insertionSort(array, low);
    } else if (maxDepth == 0) {
      heapSort(array);
    } else {
      int p = partition(array, low, high);
      introspectiveSort(array, low, p - 1, maxDepth - 1);
      introspectiveSort(array, p + 1, high, maxDepth - 1);
    }
  }

  private static void threeWayQuickSort(int[] array, int low, int high) {
    if (low < high) {
      int[] p = partition3(array, low, high);
      threeWayQuickSort(array, low, p[0] - 1);
      threeWayQuickSort(array, p[1] + 1, high);
    }
  }

  private static int[] partition3(int[] array, int low, int high) {
    int pivot = array[high];
    int i = low;
    int j = low;
    int k = high;
    while (j < k) {
      if (array[j] < pivot) {
        swap(array, i, j);
        i++;
        j++;
      } else if (array[j] == pivot) {
        j++;
      } else {
        k--;
        swap(array, j, k);
      }
    }
    swap(array, j, high);
    return new int[] { i, j };
  }

  private static int[] medianOfThreeQuickSort(int[] array, int low, int high) {
    if (low < high) {
      int p = partitionMedianOfThree(array, low, high);
      medianOfThreeQuickSort(array, low, p - 1);
      medianOfThreeQuickSort(array, p + 1, high);
    }
    return array;
  }

  private static int partitionMedianOfThree(int[] array, int low, int high) {
    int mid = low + (high - low) / 2;

    // Sort low, mid, and high to find the median pivot
    if (array[low] > array[mid])
      swap2(array, low, mid);
    if (array[low] > array[high])
      swap2(array, low, high);
    if (array[mid] > array[high])
      swap2(array, mid, high);

    // Move the median to high - 1 for partitioning
    swap2(array, mid, high - 1);
    int pivot = array[high - 1];

    int i = low;
    int j = high - 1;

    while (true) {
      // Move i to the right until we find an element >= pivot
      while (i < high - 1 && array[++i] < pivot)
        ;

      // Move j to the left until we find an element <= pivot
      while (j > low && array[--j] > pivot)
        ;

      if (i >= j)
        break;

      // Swap elements at i and j
      swap2(array, i, j);
    }

    // Place the pivot in its correct location
    swap2(array, i, high - 1);
    return i;
  }

  // Swap function to exchange elements in the array
  private static void swap2(int[] array, int i, int j) {
    int temp = array[i];
    array[i] = array[j];
    array[j] = temp;
  }

  public static void bucketSort(int[] array) {
    int n = array.length;
    if (n <= 1)
      return;

    int max = Arrays.stream(array).max().orElse(0);
    int min = Arrays.stream(array).min().orElse(0);

    // Define the number of buckets and their range
    int bucketCount = (int) Math.sqrt(n); // Optimal number of buckets is often √n
    int bucketRange = (max - min) / bucketCount + 1;

    // Create buckets as array of ArrayLists
    ArrayList<Integer>[] buckets = new ArrayList[bucketCount];
    for (int i = 0; i < bucketCount; i++) {
      buckets[i] = new ArrayList<>();
    }

    // Distribute elements into buckets
    for (int value : array) {
      int bucketIndex = (value - min) / bucketRange;
      buckets[bucketIndex].add(value);
    }

    // Sort each bucket using insertion sort or another suitable algorithm
    int index = 0;
    for (ArrayList<Integer> bucket : buckets) {
      insertionSort(bucket);
      for (int value : bucket) {
        array[index++] = value;
      }
    }
  }

  // Insertion sort for ArrayList
  private static void insertionSort(ArrayList<Integer> bucket) {
    for (int i = 1; i < bucket.size(); i++) {
      int key = bucket.get(i);
      int j = i - 1;
      while (j >= 0 && bucket.get(j) > key) {
        bucket.set(j + 1, bucket.get(j));
        j--;
      }
      bucket.set(j + 1, key);
    }
  }

  private static final int MAX_COUNTING_SORT_RANGE = 512;

  // public static void countingSortWithPartitionedMergeSort(int[] array) {
  // int n = array.length;

  // // Define initial partition boundaries
  // int start = 0;
  // while (start < n) {
  // int end = Math.min(start + MAX_COUNTING_SORT_RANGE, n);

  // // Get min and max for the current partition
  // int max = Arrays.stream(array, start, end).max().orElse(Integer.MAX_VALUE);
  // int min = Arrays.stream(array, start, end).min().orElse(Integer.MIN_VALUE);

  // if (max - min + 1 <= MAX_COUNTING_SORT_RANGE) {
  // // Use counting sort on this partition
  // countingSort(array, start, end, min, max);
  // } else {
  // // Use merge sort on this partition
  // mergeSort(array, start, end);
  // }
  // start = end;
  // }
  // }

  // private static void countingSort(int[] array, int start, int end, int min,
  // int max) {
  // int range = max - min + 1;
  // int[] count = new int[range];
  // int[] output = new int[end - start];

  // // Count occurrences of each value in the partition
  // for (int i = start; i < end; i++) {
  // count[array[i] - min]++;
  // }

  // // Calculate cumulative count
  // for (int i = 1; i < range; i++) {
  // count[i] += count[i - 1];
  // }

  // // Place elements in output array based on counts
  // for (int i = end - 1; i >= start; i--) {
  // output[count[array[i] - min] - 1] = array[i];
  // count[array[i] - min]--;
  // }

  // // Copy sorted partition back to the original array
  // System.arraycopy(output, 0, array, start, end - start);
  // }

  // private static void mergeSort(int[] array, int start, int end) {
  // if (end - start < 2)
  // return;
  // int mid = (start + end) / 2;

  // mergeSort(array, start, mid);
  // mergeSort(array, mid, end);
  // merge2(array, start, mid, end);
  // }

  // private static void merge2(int[] array, int start, int mid, int end) {
  // int[] left = Arrays.copyOfRange(array, start, mid);
  // int[] right = Arrays.copyOfRange(array, mid, end);

  // int i = 0, j = 0, k = start;
  // while (i < left.length && j < right.length) {
  // array[k++] = (left[i] <= right[j]) ? left[i++] : right[j++];
  // }

  // while (i < left.length) {
  // array[k++] = left[i++];
  // }
  // while (j < right.length) {
  // array[k++] = right[j++];
  // }
  // }

  private static void bitonicSort(int[] array) {
    int n = array.length;
    for (int i = 2; i <= n; i = i * 2) {
      for (int j = 0; j < n; j += i) {
        bitonicMerge(array, j, i);
      }
    }
  }

  private static void bitonicMerge(int[] array, int start, int size) {
    if (size > 1) {
      int mid = size / 2;
      for (int i = start; i < start + mid; i++) {
        if (array[i] > array[i + mid]) {
          swap(array, i, i + mid);
        }
      }
      bitonicMerge(array, start, mid);
      bitonicMerge(array, start + mid, mid);
    }
  }

  private static void threeWayQuicksortWithSelectionSort(int[] array, int low, int high) {
    if (low < high) {
      if (high - low < INSERTION_SORT_THRESHOLD) {
        selectionSort(array);
      } else {
        int[] p = partition3(array, low, high);
        threeWayQuicksortWithSelectionSort(array, low, p[0] - 1);
        threeWayQuicksortWithSelectionSort(array, p[1] + 1, high);
      }
    }
  }

  private static void medianOfThreeQuickSortWithSelectionSort(int[] array, int low, int high) {
    if (low < high) {
      if (high - low < INSERTION_SORT_THRESHOLD) {
        selectionSort(array);
      } else {
        int p = partitionMedianOfThree(array, low, high);
        medianOfThreeQuickSortWithSelectionSort(array, low, p - 1);
        medianOfThreeQuickSortWithSelectionSort(array, p + 1, high);
      }
    }
  }

  private static void introspectiveSortWithSelectionSort(int[] array) {
    int maxDepth = (int) (2 * Math.log(array.length) / Math.log(2));
    introspectiveSortWithSelectionSort(array, 0, array.length - 1, maxDepth);
  }

  private static void introspectiveSortWithSelectionSort(int[] array, int low, int high, int maxDepth) {
    if (high - low < INSERTION_SORT_THRESHOLD) {
      selectionSort(array);
    } else if (maxDepth == 0) {
      heapSort(array);
    } else {
      int p = partition(array, low, high);
      introspectiveSortWithSelectionSort(array, low, p - 1, maxDepth - 1);
      introspectiveSortWithSelectionSort(array, p + 1, high, maxDepth - 1);
    }
  }

  public static void printTableResults(HashMap<String, Double> sortingAlgorithms) {
    System.out.println("Sorting Algorithm\t\tExecution Time (ms)\tExecution Time (s)");
    sortingAlgorithms.entrySet().stream()
        .sorted((e1, e2) -> e1.getValue().compareTo(e2.getValue()))
        .forEach(e -> printTableRow(e.getKey(), String.format("%.6f", e.getValue())));
  }

  public static void printTableRow(String label, String data) {
    int labelCellWidth = 40;
    int dataCellWidth = 20;
    int secondDataCellWidth = 15;

    // Parse data as a double to handle possible decimals
    double dataInMillis = Double.parseDouble(data);
    double dataInSeconds = dataInMillis / 1000.0;

    // Print the formatted row
    System.out.printf("%-" + labelCellWidth + "s%-" + dataCellWidth + ".4f%-" + secondDataCellWidth + ".6f\n", label,
        dataInMillis, dataInSeconds);
  }

}
