
/**
 * NAME: Christian Byrne
 * DATE: 2021-10-27
 * FILE: Prog3.java
 * DESC: A Java program that implements the columnsort algorithm to sort an array. The
 * algorithm requires the input array to be reshaped into a matrix with a specified number
 * of rows and columns. The matrix is then sorted column-wise using an in-place insertion
 * sort algorithm on doubly circular linked lists. After sorting, the matrix is reshaped
 * back into a single array. The algorithm also sorts the tail overflow partition of the
 * original array separately.
 * KNOWN ISSUES: None
 * COMPILATION: javac Prog3.java
 * EXECUTION: java Prog3 <filename>
 * USAGE: Replace <filename> with the path to the file containing the array to be sorted. The
 * file should contain one integer per line.
 * EXAMPLE: java Prog3 data.txt
 */
import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.Optional;
import java.io.IOException;

import java.util.Arrays;
import java.util.HashMap;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * ColumnSort class contains the implementation of the columnsort algorithm.
 * The algorithm sorts an array using the columnsort algorithm, which requires
 * the input array to be reshaped into a matrix with a specified number of rows
 * and columns.
 * The matrix is then sorted column-wise using an in-place insertion sort
 * algorithm on doubly circular linked lists.
 * After sorting, the matrix is reshaped back into a single array.
 * The algorithm also sorts the tail overflow partition of the original array
 * separately.
 * Prerequisites for Columnsort:
 * 1. data arranged in p x q matrix
 * 2. rows p atleast 2 times size of q
 * 3. p is even
 * 4. a comparison-based sorting algorithm is used for sorting rows/cols
 * 
 * @see <a href="https://en.wikipedia.org/wiki/Columnsort">Columnsort -
 *      Wikipedia</a>
 * @author Chrstian Byrne
 * @date 2021-10-27
 */
public class Prog3 {
  private static int rows;
  private static int cols;
  private static int size;

  /** Number of elements truncated from array s.t. a valid matrix can be formed */
  private static int remainder;

  /** The input array to sort */
  private static int[] arr;

  /** Minimum matrix size for which columnsort is used */
  private static final int MIN_MATRIX_SIZE = 8;

  /** Total number of entries in the lookup table */
  private static final int TOTAL_ENTRIES = 65536;

  public static void main(String[] args) {
    // Read the input file and parse the data
    String filename = args[0];
    arr = parseDataFile(filename).orElseThrow(RuntimeException::new);

    // Get the r and s values from the lookup table
    int[][] lut = loadLookupTable("lookupTable.bin");
    size = arr.length;
    rows = lut[size][0];
    cols = lut[size][1];
    remainder = lut[size][2];
    System.out.println("n = " + size + "\nr = " + rows + "\ns = " + cols);

    // Sort the array using the columnsort algorithm and measure the elapsed time
    double startTime = System.nanoTime();
    columnsort();
    double endTime = System.nanoTime();

    // Calculate and print the elapsed time
    String formattedTime = String.format("%.3f", (endTime - startTime) / 1_000_000_000.0);
    System.out.println("Elapsed time = " + formattedTime + " seconds.");

    // Print the sorted array
    for (int i = 0; i < arr.length; i++) {
      // System.out.println(arr[i]);
    }
  }

  /**
   * Sorts an array using the columnsort algorithm.
   * 
   * The algorithm requires the input array to be reshaped into a matrix with a
   * specified number of rows and columns.
   * The matrix is then sorted column-wise using an in-place insertion sort
   * algorithm on doubly circular linked lists.
   * After sorting, the matrix is reshaped back into a single array.
   * The algorithm also sorts the tail overflow partition of the original array
   * separately.
   */
  private static final void columnsort() {
    // If size of array is less than the minimum matrix size, use insertion sort
    if (size < MIN_MATRIX_SIZE) {
      insertionSort(arr, 0);
      return;
    }

    // Create the matrix from the input array
    ArrayList<DCLinkedList> matrix = createMatrix(arr);

    // Follow the steps of the columnsort algorithm
    sortColumns(matrix);
    transposeAndReshape(matrix);
    sortColumns(matrix);
    reshapeAndTranspose(matrix);
    sortColumns(matrix);
    shiftDownHalfR(matrix);
    sortColumns(matrix);
    shiftUpHalfR(matrix);
    sortColumns(matrix);

    // Sort the tail overflow of original array
    insertionSort(arr, size - remainder);

    // Merge the matrix cols with the sorted overflow partition
    mergeWithOverflow(arr, matrix);
  }

  /**
   * Custom node class for the doubly circular linked list used in the columnsort
   * algorithm.
   * Each node contains an integer value and references to the next and previous
   * nodes.
   */
  private static final class Node {
    int value;
    Node next;
    Node prev;

    Node(int value) {
      this.value = value;
      this.next = null;
      this.prev = null;
    }
  }

  /**
   * Custom doubly circular linked list class for the columnsort algorithm.
   * The class contains methods to prepend, append, pop from the left, and pop
   * from the right.
   */
  private static final class DCLinkedList {
    Node head;

    // Method to prepend a node to the start of the list
    /**
     * Prepends a new node to the beginning of the doubly linked list.
     * If the list is empty, the new node becomes the head and points to itself.
     * Otherwise, the new node is inserted before the current head and becomes the
     * new head.
     *
     * @param newNode the node to be prepended to the list. If null, the method
     *                returns without making any changes.
     */
    public final void prepend(Node newNode) {
      if (newNode == null) {
        return;
      }
      if (head == null) { // List is empty
        head = newNode;
        head.next = head;
        head.prev = head;
      } else {
        Node tail = head.prev; // Get the current tail node
        newNode.next = head;
        newNode.prev = tail;
        tail.next = newNode;
        head.prev = newNode;
        head = newNode; // Update head to the new node
      }
    }

    /**
     * Appends a new node to the end of the doubly linked list.
     * If the list is empty, the new node becomes the head and points to itself.
     * Otherwise, the new node is inserted after the current tail and becomes the
     * new tail.
     * 
     * @param value the value of the new node to be appended to the list
     */
    public final void append(int value) {
      Node newNode = new Node(value);

      if (head == null) { // List is empty
        head = newNode;
        head.next = head;
        head.prev = head;
      } else {
        Node tail = head.prev; // Get the current tail node
        newNode.next = head;
        newNode.prev = tail;
        tail.next = newNode;
        head.prev = newNode;
      }
    }

    /**
     * Removes the last node from the list and returns it.
     * If the list is empty, the method returns null.
     * 
     * @return the last node in the list, or null if the list is empty
     */
    public final Node popRight() {
      if (head == null) { // No nodes in the list
        return null;
      }

      Node tail = head.prev; // Last node (tail)

      if (tail == head) { // Only one node in the list
        head = null;
      } else {
        tail.prev.next = head; // Link the second-to-last node to head
        head.prev = tail.prev; // Link head back to the new tail

      }

      tail.next = null;
      tail.prev = null;
      return tail;
    }

    /**
     * Removes the first node from the list and returns it.
     * If the list is empty, the method returns null.
     * 
     * @return the first node in the list, or null if the list is empty
     */
    public final Node popLeft() {
      if (head == null) { // No nodes in the list
        return null;
      }

      Node oldHead = head; // Save the current head

      if (head.next == head) { // Only one node in the list
        head = null;
      } else {
        head = head.next; // Update head to the next node
        head.prev = oldHead.prev; // Set head's prev to the old tail
        oldHead.prev.next = head; // Link old tail to the new head
      }

      oldHead.next = null;
      oldHead.prev = null;
      return oldHead;
    }
  }

  /**
   * Creates a matrix represented as an ArrayList of DCLinkedList objects from a
   * given array.
   * The matrix is constructed with a specified number of columns (COLS) and rows
   * (ROWS).
   * Each column is filled with elements from the input array in a column-major
   * order.
   *
   * @param array the input array from which the matrix is created
   * @return an ArrayList of DCLinkedList objects representing the matrix
   */
  private static final ArrayList<DCLinkedList> createMatrix(int[] array) {
    ArrayList<DCLinkedList> matrix = new ArrayList<>(cols);
    for (int i = cols - 1; i >= 0; i--) {
      DCLinkedList col = new DCLinkedList();
      for (int j = 0; j < rows; j++) {
        col.append(array[i * rows + j]);
      }
      matrix.add(col);
    }
    return matrix;
  }

  /**
   * Sorts a portion of the array using the insertion sort algorithm.
   *
   * @param array          the array to be sorted
   * @param partitionStart the starting index of the partition to be sorted
   */
  private static final void insertionSort(int[] array, int partitionStart) {
    for (int i = partitionStart + 1; i < array.length; i++) {
      int key = array[i];
      int j = i - 1;

      while (j >= partitionStart && array[j] > key) {
        array[j + 1] = array[j];
        j--;
      }

      array[j + 1] = key;
    }
  }

  /**
   * Transposes and reshapes the given matrix represented as an ArrayList of
   * DCLinkedList.
   * The method iterates through the columns and rows of the matrix, adjusting the
   * elements
   * by popping and prepending values from the linked lists.
   * 
   * The end result is as if the matrix was transposed and then reshaped so every
   * two columns
   * were concatenated.
   *
   * @param matrix the matrix to be transposed and reshaped, represented as an
   *               ArrayList of DCLinkedList
   */
  private static final void transposeAndReshape(ArrayList<DCLinkedList> matrix) {
    for (int i = 0; i < matrix.size(); i++) {
      for (int j = 0; j < cols; j++) {
        int targetIndex = j % matrix.size();
        matrix.get(targetIndex).prepend(matrix.get(i).popRight());
      }
    }
  }

  /**
   * Reshapes and transposes the given matrix represented as an ArrayList of
   * DCLinkedList.
   * The method iterates through the columns and rows of the matrix, adjusting the
   * elements
   * by appending and popping values from the linked lists.
   * 
   * The end result is as if the matrix was reshaped so every two rows were
   * concatenated and then the matrix was transposed.
   *
   * @param matrix the matrix to be reshaped and transposed, represented as an
   *               ArrayList of DCLinkedList
   */
  private static final void reshapeAndTranspose(ArrayList<DCLinkedList> matrix) {
    for (int i = cols - 1; i >= 0; i--) {
      for (int j = 0; j < rows; j++) {
        int offset = j % cols;
        int targetIndex = cols - 1 - offset;
        matrix.get(i).append(matrix.get(targetIndex).popLeft().value);
      }
    }
  }

  /**
   * Sorts the columns of a matrix represented as an ArrayList of DCLinkedList
   * objects.
   * Each DCLinkedList represents a column in the matrix, and each column is
   * sorted in ascending order.
   * The sorting is performed using an in-place insertion sort algorithm on the
   * doubly circular linked list.
   *
   * @param matrix An ArrayList of DCLinkedList objects, where each DCLinkedList
   *               represents a column in the matrix.
   */
  private static final void sortColumns(ArrayList<DCLinkedList> matrix) {
    for (DCLinkedList row : matrix) {
      Node sortedTail = row.head; // Start with the first node as the sorted portion
      Node unsorted = row.head.next;

      while (unsorted != row.head) { // Stop when we circle back to the head
        Node next = unsorted.next;

        if (unsorted.value < sortedTail.value) {
          // Remove unsorted node from its current position
          unsorted.prev.next = unsorted.next;
          unsorted.next.prev = unsorted.prev;

          // Insert unsorted node into the sorted portion
          Node current = sortedTail;

          // Find the correct insertion point by moving backward
          while (current != row.head && unsorted.value <= current.value) {
            current = current.prev;
          }

          if (current == row.head && unsorted.value < current.value) {
            // Insert at the beginning, before head
            unsorted.prev = row.head.prev;
            unsorted.next = row.head;
            row.head.prev.next = unsorted;
            row.head.prev = unsorted;
            row.head = unsorted; // Update head to the new start of the row
          } else {
            // Insert after current node
            unsorted.next = current.next;
            unsorted.prev = current;
            current.next.prev = unsorted;
            current.next = unsorted;
          }
        } else {
          sortedTail = unsorted;
        }

        unsorted = next; // Move to the next node in the unsorted portion
      }
    }
  }

  /**
   * Shifts each column down by half the number of rows minus one. Elements
   * shifted past the last spot in their column are popped and prepended to the
   * next column.
   * 
   * This method performs the following operations:
   * - Calculates the shift amount as half the number of rows minus one
   * - For each column (except the first), it pops the calculated number of
   * elements from the end and prepends them to the next column.
   * - Removes the calculated number of padding values from the last
   * column
   *
   * @param matrix the matrix to be shifted, represented as an ArrayList of
   *               DCLinkedList.
   */
  private static final void shiftDownHalfR(ArrayList<DCLinkedList> matrix) {
    int shift = (rows / 2) - 1;
    int originalColCt = matrix.size();

    // Add a new col to front
    matrix.add(new DCLinkedList());

    // For each original row, pop `shift` values from the end and prepend to the
    // next row
    for (int i = originalColCt - 1; i >= 0; i--) {
      DCLinkedList row = matrix.get(i);
      for (int j = 0; j < shift; j++) {
        matrix.get(i + 1).append(row.popLeft().value);
      }
    }

    // Append the last col with `shift` number of Infinity values
    // Prepend the first col with `shift` number of -Infinity values
    int lastIndex = matrix.size() - 1;
    for (int i = 0; i < shift; i++) {
      matrix.get(0).append(Integer.MAX_VALUE);
      matrix.get(lastIndex).prepend(new Node(Integer.MIN_VALUE));
    }
  }

  /**
   * Shifts each column up by half the number of rows minus one. Elements shifted
   * past the first spot in their column are popped and appended to the previous
   * column.
   * 
   * This method performs the following operations:
   * - Calculates the shift amount as half the number of rows minus one
   * - For each column (except the last), it pops the calculated number of
   * elements from the end and appends them to the previous column.
   * - Removes the calculated number of padding values from the first column
   *
   * @param matrix the matrix to be shifted, represented as an ArrayList of
   *               DCLinkedList.
   */
  private static final void shiftUpHalfR(ArrayList<DCLinkedList> matrix) {
    int shift = (rows / 2) - 1;
    int originalColCt = matrix.size();

    // For n-1 first cols, pop `shift` values from the end and prepend to the
    // next col
    for (int i = originalColCt - 1; i > 0; i--) {
      DCLinkedList row = matrix.get(i);
      for (int j = 0; j < shift; j++) {
        matrix.get(i - 1).prepend(new Node(row.popRight().value));
      }
    }

    // Remove the `shift` Infinity pad values from the last col
    for (int i = 0; i < shift; i++) {
      matrix.get(0).popRight();
    }
  }

  /**
   * Merges the elements of the given array and the matrix of doubly circular
   * linked lists
   * into the array, handling overflow elements from the array.
   *
   * This method assumes that the last column of the matrix contains negative
   * infinity values
   * and starts merging from the second-to-last column. It compares elements from
   * the current
   * column of the matrix and the overflow elements from the array, placing the
   * smaller element
   * into the array. If a column in the matrix is exhausted, it moves to the
   * previous column.
   * If the overflow elements in the array are exhausted, it continues with the
   * remaining elements
   * from the matrix.
   *
   * @param array  the array containing overflow elements to be merged
   * @param matrix the matrix of doubly circular linked lists to be merged
   */
  private static void mergeWithOverflow(int[] array, ArrayList<DCLinkedList> matrix) {
    int curOverflowIdx = size - remainder;
    int curOverflowVal = curOverflowIdx < size ? array[curOverflowIdx] : Integer.MAX_VALUE;

    // The last column contains the negative infinity values, so we start from the
    // second-to-last column
    int curColIndex = matrix.size() - 2;
    int curColVal = curColIndex >= 0 ? matrix.get(curColIndex).popLeft().value : Integer.MAX_VALUE;

    for (int i = 0; i < size; i++) {
      if (curColIndex >= 0 && curColVal < curOverflowVal) {
        array[i] = curColVal;
        if (matrix.get(curColIndex).head == null) {
          curColIndex--;
          if (curColIndex >= 0) {
            curColVal = matrix.get(curColIndex).popLeft().value;
          } else {
            curColVal = Integer.MAX_VALUE; // LinkedList exhausted
          }
        } else {
          curColVal = matrix.get(curColIndex).popLeft().value;
        }
      } else {
        array[i] = curOverflowVal;
        curOverflowIdx++;
        if (curOverflowIdx < size) {
          curOverflowVal = array[curOverflowIdx];
        } else {
          curOverflowVal = Integer.MAX_VALUE; // Array exhausted
        }
      }
    }
  }

  /**
   * Loads the lookup table from a binary file and returns it as a 2D array.
   * The lookup table contains the r, s, and overflow values for each possible
   * array size.
   *
   * @param fileName the name of the binary file containing the lookup table
   * @return a 2D array containing the lookup table values
   */
  public static int[][] loadLookupTable(String fileName) {
    int[][] lookupTable = new int[TOTAL_ENTRIES][3];

    try (DataInputStream dis = new DataInputStream(new FileInputStream(fileName))) {
      // Populate the array directly
      for (int i = 0; i < TOTAL_ENTRIES; i++) {
        lookupTable[i][0] = dis.readInt();
        lookupTable[i][1] = dis.readInt();
        lookupTable[i][2] = dis.readInt();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return lookupTable;
  }

  /**
   * Parses the data file and returns the data as an array of integers.
   * If the file cannot be read or contains invalid data, an empty Optional is
   * returned.
   *
   * @param filename the name of the file to be read
   * @return an Optional containing the array of integers if the file was read
   *         successfully, or an empty Optional if an error occurred
   */
  public static Optional<int[]> parseDataFile(String filename) {
    try (Stream<String> lines = Files.lines(Paths.get(filename))) {
      return Optional.of(lines
          .mapToInt(Integer::parseInt)
          .toArray());
    } catch (IOException e) {
      System.out.println("Error reading file: " + e.getMessage());
      return Optional.empty();
    } catch (NumberFormatException e) {
      System.out.println("File contains invalid number format." + e.getMessage());
      return Optional.empty();
    }
  }
}