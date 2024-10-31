
/**
 * File: Prog3.java
 * Author: Christian Byrne
 * Course: CSc 345 — Analysis of Discrete Structures
 * Assignment: Program #3: Columnsort
 * Instructor: McCann
 * TAs: Rubin Yang, Lucas Almeida, Hamad Ayaz, Sohan Bhakta, CJ Chen, Hyungji Kim, Hamlet Taraz
 * Due Date: October 31, 2024
 * Desc: A Java program that implements the columnsort algorithm to sort an array. The
 *       algorithm requires the input array to be reshaped into a matrix with a specified number
 *       of rows and columns. The matrix is then sorted column-wise using an in-place insertion
 *       sort algorithm on doubly circular linked lists. After sorting, the matrix is reshaped
 *       back into a single array. The algorithm also sorts the tail overflow partition of the
 *       original array separately. The tail should never have more than 4 elements for n < 8192.
 *       It is necessary to create the tail partition when the number of elements in the array is
 *       prime or otherwise invalid.
 * Compilation: javac Prog3.java
 * Execution: java Prog3 <filename>
 * Usage: Replace <filename> with the path to the file containing the array to be sorted. The
 *        file should contain one integer per line.
 * Example: java Prog3 data.txt
 * Features Not Implemented: Parallelized sorting of the columns
 * 
 */

import java.util.ArrayList;
import java.util.stream.Stream;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * ColumnSort class contains the implementation of the columnsort algorithm.
 * The algorithm sorts an array using the columnsort algorithm, which requires
 * the input array to be reshaped into a matrix with a specified number of rows
 * and columns.
 * 
 * The matrix is then sorted column-wise using an in-place insertion sort
 * algorithm on doubly circular linked lists. The reason for the partitioning
 * is due to the prerequisites of the columnsort algorithm, shown below.
 * 
 * Prerequisites for Columnsort:
 * 1. data arranged in p x q matrix
 * 2. rows p atleast 2 times size of q
 * 3. p is even
 * 4. a comparison-based sorting algorithm is used for sorting rows/cols
 * 
 * @depdendencies None
 * @see <a href="https://en.wikipedia.org/wiki/Columnsort">Columnsort -
 *      Wikipedia</a>
 * @author Chrstian Byrne
 * @date 2021-10-27
 */
public class Prog3 {
  /** The input array to sort */
  private static Integer[] arr;

  /** Number of rows in the matrix */
  private static int rows;

  /** Number of columns in the matrix */
  private static int cols;

  /** Size of the input array */
  private static int size;

  /** Number of elements truncated from input s.t. a valid matrix can be formed */
  private static int remainder;

  /** Minimum input size for which full columnsort algorithm is used */
  private static final int MIN_SIZE = 8;

  /** Total number of entries in the lookup table */
  private static final int MAX_ENTRIES = 8192;

  public static void main(String[] args) {
    // Read the input files and parse the data
    if (!parseDataFiles(args[0]))
      System.exit(1);

    /* ------------------------------ TIMED SECTION ----------------------------- */
    double startTime = System.nanoTime();
    setMatrixDims(lut);

    // Set the matrix dims (r and s values)

    // Sort the array using the columnsort algorithm
    columnsort();

    double endTime = System.nanoTime();
    /* ---------------------------- END TIMED SECTION --------------------------- */

    // Print the matrix size, dimensions, and elapsed time
    printResults(startTime, endTime);
  }

  /**
   * Sorts an array using the columnsort algorithm.
   * 
   * The algorithm requires the input array to be reshaped into a matrix with a
   * specified number of rows and columns. The algorithm follows 8 steps to sort
   * which generally involve sorting column-wise, reshaping in some way, then
   * sorting again. The final step merges the sorted columns with the overflow
   * partition of the original array.
   */
  private static final void columnsort() {
    // If size is < than minimum size, just insertion sort on a single column
    if (size < MIN_SIZE) {
      insertionSort(arr, 0);
      return;
    }

    // Create the matrix from the input array
    ArrayList<CDLinkedList> matrix = createMatrix(arr);

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
   * Creates a matrix represented as an ArrayList of CDLinkedList objects from a
   * given array. The matrix is constructed with a specified number of columns
   * (COLS) and rows (ROWS). Each column is filled with elements from the input
   * array in a column-major order.
   *
   * @param array the input array from which the matrix is created
   * @return an ArrayList of CDLinkedList objects representing the matrix
   */
  private static final ArrayList<CDLinkedList> createMatrix(Integer[] array) {
    ArrayList<CDLinkedList> matrix = new ArrayList<>(cols);
    for (int i = cols - 1; i >= 0; i--) {
      CDLinkedList col = new CDLinkedList();
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
  private static final void insertionSort(Integer[] array, int partitionStart) {
    for (int i = partitionStart + 1; i < array.length; i++) {
      Integer key = array[i];
      Integer j = i - 1;

      while (j >= partitionStart && array[j] > key) {
        array[j + 1] = array[j];
        j--;
      }

      array[j + 1] = key;
    }
  }

  /**
   * Transposes and reshapes the given matrix represented as an ArrayList of
   * CDLinkedList. The method iterates through the columns and rows of the matrix,
   * adjusting the elements by popping and prepending values from the linked
   * lists. The end result is as if the matrix was transposed and then reshaped so
   * every two columns were concatenated.
   *
   * @param matrix the matrix to be transposed and reshaped, represented as an
   *               ArrayList of CDLinkedList
   */
  private static final void transposeAndReshape(ArrayList<CDLinkedList> matrix) {
    for (int i = 0; i < matrix.size(); i++) {
      for (int j = 0; j < cols; j++) {
        int targetIndex = j % matrix.size();
        matrix.get(targetIndex).prepend(matrix.get(i).popRight());
      }
    }
  }

  /**
   * Reshapes and transposes the given matrix represented as an ArrayList of
   * CDLinkedList. The method iterates through the columns and rows of the matrix,
   * adjusting the elements by appending and popping values from the linked lists.
   * 
   * The end result is as if the matrix was reshaped so every two rows were
   * concatenated and then the matrix was transposed.
   *
   * @param matrix the matrix to be reshaped and transposed, represented as an
   *               ArrayList of CDLinkedList
   */
  private static final void reshapeAndTranspose(ArrayList<CDLinkedList> matrix) {
    for (int i = cols - 1; i >= 0; i--) {
      for (int j = 0; j < rows; j++) {
        int offset = j % cols;
        int targetIndex = cols - 1 - offset;
        matrix.get(i).append(matrix.get(targetIndex).popLeft().value);
      }
    }
  }

  /**
   * Sorts the columns of a matrix represented as an ArrayList of CDLinkedList
   * objects. Each CDLinkedList represents a column in the matrix, and each column
   * is sorted in ascending order. The sorting is performed using an in-place
   * insertion sort algorithm on the doubly circular linked list.
   *
   * @param matrix An ArrayList of CDLinkedList objects, where each CDLinkedList
   *               represents a column in the matrix.
   */
  private static final void sortColumns(ArrayList<CDLinkedList> matrix) {
    for (CDLinkedList row : matrix) {
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
   *               CDLinkedList.
   */
  private static final void shiftDownHalfR(ArrayList<CDLinkedList> matrix) {
    int shift = (rows / 2) - 1;
    int originalColCt = matrix.size();

    // Add a new col to front
    matrix.add(new CDLinkedList());

    // For each original row, pop `shift` values from the end and prepend to the
    // next row
    for (int i = originalColCt - 1; i >= 0; i--) {
      CDLinkedList row = matrix.get(i);
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
   *               CDLinkedList.
   */
  private static final void shiftUpHalfR(ArrayList<CDLinkedList> matrix) {
    int shift = (rows / 2) - 1;
    int originalColCt = matrix.size();

    // For first n - 1 cols, pop `shift` values from the end and prepend to the
    // start of the next col
    for (int i = originalColCt - 1; i > 0; i--) {
      CDLinkedList row = matrix.get(i);
      for (int j = 0; j < shift; j++) {
        matrix.get(i - 1).prepend(new Node(row.popRight().value));
      }
    }

    // Remove the `shift` number of pad values from the last col
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
   * infinity values and starts merging from the second-to-last column. It
   * compares elements from the current column of the matrix and the overflow
   * elements from the array, placing the smaller element into the array.
   * 
   * If a column in the matrix is exhausted, it moves to the previous column. If
   * the overflow elements in the array are exhausted, it continues with the
   * remaining elements from the matrix.
   *
   * @param array  the array containing overflow elements to be merged
   * @param matrix the matrix of doubly circular linked lists to be merged
   */
  private static void mergeWithOverflow(Integer[] array, ArrayList<CDLinkedList> matrix) {
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
            curColVal = Integer.MAX_VALUE; // CDLinkedList exhausted
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
   * Prints the results of the columnsort algorithm, including the matrix size,
   * dimensions, and elapsed time.
   *
   * @param startTime the start time of the algorithm
   * @param endTime   the end time of the algorithm
   */
  private static final void printResults(double startTime, double endTime) {
    // Print the matrix size and dimensions
    System.out.println("n = " + size + "\nr = " + rows + "\ns = " + cols);

    // Print the elapsed time
    String formattedTime = String.format("%.3f", (endTime - startTime) / 1_000_000_000.0);
    System.out.println("Elapsed time = " + formattedTime + " seconds.");

    // Print the sorted array
    for (int i = 0; i < arr.length; i++) {
      System.out.println(arr[i]);
    }
  }

  /**
   * Custom node class for the doubly circular linked list used in the columnsort
   * algorithm. Each node contains an integer value and references to the next and
   * previous nodes.
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
  private static final class CDLinkedList {
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

  private static int[][] lut = new int[MAX_ENTRIES][3];

  /**
   * Sets the matrix dimensions based on the size of the input array and the
   * lookup table values.
   *
   * @param lut the lookup table containing the r, s, and overflow values
   */
  private static final void setMatrixDims(int[][] lut) {
    size = arr.length;
    rows = size < MAX_ENTRIES ? lut[size][0] : lut[MAX_ENTRIES - 1][0];
    cols = size < MAX_ENTRIES ? lut[size][1] : lut[MAX_ENTRIES - 1][1];
    remainder = size < MAX_ENTRIES ? lut[size][2] : size - MAX_ENTRIES;
  }

  /**
   * Parses the data files containing the input array and the lookup table.
   *
   * @param filename the name of the file containing the input array
   * @return true if the files were successfully parsed, false otherwise
   */
  private static boolean parseDataFiles(String filename) {
    try (Stream<String> lines = Files.lines(Paths.get(filename))) {
      arr = lines
          .map(Integer::parseInt) // Map each line to Integer
          .toArray(Integer[]::new); // Collect as Integer[]
    } catch (IOException e) {
      System.out.println("Error reading file: " + e.getMessage());
      return false;
    } catch (NumberFormatException e) {
      System.out.println("File contains invalid number format: " + e.getMessage());
      return false;
    }

    // Pre-computed (input array size -> matrix dimensions) mappings are stored in a
    // binary file so as not to over-clutter the source code. It is the same as
    // hard-coding the values directly in the source code.
    try (DataInputStream dis = new DataInputStream(new FileInputStream("lookupTable.bin"))) {
      // Populate the array directly
      for (int i = 0; i < MAX_ENTRIES; i++) {
        lut[i][0] = dis.readInt();
        lut[i][1] = dis.readInt();
        lut[i][2] = dis.readInt();
      }
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }
}