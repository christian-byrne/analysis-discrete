import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.Optional;
import java.io.IOException;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Prerequisites for Columnsort:
 * 1. data arranged in p x q matrix
 * 2. rows p atleast 2 times size of q
 * 3. p is even
 * 4. a comparison-based sorting algorithm is used for sorting rows/cols
 */

public class Prog3 {
  private static int[][] LOOKUP_TABLE;

  public static void main(String[] args) {
    String filename = args[0];
    // The input file will contain one integer per line.
    // Parse the file and store the integers in an array.
    int[] arr = FileLoader.parseDataFile(filename).orElseThrow(RuntimeException::new);
    int[] initialArray = Arrays.copyOf(arr, arr.length);
    LOOKUP_TABLE = FileLoader.loadLookupTable("lookupTable.bin");

    int rows = LOOKUP_TABLE[arr.length][0];
    int cols = LOOKUP_TABLE[arr.length][1];
    int overflow = LOOKUP_TABLE[arr.length][2];
    int initialLen = arr.length;
    System.out.println("n = " + arr.length + "\nr = " + rows + "\ns = " + cols);

    double startTime = System.nanoTime();
    ColumnSort.columnsort(arr, rows, cols, overflow);
    double endTime = System.nanoTime();

    System.out.println("Elapsed time = " + (endTime - startTime) /
        1_000_000_000.0 +
        " seconds.");

    for (int i = 0; i < arr.length; i++) {
      // System.out.println(arr[i]);
    }

    assertSorted(arr);
    assertSameLength(arr, initialLen);
    assertHasSameElements(arr, initialArray);

  }

  private static void assertHasSameElements(int[] arr, int[] initialArray) {
    HashMap<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < arr.length; i++) {
      map.put(arr[i], map.getOrDefault(arr[i], 0) + 1);
    }

    HashMap<Integer, Integer> initialMap = new HashMap<>();
    for (int i = 0; i < initialArray.length; i++) {
      initialMap.put(initialArray[i], initialMap.getOrDefault(initialArray[i], 0) + 1);
    }

    for (int key : map.keySet()) {
      if (!map.get(key).equals(initialMap.get(key))) {
        System.out.println("Array elements have changed. There was " + initialMap.get(key) +
            " of " + key + " but now there is " + map.get(key));
        System.exit(1);
      }
    }
  }

  private static void assertSameLength(int[] arr, int initialLen) {
    if (arr.length != initialLen) {
      System.out.println("Array length has changed.");
      System.exit(1);
    }
  }

  private static boolean assertSorted(int[] arr) {
    for (int i = 1; i < arr.length; i++) {
      if (arr[i] < arr[i - 1]) {
        System.out.println("Array is not sorted.");
        System.exit(1);
      }
    }
    return true;
  }

}