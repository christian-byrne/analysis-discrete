import networkx as nx

def concentric_circle_dual_graph(labels):
    """
    Generates the dual graph of a map with concentric circles, using specified labels.

    Args:
        labels: A list of labels for the regions, starting with the outermost.

    Returns:
        A NetworkX graph object representing the dual graph.
    """

    G = nx.Graph()

    # Add nodes using the provided labels
    for label in labels:
        G.add_node(label)

    # Connect nodes based on adjacency
    for i in range(len(labels) - 1):
        G.add_edge(labels[i], labels[i + 1])

    return G

# Example usage with your labels
labels = ["D", "C", "B", "A"] 
dual_graph = concentric_circle_dual_graph(labels)

# Visualize the graph (optional)
import matplotlib.pyplot as plt

nx.draw(dual_graph, with_labels=True, node_color="skyblue", node_size=1500)
plt.savefig("dual_graph.png")
plt.show()
