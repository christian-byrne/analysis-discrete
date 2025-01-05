class TreeNode:
    def __init__(self, value):
        self.value = value
        self.left = None
        self.right = None
        self.middle = None  # For nodes like 'j' which have more than two children

def preorder_traversal(node, result=None, depth=0):
    if result is None:
        result = []
    
    if node:
        # Visit the root node
        print(f"{'  ' * depth}Visit {node.value}")
        result.append(node.value)
        
        # Traverse the left subtree
        preorder_traversal(node.left, result, depth + 1)
        
        # Traverse the middle subtree if exists (for the specific tree)
        if node.middle:
            preorder_traversal(node.middle, result, depth + 1)
        
        # Traverse the right subtree
        preorder_traversal(node.right, result, depth + 1)
    
    return result

# Construct the tree as per the provided diagram
root = TreeNode('a')

root.left = TreeNode('b')
root.right = TreeNode('c')

root.left.left = TreeNode('d')
root.left.right = TreeNode('e')

root.left.right.left = TreeNode('i')

root.right.left = TreeNode('f')
root.right.right = TreeNode('h')

root.right.left.left = TreeNode('j')

root.right.left.left.left = TreeNode('m')
root.right.left.left.middle = TreeNode('n')
root.right.left.left.right = TreeNode('o')
root.right.left.left.right.right = TreeNode('p')

root.right.left.right = TreeNode('g')
root.right.right.right = TreeNode('l')

# Perform the preorder traversal and print the process
print("Preorder Traversal:")
traversal_result = preorder_traversal(root)
print("\nTraversal Order:", traversal_result)
