import matplotlib.pyplot as plt
import networkx as nx


class Node:
    def __init__(self, data, left=None, right=None):
        self.data = data
        self.left = left
        self.right = right


def check_bst(root: Node):
    """Given the root to a binary tree, returns true if the tree is a BST and false if it is not"""
    if not root:
        return True

    def check_bst_(root: Node, min_value: int, max_value: int):
        if not root:
            return True
        
        # Check if the current node's data violates the BST property
        if not (min_value <= root.data <= max_value):
            return False
        
        # Recursively check the left and right subtrees
        return (check_bst_(root.left, min_value, root.data) and
                check_bst_(root.right, root.data, max_value))

    return check_bst_(root, float('-inf'), float('inf'))


def visualize_tree(root):
    """
    Visualize a binary tree in a pyramid-like layout using NetworkX and matplotlib.

    Args:
        root (Node): Root of the binary tree.
    """
    if not root:
        print("The tree is empty.")
        return

    # Create a directed graph
    tree_graph = nx.DiGraph()

    # Helper function to add edges recursively
    def add_edges(node, pos=0, depth=0, parent=None):
        if node is None:
            return
        tree_graph.add_node(pos, label=node.data, depth=depth)
        if parent is not None:
            tree_graph.add_edge(parent, pos)
        add_edges(node.left, pos=2 * pos + 1, depth=depth + 1, parent=pos)
        add_edges(node.right, pos=2 * pos + 2, depth=depth + 1, parent=pos)

    # Add all edges to the graph
    add_edges(root)

    # Generate positions for nodes
    def generate_positions(tree, node=0, depth=0, x=0.5, dx=0.25):
        if node not in tree.nodes:
            return {}
        positions = {node: (x, -depth)}
        positions.update(
            generate_positions(
                tree, node=2 * node + 1, depth=depth + 1, x=x - dx, dx=dx / 2
            )
        )
        positions.update(
            generate_positions(
                tree, node=2 * node + 2, depth=depth + 1, x=x + dx, dx=dx / 2
            )
        )
        return positions

    pos = generate_positions(tree_graph)

    # Draw the graph
    labels = nx.get_node_attributes(tree_graph, "label")
    nx.draw(
        tree_graph,
        pos,
        with_labels=True,
        labels=labels,
        node_size=2000,
        node_color="lightblue",
        font_size=10,
    )
    plt.title("Binary Search Tree Visualization")
    plt.show()


def test_check_bst():
    def create_tree_from_list(lst):
        """Helper to create a tree from a list (level order)."""
        if not lst:
            return None
        nodes = [None if val is None else Node(val) for val in lst]
        for index in range(len(lst)):
            if nodes[index] is not None:
                left_index = 2 * index + 1
                right_index = 2 * index + 2
                if left_index < len(lst):
                    nodes[index].left = nodes[left_index]
                if right_index < len(lst):
                    nodes[index].right = nodes[right_index]
        return nodes[0]

    test_cases = [
        # Edge cases
        ([], True),  # Empty tree
        ([5], True),  # Single node
        ([2, 1, 3], True),  # Perfect BST
        ([2, 3, 1], False),  # Invalid BST
        # Trees with missing nodes
        ([10, 5, 15, None, None, None, 20], True),  # Valid with missing nodes
        (
            [10, 5, 15, None, None, 11, 20],
            True,
        ), # Valid
        (
            [10, 5, 15, None, None, 22, 20],
            False,
        ),  # Invalid due to 22 in the right subtree
        (
            [10, 16, 15, None, None, 22, 20],
            False,
        ),  # Invalid due to 22 in the right subtree
        # Larger balanced trees
        ([20, 10, 30, 5, 15, 25, 35], True),  # Larger valid BST
        (
            [20, 10, 30, 5, 25, 15, 35],
            False,
        ),  # Invalid because 25 is on the left subtree
        # Skewed trees
        ([10, 5, None, 1, None, None, None], True),  # Left-skewed valid BST
        ([10, None, 15, None, None, None, 20], True),  # Right-skewed valid BST
        ([10, None, 15, None, None, None, 5], False),  # Invalid right-skewed
        # Subtree violations
        ([10, 5, 15, 3, 7, 9, 20], False),  # 9 violates the BST property
        ([10, 5, 15, 3, 7, 12, 25], True),  # All valid nodes
        # Duplicate values
        ([10, 5, 15, 5, None, 12, 20], False),  # Duplicate left child
        ([10, 5, 15, None, None, 12, 10], False),  # Duplicate right child
        # Extremely unbalanced trees
        ([10, 9, None, 8, None, 7, None, 6], True),  # Deeply left-skewed valid BST
        (
            [10, None, 11, None, 12, None, 13, None],
            True,
        ),  # Deeply right-skewed valid BST
        # Complex mixed scenarios
        ([50, 30, 70, 20, 40, 60, 80], True),  # Perfect BST with multiple levels
        (
            [50, 30, 70, 20, 60, 40, 80],
            False,
        ),  # Invalid because 60 is in the wrong subtree
        (
            [50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45, 55, 65, 75, 85],
            True,
        ),  # Larger valid BST
        (
            [50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45, 65, 55, 75, 85],
            False,
        ),  # Subtree violation
        # Edge values
        # ([float("-inf"), None, float("inf")], True),  # Valid extreme values
        # ([float("inf"), float("-inf"), None], False),  # Invalid extreme values
        ([0, -1, 1], True),  # Valid with 0 at root
        ([0, 1, -1], False),  # Invalid with 0 at root
    ]

    for i, (tree_list, expected) in enumerate(test_cases):
        root = create_tree_from_list(tree_list)
        result = check_bst(root)
        if result != expected:
            print(f"Test case {i+1} failed: got {result}, expected {expected}")
            print("Visualizing the tree for debugging:")
            visualize_tree(root)
            assert False, f"Test case {i+1} failed"

    print("All extended test cases passed.")


# Run the tests
test_check_bst()
