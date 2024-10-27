import java.util.ArrayList;
import java.util.stream.Stream;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Optional;

import java.util.LinkedList;

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

    // Print list for verification (optional)
    public void printList() {
      if (head == null) {
        System.out.println("List is empty.");
        return;
      }

      Node current = head;
      do {
        System.out.print(padNum(current.value));
        current = current.next;
      } while (current != head);
      System.out.println();
    }
  }

  private void printOutput(ArrayList<CustomLinkedList> matrix) {
    for (int i = 0; i < matrix.size(); i++) {
      CustomLinkedList row = matrix.get(i);
      if (row.head == null) {
        System.out.println("  ·  ".repeat(COLS));
        continue;
      }
      row.printList();
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

  private void prettyPrintMatrixNoTranspose(ArrayList<CustomLinkedList> matrix) {
    int maxSize = 0;
    for (int i = 0; i < matrix.size(); i++) {
      CustomLinkedList row = matrix.get(i);
      if (row.size > maxSize) {
        maxSize = row.size;
      }
    }
    for (int i = 0; i < matrix.size(); i++) {
      // System.out.print("LinkedList: ");
      CustomLinkedList row = matrix.get(i);
      if (row.head == null) {
        System.out.println(" · ".repeat(maxSize));
        continue;
      }
      row.printList();
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
      System.out.println("i: " + i);
      CustomLinkedList row = matrix.get(i);
      for (int j = 0; j < shift; j++) {
        matrix.get(i - 1).prepend(new Node(row.popRight().value));
      }
    }

    // Remove the `shift` Infinity pad values from the last col
    for (int i = 0; i < shift; i++) {
      matrix.get(0).popRight();
    }

    // Remove the first col (contains only -Infinity values)
    // TODO: perhaps it's more efficient to not remove an item from the matrix, but
    // rather just skip this col in the final output step?
    matrix.remove(originalColCt - 1);
  }

  ArrayList<CustomLinkedList> columnsort(int[] arr) {
    SIZE = arr.length;
    System.out.println("Size of input: " + SIZE);
    List<List<Integer>> factors = factorize(SIZE);
    System.out.println("Factors: " + factors);
    List<List<Integer>> possibleDims = filterDimSizes(factors);
    List<Integer> dims;
    if (possibleDims.isEmpty()) {
      System.out.println("No valid dimensions found for the matrix.");
      dims = getOptimalDims(factors);
    } else {
      System.out.println("Possible dimensions: " + possibleDims);
      dims = getOptimalDims(possibleDims);
    }
    ROWS = dims.get(0);
    COLS = dims.get(1);
    ROWS = 6;
    COLS = 3;
    System.out.println("\nChosen Dimensions: [" + ROWS + ", " + COLS + "]");

    // Contrstuct the matrix, which will be a LinkedList of LinkedLists
    System.out.println("Constructing matrix:");
    ArrayList<CustomLinkedList> matrix = new ArrayList<>(COLS);
    int maxDim = Math.max(ROWS, COLS);
    System.out.println("Max dimension: " + maxDim);
    // for (int i = 0; i < maxDim; i++) {
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
    // rotate(matrix);
    // rotate(matrix);
    // rotate(matrix);
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

  private boolean isSDivisorOfR(int r, int s) {
    return r % s == 0;
  }

  private boolean isTwiceSMinus1Squared(int r, int s) {
    System.out.println("Testing row size: " + r + ", col size: " + s);
    System.out.println("Math.pow((s - 1), 2): " + Math.pow((s - 1), 2));
    boolean ret = r >= 2 * Math.pow((s - 1), 2);
    System.out.println("Result: " + ret);
    return ret;
  }

  private boolean isFactor(int n, int p) {
    return n % p == 0;
  }

  private List<Integer> getOptimalDims(List<List<Integer>> factors) {
    System.out.println("Finding optimal dimensions for:");
    factors.forEach(System.out::println);
    // Sort based on p (first factor in pair)
    factors.sort(Comparator.comparingInt(pair -> pair.get(0)));
    // Move pairs where p <= 2 to the end
    factors.sort(Comparator.comparingInt(pair -> pair.get(0) <= 2 ? 1 : 0));
    return factors.get(0);
  }

  /**
   * For a matrix of size n, filter factor pairs representing possible dim sizes
   * [row, col] to only include the dim sizes that satisfy these conditions:
   * 1. row size is even
   * 2. row size is at least twice the column size
   * 3. row size * col size = n
   * 
   * @param factors List of List of pairs of row and column sizes
   * @param n       Size of the matrix
   * @return List of List of pairs of row and column sizes
   */
  private List<List<Integer>> filterDimSizes(List<List<Integer>> factors) {
    System.out.println("Filtering dimensions for:");
    factors.forEach(System.out::println);
    return factors.stream()
        .filter(pair -> isSDivisorOfR(pair.get(0), pair.get(1)) && isTwiceSMinus1Squared(pair.get(0), pair.get(1)))
        .collect(Collectors.toList());
  }

  private List<List<Integer>> factorize(int n) {
    int sqrt = (int) Math.sqrt(n);
    return IntStream.rangeClosed(2, sqrt)
        .filter(i -> isFactor(n, i))
        .mapToObj(i -> i == n / i
            ? List.of(List.of(i, i))
            : List.of(List.of(i, n / i), List.of(n / i, i)))
        .flatMap(List::stream)
        .collect(Collectors.toList());
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
      System.out.println("File contains invalid number format.");
      return Optional.empty();
    }
  }
}
