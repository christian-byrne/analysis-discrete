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

public class ColumnSort {
  private static int ROWS;
  private static int COLS;
  private static int SIZE;
  private static int OVERFLOW;
  private static final int MIN_MATRIX_SIZE = 16;
  private static final boolean DEBUG = false;

  private static void printIfDebug(String message) {
    if (DEBUG) {
      System.out.println(message);
    }
  }

  public static void columnsort(int[] arr, int rows, int cols, int overflow) {
    SIZE = arr.length;
    if (SIZE < MIN_MATRIX_SIZE) {
      insertionSort(arr, 0);
      return;
    }
    ROWS = rows;
    COLS = cols;
    OVERFLOW = overflow;
    ArrayList<DCLinkedList> matrix = createMatrix(arr);
    sortColumns(matrix);
    printIfDebug("After Step 1: Sort columns");
    printMatrix(matrix);
    transposeAndReshape(matrix);
    printIfDebug("After Step 2: Transpose and reshape");
    printMatrix(matrix);
    sortColumns(matrix);
    printIfDebug("After Step 3: Sort columns");
    printMatrix(matrix);
    reshapeAndTranspose(matrix);
    printIfDebug("After Step 4: Reshape and transpose");
    printMatrix(matrix);
    sortColumns(matrix);
    printIfDebug("After Step 5: Sort columns");
    printMatrix(matrix);
    shiftDownHalfR(matrix);
    printIfDebug("After Step 6: Shift down half R");
    printMatrix(matrix);
    sortColumns(matrix);
    printIfDebug("After Step 7: Sort columns");
    printMatrix(matrix);
    shiftUpHalfR(matrix);
    printIfDebug("After Step 8: Shift up half R");
    printMatrix(matrix);
    sortColumns(matrix);
    insertionSort(arr, SIZE - OVERFLOW); // Sort the tail overflow of original array
    mergeWithOverflow(arr, matrix); // Merge the matrix cols with the sorted overflow partition
  }

  // Nested Node class for custom linked list
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

  // DCLinkedList class with circular doubly linked list structure
  private static final class DCLinkedList {
    Node head;

    // Method to prepend a node to the start of the list
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

    // Method to append a node to the end of the list
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

    // Method to remove the last element and return its value
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

    // Method to remove the first element and return its value
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

  private static String padNumber(int num, int space) {
    String numStr = String.valueOf(num);
    int pad = space - numStr.length();
    return " ".repeat(pad) + numStr;
  }

  private static void printMatrix(ArrayList<DCLinkedList> matrix) {
    if (!DEBUG) {
      return;
    }
    System.out.println("Matrix:");
    for (DCLinkedList col : matrix) {
      Node current = col.head;
      int space = 6;
      int pad = space - String.valueOf(current.value).length();
      do {
        if (current.value == Integer.MIN_VALUE || current.value == Integer.MAX_VALUE) {
          System.out.print("     ·");
        } else {
          System.out.print(padNumber(current.value, space));
        }
        current = current.next;
      } while (current != col.head);
      System.out.println();
    }
    System.out.println();
  }

  private static final ArrayList<DCLinkedList> createMatrix(int[] arr) {
    ArrayList<DCLinkedList> matrix = new ArrayList<>(COLS);
    for (int i = COLS - 1; i >= 0; i--) {
      DCLinkedList col = new DCLinkedList();
      for (int j = 0; j < ROWS; j++) {
        col.append(arr[i * ROWS + j]);
      }
      matrix.add(col);
    }
    return matrix;
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

  private static final void transposeAndReshape(ArrayList<DCLinkedList> matrix) {
    for (int i = 0; i < matrix.size(); i++) {
      for (int j = 0; j < COLS; j++) {
        int targetIndex = j % matrix.size();
        matrix.get(targetIndex).prepend(matrix.get(i).popRight());
      }
    }
  }

  private static final void reshapeAndTranspose(ArrayList<DCLinkedList> matrix) {
    for (int i = COLS - 1; i >= 0; i--) {
      for (int j = 0; j < ROWS; j++) {
        int offset = j % COLS;
        int targetIndex = COLS - 1 - offset;
        matrix.get(i).append(matrix.get(targetIndex).popLeft().value);
      }
    }
  }

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

  private static final void shiftDownHalfR(ArrayList<DCLinkedList> matrix) {
    int shift = (ROWS / 2) - 1;
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

    // Ensure shape is preserved when rows is odd
    // if (ROWS % 2 == 0) {
    // matrix.get(lastIndex).prepend(new Node(Integer.MIN_VALUE));
    // }
  }

  private static final void shiftUpHalfR(ArrayList<DCLinkedList> matrix) {
    int shift = (ROWS / 2) - 1;
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

  private static void mergeWithOverflow(int[] arr, ArrayList<DCLinkedList> matrix) {
    int curOverflowIdx = SIZE - OVERFLOW;
    int curOverflowVal = curOverflowIdx < SIZE ? arr[curOverflowIdx] : Integer.MAX_VALUE;

    // The last column contains the negative infinity values, so we start from the
    // second-to-last column
    int curColIndex = matrix.size() - 2;
    int curColVal = curColIndex >= 0 ? matrix.get(curColIndex).popLeft().value : Integer.MAX_VALUE;

    for (int i = 0; i < SIZE; i++) {
      if (curColIndex >= 0 && curColVal < curOverflowVal) {
        arr[i] = curColVal;
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
        arr[i] = curOverflowVal;
        curOverflowIdx++;
        if (curOverflowIdx < SIZE) {
          curOverflowVal = arr[curOverflowIdx];
        } else {
          curOverflowVal = Integer.MAX_VALUE; // Array exhausted
        }
      }
    }
  }
}
