import networkx as nx
import matplotlib.pyplot as plt
from PIL import Image
import os


class AVLNode:
    def __init__(self, key):
        self.key = key
        self.height = 1
        self.left = None
        self.right = None


class AVLTree:
    def __init__(self):
        self.root = None
        self.image_dir = "avl_images"
        os.makedirs(self.image_dir, exist_ok=True)
        self.rebalancing_info = ""  # To store rebalancing details

    def insert(self, root, key):
        # Step 1: Perform normal BST insertion
        if not root:
            return AVLNode(key)
        elif key < root.key:
            root.left = self.insert(root.left, key)
        else:
            root.right = self.insert(root.right, key)

        # Step 2: Update height of the current node
        root.height = 1 + max(self.get_height(root.left), self.get_height(root.right))

        # Step 3: Get the balance factor
        balance = self.get_balance(root)

        # Step 4: Perform rotations to balance the tree
        if balance > 1 and key < root.left.key:  # Left Left Case
            self.rebalancing_info = f"Rebalancing: Single Right Rotation at {root.key}"
            return self.right_rotate(root)
        if balance < -1 and key > root.right.key:  # Right Right Case
            self.rebalancing_info = f"Rebalancing: Single Left Rotation at {root.key}"
            return self.left_rotate(root)
        if balance > 1 and key > root.left.key:  # Left Right Case
            self.rebalancing_info = (
                f"Rebalancing: Double Rotation (Left-Right) at {root.key}"
            )
            root.left = self.left_rotate(root.left)
            return self.right_rotate(root)
        if balance < -1 and key < root.right.key:  # Right Left Case
            self.rebalancing_info = (
                f"Rebalancing: Double Rotation (Right-Left) at {root.key}"
            )
            root.right = self.right_rotate(root.right)
            return self.left_rotate(root)

        self.rebalancing_info = ""  # No rebalancing occurred
        return root

    def left_rotate(self, z):
        y = z.right
        T2 = y.left

        y.left = z
        z.right = T2

        z.height = 1 + max(self.get_height(z.left), self.get_height(z.right))
        y.height = 1 + max(self.get_height(y.left), self.get_height(y.right))

        return y

    def right_rotate(self, z):
        y = z.left
        T3 = y.right

        y.right = z
        z.left = T3

        z.height = 1 + max(self.get_height(z.left), self.get_height(z.right))
        y.height = 1 + max(self.get_height(y.left), self.get_height(y.right))

        return y

    def get_height(self, node):
        if not node:
            return 0
        return node.height

    def get_balance(self, node):
        if not node:
            return 0
        return self.get_height(node.left) - self.get_height(node.right)

    def visualize(self, root, filename, title):
        graph = nx.DiGraph()
        self._add_edges(root, graph)
        pos = nx.nx_agraph.graphviz_layout(graph, prog="dot")
        labels = nx.get_node_attributes(graph, "label")
        nx.draw(
            graph,
            pos,
            labels=labels,
            with_labels=True,
            node_color="lightblue",
            node_size=2000,
            font_size=10,
        )
        plt.title(title, fontsize=12)
        plt.savefig(filename)
        plt.close()

    def _add_edges(self, node, graph, parent=None):
        if node:
            graph.add_node(node.key, label=node.key)
            if parent is not None:
                graph.add_edge(parent, node.key)
            self._add_edges(node.left, graph, node.key)
            self._add_edges(node.right, graph, node.key)

    def insert_and_visualize(self, keys):
        images = []
        for i, key in enumerate(keys):
            self.root = self.insert(self.root, key)
            title = (
                f"Inserted: {key}. {self.rebalancing_info or 'No Rebalancing Needed.'}"
            )
            filename = os.path.join(self.image_dir, f"step_{i + 1}.png")
            self.visualize(self.root, filename, title)
            images.append(filename)

        self.stitch_images(images)

    def stitch_images(self, image_paths):
        images = [Image.open(img) for img in image_paths]
        widths, heights = zip(*(img.size for img in images))

        total_width = sum(widths)
        max_height = max(heights)

        stitched_image = Image.new("RGB", (total_width, max_height))
        x_offset = 0
        for img in images:
            stitched_image.paste(img, (x_offset, 0))
            x_offset += img.width

        stitched_image.save(os.path.join(self.image_dir, "stitched_image.png"))
        print(
            f"Stitched image saved as {os.path.join(self.image_dir, 'stitched_image.png')}."
        )


if __name__ == "__main__":
    tree = AVLTree()
    letters = ["K", "T", "Y", "V", "W", "U", "G", "E", "N"]
    tree.insert_and_visualize(letters)
