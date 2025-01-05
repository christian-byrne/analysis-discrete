import networkx as nx
import matplotlib.pyplot as plt

def regions_dual_graph(labels):
    """Generates the dual graph for regions in a map."""
    G = nx.Graph()
    for label in labels:
        G.add_node(label)

    # Add edges based on the corrected connections
    G.add_edges_from([('A', 'B'), ('B', 'D'), ('B', 'C'), ('C', 'E'), ('D', 'F')])

    return G

# Example usage with the region labels
labels = ["A", "B", "C", "D", "E", "F"]  # Removed 'G' for outer region
dual_graph = regions_dual_graph(labels)

# Visualize the graph
pos = nx.spring_layout(dual_graph)  # For better layout
nx.draw(dual_graph, pos, with_labels=True, node_color="skyblue", node_size=1500, font_size=12)
plt.savefig("dual_graph_regions_corrected.png") 
plt.show()
