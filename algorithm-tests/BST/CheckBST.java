import java.util.ArrayList;
import java.util.List;

public class CheckBST {

  /** Check whether a BT (that puts dupes on the right) is a BST */
  static boolean checkBST(Node root) {
    return checkBST(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
  }

  private static boolean checkBST(Node node, int minValue, int maxValue) {
    if (node == null)
      return true;

    if (node.data <= minValue || node.data > maxValue)
      return false;

    return checkBST(node.left, minValue, node.data) &&
        checkBST(node.right, node.data, maxValue);
  }

  /** Check whether a BT (that puts dupes on the right) is an AVL */
  static boolean isAVL(Node root) {
    return checkBST(root) && isBalanced(root);
  }

  private static boolean isBalanced(Node node) {
    if (node == null)
      return true;

    int leftHeight = height(node.left);
    int rightHeight = height(node.right);

    return Math.abs(leftHeight - rightHeight) <= 1 &&
        isBalanced(node.left) &&
        isBalanced(node.right);
  }

  private static int height(Node node) {
    if (node == null)
      return 0;

    return 1 + Math.max(height(node.left), height(node.right));
  }

  public static Node createTreeFromList(Integer[] values) {
    if (values.length == 0) {
      return null;
    }
    List<Node> nodes = new ArrayList<>();
    for (Integer value : values) {
      nodes.add(value == null ? null : new Node(value));
    }
    for (int i = 0; i < values.length; i++) {
      if (nodes.get(i) != null) {
        int leftIndex = 2 * i + 1;
        int rightIndex = 2 * i + 2;
        if (leftIndex < values.length) {
          nodes.get(i).left = nodes.get(leftIndex);
        }
        if (rightIndex < values.length) {
          nodes.get(i).right = nodes.get(rightIndex);
        }
      }
    }
    return nodes.get(0);
  }

  public static void runTests() {
    Object[][] testCases = {
        // Edge cases
        { new Integer[] {}, true }, // Empty tree
        { new Integer[] { 5 }, true }, // Single node
        { new Integer[] { 2, 1, 3 }, true }, // Perfect BST
        { new Integer[] { 2, 3, 1 }, false }, // Invalid BST

        // Trees with missing nodes
        { new Integer[] { 10, 5, 15, null, null, null, 20 }, true }, // Valid with missing nodes
        { new Integer[] { 10, 5, 15, null, null, 11, 20 }, true }, // Valid
        { new Integer[] { 10, 5, 15, null, null, 22, 20 }, false }, // Invalid

        // Larger balanced trees
        { new Integer[] { 20, 10, 30, 5, 15, 25, 35 }, true }, // Larger valid BST
        { new Integer[] { 20, 10, 30, 5, 25, 15, 35 }, false }, // Invalid

        // Skewed trees
        { new Integer[] { 10, 5, null, 1, null, null, null }, true }, // Left-skewed valid BST
        { new Integer[] { 10, null, 15, null, null, null, 20 }, true }, // Right-skewed valid BST
        { new Integer[] { 10, null, 15, null, null, null, 5 }, false }, // Invalid

        // Subtree violations
        { new Integer[] { 10, 5, 15, 3, 7, 9, 20 }, false }, // 9 violates the BST property
        { new Integer[] { 10, 5, 15, 3, 7, 12, 25 }, true }, // All valid nodes

        // Duplicate values
        { new Integer[] { 10, 5, 15, 5, null, 12, 20 }, false }, // Duplicate left child
        { new Integer[] { 10, 5, 15, null, null, 12, 10 }, true }, // Duplicate right child
        { new Integer[] { 10, 5, 15, 5, null, 12, 12 }, true }, // Duplicate right child
        { new Integer[] { 10, 5, 15, 5, 5, null, 20 }, false }, // Duplicate left child

        // Extremely unbalanced trees
        { new Integer[] { 10, 9, null, 8, null, 7, null, 6 }, true }, // Deeply left-skewed valid BST
        { new Integer[] { 10, null, 11, null, 12, null, 13, null }, true }, // Deeply right-skewed valid BST

        // Complex mixed scenarios
        { new Integer[] { 50, 30, 70, 20, 40, 60, 80 }, true }, // Perfect BST
        { new Integer[] { 50, 30, 70, 20, 60, 40, 80 }, false }, // Invalid
    };

    int testNumber = 1;
    for (Object[] testCase : testCases) {
      Integer[] treeData = (Integer[]) testCase[0];
      boolean expected = (boolean) testCase[1];

      Node root = createTreeFromList(treeData);
      boolean result = checkBST(root);

      if (result != expected) {
        System.out.printf("Test case %d failed: got %b, expected %b%n", testNumber, result, expected);
      } else {
        System.out.printf("Test case %d passed.%n", testNumber);
      }
      testNumber++;
    }
  }

  public static void main(String[] args) {
    runTests();
  }
}
