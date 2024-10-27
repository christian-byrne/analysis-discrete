import java.util.Arrays;
import java.util.HashMap;

public class SortingAlgs {

  private static final int INSERTION_SORT_THRESHOLD = 32;

  public static HashMap<String, Double> benchmarkAlgs(int[] arr) {
    HashMap<String, Double> sortingAlgorithms = new HashMap<>();
    long startTime, endTime;
    // Create deep copies of the array and then test various other sorting
    // algorithms
    int[] arrCopy1 = arr.clone();
    int[] arrCopy2 = arr.clone();
    int[] arrCopy3 = arr.clone();
    int[] arrCopy4 = arr.clone();
    int[] arrCopy5 = arr.clone();
    int[] arrCopy6 = arr.clone();
    int[] arrCopy7 = arr.clone();
    int[] arrCopy8 = arr.clone();
    int[] arrCopy9 = arr.clone();
    int[] arrCopy10 = arr.clone();
    int[] arrCopy11 = arr.clone();
    int[] arrCopy12 = arr.clone();
    int[] arrCopy13 = arr.clone();

    // Insertion sort
    startTime = System.nanoTime();
    insertionSort(arrCopy1, 0);
    endTime = System.nanoTime();
    sortingAlgorithms.put("Insertion Sort", (endTime - startTime) / 1_000_000.0);

    // Dual-Pivot QuickSort (Builtin Java)
    startTime = System.nanoTime();
    java.util.Arrays.sort(arrCopy2);
    endTime = System.nanoTime();
    sortingAlgorithms.put("Dual-Pivot QuickSort", (endTime - startTime) / 1_000_000.0);

    // Parallel Sort (Builtin Java)
    startTime = System.nanoTime();
    java.util.Arrays.parallelSort(arrCopy3);
    endTime = System.nanoTime();
    sortingAlgorithms.put("Parallel Sort", (endTime - startTime) / 1_000_000.0);

    // Merge Sort
    startTime = System.nanoTime();
    mergeSort(arrCopy4);
    endTime = System.nanoTime();
    sortingAlgorithms.put("Merge Sort", (endTime - startTime) / 1_000_000.0);

    // Merge Sort with Insertion Sort
    startTime = System.nanoTime();
    mergeSortIntoInsertionSort(arrCopy5);
    endTime = System.nanoTime();
    sortingAlgorithms.put("Merge Sort with Insertion Sort", (endTime - startTime) / 1_000_000.0);

    // Radix Sort
    if (!hasNegativeNumbers(arrCopy6)) {
      startTime = System.nanoTime();
      radixSort(arrCopy6);
      endTime = System.nanoTime();
      sortingAlgorithms.put("Radix Sort", (endTime - startTime) / 1_000_000.0);
    } else {
      sortingAlgorithms.put("Radix Sort", 1.0);
    }

    // Bubble Sort
    startTime = System.nanoTime();
    bubbleSort(arrCopy7);
    endTime = System.nanoTime();
    sortingAlgorithms.put("Bubble Sort", (endTime - startTime) / 1_000_000.0);

    // Selection Sort
    startTime = System.nanoTime();
    selectionSort(arrCopy8);
    endTime = System.nanoTime();
    sortingAlgorithms.put("Selection Sort", (endTime - startTime) / 1_000_000.0);

    // Quick Sort
    startTime = System.nanoTime();
    quickSort(arrCopy9, 0, arrCopy9.length - 1);
    endTime = System.nanoTime();
    sortingAlgorithms.put("Quick Sort", (endTime - startTime) / 1_000_000.0);

    // Merge Sort with Selection Sort
    startTime = System.nanoTime();
    mergeSortIntoSelectionSort(arrCopy10);
    endTime = System.nanoTime();
    sortingAlgorithms.put("Merge Sort with Selection Sort", (endTime - startTime) / 1_000_000.0);

    // Merge Sort with Bubble Sort
    startTime = System.nanoTime();
    mergeSortIntoBubbleSort(arrCopy11);
    endTime = System.nanoTime();
    sortingAlgorithms.put("Merge Sort with Bubble Sort", (endTime - startTime) / 1_000_000.0);

    // Quick Sort with Insertion Sort
    startTime = System.nanoTime();
    quickSortWithInsertionSort(arrCopy12, 0, arrCopy12.length - 1);
    endTime = System.nanoTime();
    sortingAlgorithms.put("Quick Sort with Insertion Sort", (endTime - startTime) / 1_000_000.0);

    // Quick Sort with Selection Sort
    startTime = System.nanoTime();
    quickSortWithSelectionSort(arrCopy13, 0, arrCopy13.length - 1);
    endTime = System.nanoTime();
    sortingAlgorithms.put("Quick Sort with Selection Sort", (endTime - startTime) / 1_000_000.0);

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
