class TreeNode:
    def __init__(self, value):
        self.value = value
        self.left = None
        self.right = None

def iterative_preorder_traversal(root):
    if not root:
        return []

    stack = [root]
    result = []

    while stack:
        node = stack.pop()
        result.append(node.value)
        print(f"Visit {node.value}")  # Print the step

        if node.right:
            stack.append(node.right)
            print(f"Push {node.right.value} to stack")  # Print the step
        if node.left:
            stack.append(node.left)
            print(f"Push {node.left.value} to stack")  # Print the step

    return result

# Construct the tree as per the provided diagram
root = TreeNode('a')
root.left = TreeNode('b')
root.right = TreeNode('e')

root.left.left = TreeNode('c')
root.left.right = TreeNode('d')

root.right.right = TreeNode('f')

# Perform the iterative preorder traversal and print the process
print("Iterative Preorder Traversal Steps:")
traversal_result = iterative_preorder_traversal(root)
print("\nTraversal Order:", traversal_result)
