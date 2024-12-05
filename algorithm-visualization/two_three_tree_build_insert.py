import networkx as nx
import matplotlib.pyplot as plt
from PIL import Image
import os


class TwoThreeNode:
    def __init__(self, keys=None, children=None):
        self.keys = keys or []  # Keys in the node (1 or 2 keys for 2–3 tree)
        self.children = children or []  # Children nodes (2 or 3 children for 2–3 tree)

    def is_leaf(self):
        return len(self.children) == 0

    def __str__(self):
        return f"Keys: {self.keys}, Children: {len(self.children)}"


class TwoThreeTree:
    def __init__(self):
        self.root = None
        self.image_dir = "two_three_tree_images"
        os.makedirs(self.image_dir, exist_ok=True)

    def insert(self, key):
        if not self.root:
            self.root = TwoThreeNode(keys=[key])
        else:
            # Perform insertion and handle splits
            new_root = self.insert_into_node(self.root, key)
            if isinstance(new_root, TwoThreeNode):
                self.root = new_root  # Update root if tree height increases

    def insert_into_node(self, node, key):
        if node.is_leaf():
            # Insert key into the current leaf node
            node.keys.append(key)
            node.keys.sort()

            # If the node has 3 keys, split it
            if len(node.keys) > 2:
                return self.split_node(node)

            return node
        else:
            # Determine which child to recurse into
            if key < node.keys[0]:
                child_index = 0
            elif len(node.keys) == 1 or key < node.keys[1]:
                child_index = 1
            else:
                child_index = 2

            # Ensure child_index is valid
            if child_index >= len(node.children):
                raise IndexError("Child index out of range during insertion")

            # Recursive insertion into the selected child
            split_result = self.insert_into_node(node.children[child_index], key)

            # Handle splits in child nodes
            if isinstance(split_result, TwoThreeNode):
                promoted_key = split_result.keys[0]
                left_child = split_result.children[0]
                right_child = split_result.children[1]

                # Insert the promoted key into the current node
                node.keys.insert(child_index, promoted_key)
                node.children[child_index] = left_child
                node.children.insert(child_index + 1, right_child)

                # If the current node now has 3 keys, split it
                if len(node.keys) > 2:
                    return self.split_node(node)

        return node

    def split_node(self, node):
        """
        Split a node with 3 keys into two nodes and return the promoted middle key.
        """
        middle_key = node.keys[1]
        left_node = TwoThreeNode(keys=[node.keys[0]], children=node.children[:2])
        right_node = TwoThreeNode(keys=[node.keys[2]], children=node.children[2:])

        # Handle case where children may not exist
        if len(left_node.children) < 2:
            left_node.children = []
        if len(right_node.children) < 2:
            right_node.children = []

        # Return a new temporary node containing the middle key
        return TwoThreeNode(keys=[middle_key], children=[left_node, right_node])

    def visualize(self, filename, title):
        graph = nx.DiGraph()
        self._add_edges(self.root, graph)
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

    def _add_edges(self, node, graph, parent_label=None):
        if node:
            node_label = "\n".join(map(str, node.keys))
            graph.add_node(node_label, label=node_label)
            if parent_label:
                graph.add_edge(parent_label, node_label)
            for child in node.children:
                self._add_edges(child, graph, node_label)

    def build_tree_and_visualize(self, keys):
        images = []
        intermediate_tree_path = None
        for i, key in enumerate(keys):
            self.insert(key)
            title = f"Inserted: {key}"
            filename = os.path.join(self.image_dir, f"step_{i + 1}.png")
            self.visualize(filename, title)
            images.append(filename)

            # Save intermediate tree state after inserting 27
            if key == 27:
                intermediate_tree_path = filename

        self.stitch_images(images)
        print(f"Intermediate tree after 27 inserted saved at: {intermediate_tree_path}")

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


# Example Usage
if __name__ == "__main__":
    tree = TwoThreeTree()
    keys = [6, 9, 20, 8, 14, 27, 43, 22, 30, 19]
    tree.build_tree_and_visualize(keys)
