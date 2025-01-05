import networkx as nx
import matplotlib.pyplot as plt

# Your call graph data
call_data = """
555-0011
555-1221
555-1333
555-8888
555-2222
555-0091
555-1200
555-0011 555-8888
555-0011 555-8888
555-0011 555-8888
555-8888 555-0011
555-8888 555-0011
555-2222 555-0091
555-2222 555-0091
555-1221 555-0011
555-1221 555-1333
555-1221 555-8888
555-1221 555-2222
555-1221 555-0091
555-1221 555-1200
555-1221 555-0011
555-1221 555-1333
555-1221 555-8888
555-1221 555-2222
555-1221 555-0091
555-1221 555-1200
555-1333 555-1200
555-1333 555-1221
555-1333 555-0011
"""

# Create a directed multigraph
G = nx.MultiDiGraph()

# Process the data
for line in call_data.splitlines():
    line = line.strip()
    if line:
        nodes = line.split()
        if len(nodes) == 1:
            G.add_node(nodes[0])  # Add node if it's a single entry
        elif len(nodes) == 2:
            G.add_edge(nodes[0], nodes[1])  # Add edge for call pairs

# Draw the graph with labels and styling (Modified)
pos = nx.spring_layout(G, k=0.15, iterations=20)  # Adjust parameters for better spacing

# Iterate over edges to draw each individually with adjusted positions
seen = {}
for edge in G.edges():
    src, dst = edge
    if (dst, src) in seen:
        current_rad = seen[(dst, src)]
        rad = current_rad * -1
        if rad < 0:
            rad -= 0.3
        else:
            rad += 0.3
        seen[(dst, src)] = rad
    else:
        rad = 0.1
        seen[(src, dst)] = rad

    nx.draw_networkx_edges(
        G,
        pos,
        edgelist=[(src, dst)],
        connectionstyle=f"arc3,rad={rad}",
        arrowstyle="-|>",  # Use a more distinct arrowhead style
        arrowsize=15,  # Increase arrow size for better visibility
        node_size=1500,
    )

nx.draw_networkx_nodes(G, pos, node_size=1500, node_color="skyblue")
nx.draw_networkx_labels(G, pos, font_size=10, font_color="black")
plt.title("Question 5 Call Graph")
plt.show()
