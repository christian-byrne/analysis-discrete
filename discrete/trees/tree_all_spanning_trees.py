import networkx as nx
import matplotlib.pyplot as plt
from itertools import combinations

# Create the original graph
G = nx.Graph()
edges = [('a', 'b'), ('b', 'c'), ('b', 'd'), ('b', 'e'), ('c', 'd'), ('e', 'd'), ('e', 'f')]
G.add_edges_from(edges)

# Function to check if the given edges form a spanning tree
def is_spanning_tree(G, edges):
    T = nx.Graph()
    T.add_edges_from(edges)
    return nx.is_tree(T) and len(T.nodes) == len(G.nodes)

# Generate all combinations of edges and filter for spanning trees
all_spanning_trees = []
for edges_comb in combinations(G.edges, len(G.nodes) - 1):
    if is_spanning_tree(G, edges_comb):
        all_spanning_trees.append(edges_comb)

# Plot each spanning tree
fig, axs = plt.subplots(1, len(all_spanning_trees), figsize=(20, 5))

for i, tree_edges in enumerate(all_spanning_trees):
    T = nx.Graph()
    T.add_edges_from(tree_edges)
    pos = nx.spring_layout(T)
    nx.draw(T, pos, with_labels=True, node_color='lightblue', edge_color='gray', ax=axs[i])
    axs[i].set_title(f"Spanning Tree {i+1}")

# Save figure to a file
plt.savefig("all_spanning_trees.png")
plt.show()
