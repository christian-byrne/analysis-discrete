import networkx as nx
import matplotlib.pyplot as plt

def is_valid_color(graph, vertex, color, coloring):
    for neighbor in graph.neighbors(vertex):
        if coloring.get(neighbor) == color:
            return False
    return True

def color_graph(graph, colors, coloring, vertex=0, node_mapping_reverse=None, prefix=""):
    if vertex == len(graph.nodes):
        return True

    for color in colors:
        if is_valid_color(graph, vertex, color, coloring):
            coloring[vertex] = color
            print(f"{prefix}Coloring vertex {node_mapping_reverse[vertex]} with {color}")

            if color_graph(graph, colors, coloring, vertex + 1, node_mapping_reverse, prefix + "  "):
                return True

            print(f"{prefix}Backtracking on vertex {node_mapping_reverse[vertex]} (removing color {color})")
            del coloring[vertex]

    return False


def plot_colored_graph(graph, coloring, node_mapping_reverse):
    pos = nx.spring_layout(graph)
    color_map = [coloring.get(node, 'white') for node in graph.nodes]
    labels = {node: node_mapping_reverse[node] for node in graph.nodes}
    nx.draw(graph, pos, labels=labels, with_labels=True, node_color=color_map, edge_color='black', font_weight='bold', node_size=500)
    plt.show()

# Define the graph based on the provided diagram
edges = [
    ('a', 'b'), ('a', 'c'), ('a', 'e'), ('a', 'd'), 
    ('b', 'd'), ('b', 'f'), ('b', 'e'), ('b', 'a'),
    ('c', 'd'), ('c', 'f'), ('c', 'e'), ("c", "a"),
    ("e", "f"),
    ('d', 'b'), ('d', 'f'), ("d", "a"), ("d", "c"),
]

def remove_symmetric_duplicates(edges):
    unique_edges = set()
    for edge in edges:
        if (edge[1], edge[0]) not in unique_edges:
            unique_edges.add(edge)
    return list(unique_edges)

edges = remove_symmetric_duplicates(edges)

graph = nx.Graph()
graph.add_edges_from(edges)

# Create a mapping from node names to indices
node_mapping = {node: i for i, node in enumerate(graph.nodes)}
node_mapping_reverse = {i: node for node, i in node_mapping.items()}

# Relabel the nodes in the graph with indices
graph = nx.relabel_nodes(graph, node_mapping)

# Define the colors and initialize coloring
colors = ['red', 'green', 'blue']
coloring = {}

# Color the graph
if color_graph(graph, colors, coloring, node_mapping_reverse=node_mapping_reverse):
    print("Graph coloring possible with three colors.")
    plot_colored_graph(graph, coloring, node_mapping_reverse)
else:
    print("Graph coloring not possible with three colors.")