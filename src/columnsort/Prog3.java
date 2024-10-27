import java.util.ArrayList;
import java.util.stream.Stream;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Optional;

/**
 * Prerequisites for Columnsort:
 * 1. data arranged in p x q matrix
 * 2. rows p atleast 2 times size of q
 * 3. p is even
 * 4. a comparison-based sorting algorithm is used for sorting rows/cols
 */

public class Prog3 {
  private int ROWS;
  private int COLS;
  private int SIZE;
  private int OVERFLOW;
  private boolean DEBUG = false;

  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("Usage: java Prog3 <filename>");
      return;
    }
    String filename = args[0];
    int[] arr = parseFile(filename).orElseThrow(RuntimeException::new);
    // The input file will contain one integer per line. Parse the file and store
    // the integers in an array.
    Prog3 prog3 = new Prog3();

    long startTime = System.nanoTime();
    System.gc();
    ArrayList<CustomLinkedList> matrix = prog3.columnsort(arr);
    long endTime = System.nanoTime();
    System.out.println("Elapsed time = " + (endTime - startTime) / 1000000000.0 + " seconds.");

    for (int i = matrix.size() - 1; i >= 0; i--) {
      CustomLinkedList row = matrix.get(i);
      Node current = row.popLeft();
      while (current != null) {
        System.out.println(current.value);
        current = row.popLeft();
      }
    }
  }

  // Nested Node class for custom linked list
  private static class Node {
    int value;
    Node next;
    Node prev;

    Node(int value) {
      this.value = value;
      this.next = null;
      this.prev = null;
    }
  }

  // CustomLinkedList class with circular doubly linked list structure
  private static class CustomLinkedList {
    Node head;
    int size = 0;

    // Method to prepend a node to the start of the list
    public void prepend(Node newNode) {
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
      size++;
    }

    // Method to append a node to the end of the list
    public void append(int value) {
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
      size++;
    }

    // Method to remove the last element and return its value
    public Node popRight() {
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

      size--;
      tail.next = null;
      tail.prev = null;
      return tail;
    }

    // Method to remove the first element and return its value
    public Node popLeft() {
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

      size--;
      oldHead.next = null;
      oldHead.prev = null;
      return oldHead;
    }
  }

  // In-place insertion sort for CustomLinkedList, defined in Prog3
  private void insertionSort(CustomLinkedList list) {
    if (list.head == null || list.head.next == list.head) // Empty or one-node list
      return;

    Node sortedTail = list.head; // Start with the first node as the sorted portion
    Node unsorted = list.head.next;

    while (unsorted != list.head) { // Stop when we circle back to the head
      Node next = unsorted.next;

      if (unsorted.value < sortedTail.value) {
        // Remove unsorted node from its current position
        unsorted.prev.next = unsorted.next;
        unsorted.next.prev = unsorted.prev;

        // Insert unsorted node into the sorted portion
        Node current = sortedTail;

        // Find the correct insertion point by moving backward
        while (current != list.head && unsorted.value < current.value) {
          current = current.prev;
        }

        if (current == list.head && unsorted.value < current.value) {
          // Insert at the beginning, before head
          unsorted.prev = list.head.prev;
          unsorted.next = list.head;
          list.head.prev.next = unsorted;
          list.head.prev = unsorted;
          list.head = unsorted; // Update head to the new start of the list
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

  /**
   * This emulates the "Step 2: Transpose and Reshape" described in Chandry et al.
   * 
   * It transposes then permutes an r x s matrix such that the shape is preserved.
   * If the conditions (s is a divisor or r and r >= 2(s-1)^2) are not met, the
   * shape will not be preserved.
   * 
   * @param matrix
   */
  private void transposeAndReshape(ArrayList<CustomLinkedList> matrix) {
    int rowSize = 0;
    int shift = 0;
    for (int i = 0; i < matrix.size(); i++) {
      CustomLinkedList row = matrix.get(i);
      if (row.head == null) {
        continue;
      }
      if (rowSize == 0)
        rowSize = row.size;

      if (i != 0 && i % rowSize == 0) {
        shift++;
        System.out.println("\n\n\nShift: " + shift);
      }

      for (int j = 0; j < rowSize; j++) {
        int targetIndex = j % matrix.size();
        targetIndex += shift;
        matrix.get(targetIndex).prepend(row.popRight());
      }
    }
  }

  private void reshapeAndTranspose(ArrayList<CustomLinkedList> matrix) {
    int cols = matrix.get(0).size;
    int rows = matrix.size();
    for (int i = rows - 1; i >= 0; i--) {
      for (int j = 0; j < cols; j++) {
        int offset = j % rows;
        int targetIndex = rows - 1 - offset;
        matrix.get(i).append(matrix.get(targetIndex).popLeft().value);
      }
    }
  }

  private String padNum(int num) {
    if (num == Integer.MAX_VALUE) {
      return "  ∞  ";
    }
    if (num == Integer.MIN_VALUE) {
      return " -∞  ";
    }
    int padTotal = 9;
    int padleft = (padTotal - String.valueOf(num).length()) / 2;
    int padright = padTotal - padleft - String.valueOf(num).length();
    return String.format("%s%d%s", " ".repeat(padleft), num, " ".repeat(padright));
  }

  // The matrix is represented as the transpose of how it is actually accessed, so
  // adjust when printing
  private void prettyPrintMatrix(ArrayList<CustomLinkedList> matrix) {
    if (!DEBUG) {
      return;
    }
    for (int i = 0; i < matrix.get(0).size; i++) {
      for (int j = matrix.size() - 1; j >= 0; j--) {
        CustomLinkedList row = matrix.get(j);
        if (row.head == null) {
          System.out.print("  ·  ");
          continue;
        }
        Node current = row.head;
        for (int k = 0; k < i; k++) {
          current = current.next;
        }
        System.out.print(padNum(current.value));
      }
      System.out.println();
    }
  }

  private void sortColumns(ArrayList<CustomLinkedList> matrix) {
    for (CustomLinkedList row : matrix) {
      insertionSort(row);
    }
  }

  private void shiftDownHalfR(ArrayList<CustomLinkedList> matrix) {
    int shift = ROWS / 2;
    int originalColCt = matrix.size();

    // Add a new col at M_j=0
    matrix.add(new CustomLinkedList());

    // For each original row, pop `shift` values from the end and prepend to the
    // next row
    for (int i = originalColCt - 1; i >= 0; i--) {
      CustomLinkedList row = matrix.get(i);
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

    // Ensure shape is preserved when rows is odd
    if (ROWS % 2 != 0) {
      matrix.get(lastIndex).prepend(new Node(Integer.MIN_VALUE));
    }
  }

  private void shiftUpHalfR(ArrayList<CustomLinkedList> matrix) {
    int shift = ROWS / 2;
    int originalColCt = matrix.size();

    // For n-1 first cols, pop `shift` values from the end and prepend to the
    // next col
    for (int i = originalColCt - 1; i > 0; i--) {
      CustomLinkedList row = matrix.get(i);
      for (int j = 0; j < shift; j++) {
        matrix.get(i - 1).prepend(new Node(row.popRight().value));
      }
    }

    // Remove the `shift` Infinity pad values from the last col
    for (int i = 0; i < shift; i++) {
      matrix.get(0).popRight();
    }

    // TODO: maybe it's more efficient to not remove an item from the matrix, but
    // rather just skip this col in the final output step?
    matrix.remove(originalColCt - 1);
  }

  ArrayList<CustomLinkedList> columnsort(int[] arr) {
    SIZE = arr.length;
    System.out.println("Size of input: " + SIZE);

    if (SIZE >= LOOKUP_TABLE.length) {
      ROWS = LOOKUP_TABLE[LOOKUP_TABLE.length - 1][0];
      COLS = LOOKUP_TABLE[LOOKUP_TABLE.length - 1][1];
      OVERFLOW = SIZE - LOOKUP_TABLE.length;
    } else {
      ROWS = LOOKUP_TABLE[SIZE][0];
      COLS = LOOKUP_TABLE[SIZE][1];
      OVERFLOW = LOOKUP_TABLE[SIZE][2];
    }
    System.out.println("ROWS: " + ROWS + ", COLS: " + COLS + ", OVERFLOW: " + OVERFLOW);
    ArrayList<CustomLinkedList> matrix = new ArrayList<>(COLS);
    for (int i = COLS - 1; i >= 0; i--) {
      CustomLinkedList col = new CustomLinkedList();
      if (i < COLS) {
        for (int j = 0; j < ROWS; j++) {
          col.append(arr[i * ROWS + j]);
        }
      }
      matrix.add(col);
    }
    prettyPrintMatrix(matrix);

    // Step 1: Sort Columns
    System.out.println("Step 1: Sorting columns:");
    sortColumns(matrix);
    prettyPrintMatrix(matrix);

    // Step 2: Transpose and Reshape
    System.out.println("Step 2: Transpose and Reshape");
    transposeAndReshape(matrix);
    prettyPrintMatrix(matrix);

    // Step 3: Sort columns
    System.out.println("Step 3: Sorting columns:");
    sortColumns(matrix);
    prettyPrintMatrix(matrix);

    // Step 4: Reshape and Transpose
    System.out.println("Step 4: Reshape and Transpose");
    reshapeAndTranspose(matrix);
    prettyPrintMatrix(matrix);

    // Step 5: Sort columns
    System.out.println("Step 5: Sorting columns:");
    sortColumns(matrix);
    prettyPrintMatrix(matrix);

    // Step 6: Shift down by r/2
    System.out.println("Step 6: Shift down by r/2");
    shiftDownHalfR(matrix);
    prettyPrintMatrix(matrix);

    // Step 7: Sort columns
    System.out.println("Step 7: Sorting columns:");
    sortColumns(matrix);
    prettyPrintMatrix(matrix);

    // Step 8: Shift up by r/2
    System.out.println("Step 8: Shift up by r/2");
    shiftUpHalfR(matrix);
    prettyPrintMatrix(matrix);

    return matrix;
  }

  private static Optional<int[]> parseFile(String filename) {
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

  private final int[][] LOOKUP_TABLE = { { 1, 0, 0 }, { 1, 1, 0 }, { 1, 2, 0 }, { 1, 3, 0 }, { 2, 2, 0 }, { 2, 2, 1 },
      { 2, 2, 2 }, { 2, 2, 3 }, { 4, 2, 0 }, { 4, 2, 1 }, { 4, 2, 2 }, { 4, 2, 3 }, { 6, 2, 0 }, { 6, 2, 1 },
      { 6, 2, 2 }, { 6, 2, 3 }, { 8, 2, 0 }, { 8, 2, 1 }, { 8, 2, 2 }, { 8, 2, 3 }, { 10, 2, 0 }, { 10, 2, 1 },
      { 10, 2, 2 }, { 10, 2, 3 }, { 12, 2, 0 }, { 12, 2, 1 }, { 12, 2, 2 }, { 9, 3, 0 }, { 14, 2, 0 }, { 14, 2, 1 },
      { 14, 2, 2 }, { 14, 2, 3 }, { 16, 2, 0 }, { 16, 2, 1 }, { 16, 2, 2 }, { 16, 2, 3 }, { 12, 3, 0 }, { 12, 3, 1 },
      { 12, 3, 2 }, { 12, 3, 3 }, { 20, 2, 0 }, { 20, 2, 1 }, { 20, 2, 2 }, { 20, 2, 3 }, { 22, 2, 0 }, { 15, 3, 0 },
      { 15, 3, 1 }, { 15, 3, 2 }, { 24, 2, 0 }, { 24, 2, 1 }, { 24, 2, 2 }, { 24, 2, 3 }, { 26, 2, 0 }, { 26, 2, 1 },
      { 18, 3, 0 }, { 18, 3, 1 }, { 28, 2, 0 }, { 28, 2, 1 }, { 28, 2, 2 }, { 28, 2, 3 }, { 30, 2, 0 }, { 30, 2, 1 },
      { 30, 2, 2 }, { 21, 3, 0 }, { 32, 2, 0 }, { 32, 2, 1 }, { 32, 2, 2 }, { 32, 2, 3 }, { 34, 2, 0 }, { 34, 2, 1 },
      { 34, 2, 2 }, { 34, 2, 3 }, { 24, 3, 0 }, { 24, 3, 1 }, { 24, 3, 2 }, { 24, 3, 3 }, { 38, 2, 0 }, { 38, 2, 1 },
      { 38, 2, 2 }, { 38, 2, 3 }, { 20, 4, 0 }, { 27, 3, 0 }, { 27, 3, 1 }, { 27, 3, 2 }, { 42, 2, 0 }, { 42, 2, 1 },
      { 42, 2, 2 }, { 42, 2, 3 }, { 44, 2, 0 }, { 44, 2, 1 }, { 30, 3, 0 }, { 30, 3, 1 }, { 46, 2, 0 }, { 46, 2, 1 },
      { 46, 2, 2 }, { 46, 2, 3 }, { 24, 4, 0 }, { 24, 4, 1 }, { 24, 4, 2 }, { 33, 3, 0 }, { 50, 2, 0 }, { 50, 2, 1 },
      { 50, 2, 2 }, { 50, 2, 3 }, { 52, 2, 0 }, { 52, 2, 1 }, { 52, 2, 2 }, { 52, 2, 3 }, { 36, 3, 0 }, { 36, 3, 1 },
      { 36, 3, 2 }, { 36, 3, 3 }, { 28, 4, 0 }, { 28, 4, 1 }, { 28, 4, 2 }, { 28, 4, 3 }, { 58, 2, 0 }, { 39, 3, 0 },
      { 39, 3, 1 }, { 39, 3, 2 }, { 60, 2, 0 }, { 60, 2, 1 }, { 60, 2, 2 }, { 60, 2, 3 }, { 62, 2, 0 }, { 62, 2, 1 },
      { 42, 3, 0 }, { 42, 3, 1 }, { 32, 4, 0 }, { 32, 4, 1 }, { 32, 4, 2 }, { 32, 4, 3 }, { 66, 2, 0 }, { 66, 2, 1 },
      { 66, 2, 2 }, { 45, 3, 0 }, { 68, 2, 0 }, { 68, 2, 1 }, { 68, 2, 2 }, { 68, 2, 3 }, { 70, 2, 0 }, { 70, 2, 1 },
      { 70, 2, 2 }, { 70, 2, 3 }, { 36, 4, 0 }, { 36, 4, 1 }, { 36, 4, 2 }, { 36, 4, 3 }, { 74, 2, 0 }, { 74, 2, 1 },
      { 74, 2, 2 }, { 74, 2, 3 }, { 76, 2, 0 }, { 51, 3, 0 }, { 51, 3, 1 }, { 51, 3, 2 }, { 78, 2, 0 }, { 78, 2, 1 },
      { 78, 2, 2 }, { 78, 2, 3 }, { 40, 4, 0 }, { 40, 4, 1 }, { 54, 3, 0 }, { 54, 3, 1 }, { 82, 2, 0 }, { 82, 2, 1 },
      { 82, 2, 2 }, { 82, 2, 3 }, { 84, 2, 0 }, { 84, 2, 1 }, { 84, 2, 2 }, { 57, 3, 0 }, { 86, 2, 0 }, { 86, 2, 1 },
      { 86, 2, 2 }, { 35, 5, 0 }, { 44, 4, 0 }, { 44, 4, 1 }, { 44, 4, 2 }, { 44, 4, 3 }, { 60, 3, 0 }, { 60, 3, 1 },
      { 60, 3, 2 }, { 60, 3, 3 }, { 92, 2, 0 }, { 92, 2, 1 }, { 92, 2, 2 }, { 92, 2, 3 }, { 94, 2, 0 }, { 63, 3, 0 },
      { 63, 3, 1 }, { 63, 3, 2 }, { 48, 4, 0 }, { 48, 4, 1 }, { 48, 4, 2 }, { 48, 4, 3 }, { 98, 2, 0 }, { 98, 2, 1 },
      { 66, 3, 0 }, { 66, 3, 1 }, { 40, 5, 0 }, { 40, 5, 1 }, { 40, 5, 2 }, { 40, 5, 3 }, { 102, 2, 0 }, { 102, 2, 1 },
      { 102, 2, 2 }, { 69, 3, 0 }, { 52, 4, 0 }, { 52, 4, 1 }, { 52, 4, 2 }, { 52, 4, 3 }, { 106, 2, 0 }, { 106, 2, 1 },
      { 106, 2, 2 }, { 106, 2, 3 }, { 72, 3, 0 }, { 72, 3, 1 }, { 72, 3, 2 }, { 72, 3, 3 }, { 110, 2, 0 },
      { 110, 2, 1 }, { 110, 2, 2 }, { 110, 2, 3 }, { 56, 4, 0 }, { 45, 5, 0 }, { 45, 5, 1 }, { 45, 5, 2 },
      { 114, 2, 0 }, { 114, 2, 1 }, { 114, 2, 2 }, { 114, 2, 3 }, { 116, 2, 0 }, { 116, 2, 1 }, { 78, 3, 0 },
      { 78, 3, 1 }, { 118, 2, 0 }, { 118, 2, 1 }, { 118, 2, 2 }, { 118, 2, 3 }, { 60, 4, 0 }, { 60, 4, 1 },
      { 60, 4, 2 }, { 81, 3, 0 }, { 122, 2, 0 }, { 122, 2, 1 }, { 122, 2, 2 }, { 122, 2, 3 }, { 124, 2, 0 },
      { 124, 2, 1 }, { 50, 5, 0 }, { 50, 5, 1 }, { 84, 3, 0 }, { 84, 3, 1 }, { 84, 3, 2 }, { 84, 3, 3 }, { 64, 4, 0 },
      { 64, 4, 1 }, { 64, 4, 2 }, { 64, 4, 3 }, { 130, 2, 0 }, { 87, 3, 0 }, { 87, 3, 1 }, { 87, 3, 2 }, { 132, 2, 0 },
      { 132, 2, 1 }, { 132, 2, 2 }, { 132, 2, 3 }, { 134, 2, 0 }, { 134, 2, 1 }, { 90, 3, 0 }, { 90, 3, 1 },
      { 68, 4, 0 }, { 68, 4, 1 }, { 68, 4, 2 }, { 55, 5, 0 }, { 138, 2, 0 }, { 138, 2, 1 }, { 138, 2, 2 }, { 93, 3, 0 },
      { 140, 2, 0 }, { 140, 2, 1 }, { 140, 2, 2 }, { 140, 2, 3 }, { 142, 2, 0 }, { 142, 2, 1 }, { 142, 2, 2 },
      { 142, 2, 3 }, { 72, 4, 0 }, { 72, 4, 1 }, { 72, 4, 2 }, { 72, 4, 3 }, { 146, 2, 0 }, { 146, 2, 1 },
      { 146, 2, 2 }, { 146, 2, 3 }, { 148, 2, 0 }, { 99, 3, 0 }, { 99, 3, 1 }, { 99, 3, 2 }, { 60, 5, 0 }, { 60, 5, 1 },
      { 60, 5, 2 }, { 60, 5, 3 }, { 76, 4, 0 }, { 76, 4, 1 }, { 102, 3, 0 }, { 102, 3, 1 }, { 154, 2, 0 },
      { 154, 2, 1 }, { 154, 2, 2 }, { 154, 2, 3 }, { 156, 2, 0 }, { 156, 2, 1 }, { 156, 2, 2 }, { 105, 3, 0 },
      { 158, 2, 0 }, { 158, 2, 1 }, { 158, 2, 2 }, { 158, 2, 3 }, { 80, 4, 0 }, { 80, 4, 1 }, { 80, 4, 2 },
      { 80, 4, 3 }, { 54, 6, 0 }, { 65, 5, 0 }, { 65, 5, 1 }, { 65, 5, 2 }, { 164, 2, 0 }, { 164, 2, 1 }, { 164, 2, 2 },
      { 164, 2, 3 }, { 166, 2, 0 }, { 111, 3, 0 }, { 111, 3, 1 }, { 111, 3, 2 }, { 84, 4, 0 }, { 84, 4, 1 },
      { 84, 4, 2 }, { 84, 4, 3 }, { 170, 2, 0 }, { 170, 2, 1 }, { 114, 3, 0 }, { 114, 3, 1 }, { 172, 2, 0 },
      { 172, 2, 1 }, { 172, 2, 2 }, { 172, 2, 3 }, { 174, 2, 0 }, { 174, 2, 1 }, { 70, 5, 0 }, { 117, 3, 0 },
      { 88, 4, 0 }, { 88, 4, 1 }, { 88, 4, 2 }, { 88, 4, 3 }, { 178, 2, 0 }, { 178, 2, 1 }, { 178, 2, 2 },
      { 178, 2, 3 }, { 60, 6, 0 }, { 60, 6, 1 }, { 60, 6, 2 }, { 60, 6, 3 }, { 182, 2, 0 }, { 182, 2, 1 },
      { 182, 2, 2 }, { 182, 2, 3 }, { 92, 4, 0 }, { 123, 3, 0 }, { 123, 3, 1 }, { 123, 3, 2 }, { 186, 2, 0 },
      { 186, 2, 1 }, { 186, 2, 2 }, { 75, 5, 0 }, { 188, 2, 0 }, { 188, 2, 1 }, { 126, 3, 0 }, { 126, 3, 1 },
      { 190, 2, 0 }, { 190, 2, 1 }, { 190, 2, 2 }, { 190, 2, 3 }, { 96, 4, 0 }, { 96, 4, 1 }, { 96, 4, 2 },
      { 129, 3, 0 }, { 194, 2, 0 }, { 194, 2, 1 }, { 194, 2, 2 }, { 194, 2, 3 }, { 196, 2, 0 }, { 196, 2, 1 },
      { 196, 2, 2 }, { 196, 2, 3 }, { 66, 6, 0 }, { 66, 6, 1 }, { 66, 6, 2 }, { 66, 6, 3 }, { 80, 5, 0 }, { 80, 5, 1 },
      { 80, 5, 2 }, { 80, 5, 3 }, { 202, 2, 0 }, { 135, 3, 0 }, { 135, 3, 1 }, { 135, 3, 2 }, { 204, 2, 0 },
      { 204, 2, 1 }, { 204, 2, 2 }, { 204, 2, 3 }, { 206, 2, 0 }, { 206, 2, 1 }, { 138, 3, 0 }, { 138, 3, 1 },
      { 104, 4, 0 }, { 104, 4, 1 }, { 104, 4, 2 }, { 104, 4, 3 }, { 210, 2, 0 }, { 210, 2, 1 }, { 210, 2, 2 },
      { 141, 3, 0 }, { 212, 2, 0 }, { 85, 5, 0 }, { 85, 5, 1 }, { 85, 5, 2 }, { 214, 2, 0 }, { 214, 2, 1 },
      { 214, 2, 2 }, { 214, 2, 3 }, { 72, 6, 0 }, { 72, 6, 1 }, { 72, 6, 2 }, { 72, 6, 3 }, { 218, 2, 0 },
      { 218, 2, 1 }, { 218, 2, 2 }, { 218, 2, 3 }, { 220, 2, 0 }, { 147, 3, 0 }, { 147, 3, 1 }, { 147, 3, 2 },
      { 222, 2, 0 }, { 222, 2, 1 }, { 222, 2, 2 }, { 222, 2, 3 }, { 112, 4, 0 }, { 112, 4, 1 }, { 90, 5, 0 },
      { 90, 5, 1 }, { 226, 2, 0 }, { 226, 2, 1 }, { 226, 2, 2 }, { 226, 2, 3 }, { 228, 2, 0 }, { 228, 2, 1 },
      { 228, 2, 2 }, { 153, 3, 0 }, { 230, 2, 0 }, { 230, 2, 1 }, { 230, 2, 2 }, { 230, 2, 3 }, { 116, 4, 0 },
      { 116, 4, 1 }, { 116, 4, 2 }, { 116, 4, 3 }, { 78, 6, 0 }, { 78, 6, 1 }, { 78, 6, 2 }, { 78, 6, 3 },
      { 236, 2, 0 }, { 236, 2, 1 }, { 236, 2, 2 }, { 95, 5, 0 }, { 238, 2, 0 }, { 159, 3, 0 }, { 159, 3, 1 },
      { 159, 3, 2 }, { 120, 4, 0 }, { 120, 4, 1 }, { 120, 4, 2 }, { 120, 4, 3 }, { 242, 2, 0 }, { 242, 2, 1 },
      { 162, 3, 0 }, { 162, 3, 1 }, { 244, 2, 0 }, { 244, 2, 1 }, { 244, 2, 2 }, { 244, 2, 3 }, { 246, 2, 0 },
      { 246, 2, 1 }, { 246, 2, 2 }, { 165, 3, 0 }, { 124, 4, 0 }, { 124, 4, 1 }, { 124, 4, 2 }, { 124, 4, 3 }, };
}