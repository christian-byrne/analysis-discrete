import networkx as nx
import matplotlib.pyplot as plt
import heapq

def prim_mst(graph, start_node):
    mst_edges = []
    visited = set([start_node])
    edges = [(weight, start_node, to) for to, weight in graph[start_node].items()]
    heapq.heapify(edges)

    print(f"Starting at node {start_node}")
    print(f"Initial edges: {edges}\n")

    while edges:
        weight, frm, to = heapq.heappop(edges)
        print(f"Selected edge ({frm}, {to}) with weight {weight}")

        if to not in visited:
            visited.add(to)
            mst_edges.append((frm, to, weight))
            print(f"Adding edge ({frm}, {to}) to MST")
            print(f"Visited nodes: {visited}")

            for to_next, weight in graph[to].items():
                if to_next not in visited:
                    heapq.heappush(edges, (weight, to, to_next))
                    print(f"Adding edge ({to}, {to_next}) with weight {weight} to the priority queue")

        print(f"Current MST edges: {mst_edges}\n")
    
    return mst_edges

def plot_graph(graph, mst_edges):
    G = nx.Graph()
    for frm, to, weight in mst_edges:
        G.add_edge(frm, to, weight=weight)

    pos = nx.spring_layout(G)
    labels = nx.get_edge_attributes(G, 'weight')
    nx.draw(G, pos, with_labels=True, node_color='lightblue', edge_color='gray', node_size=500)
    nx.draw_networkx_edge_labels(G, pos, edge_labels=labels)
    plt.show()

# Define the weighted graph based on the provided diagram
graph = {
    'a': {'b': 1, 'c': 4, 'e': 2},
    'b': {'a': 1, 'd': 3, 'e': 3},
    'c': {'a': 4, 'd': 1, 'e': 3},
    'd': {'b': 3, 'c': 1, 'e': 2},
    'e': {'a': 2, 'b': 3, 'c': 3, 'd': 2}
}

# Run Prim's algorithm starting from node 'a'
mst_edges = prim_mst(graph, 'a')
print("Minimum Spanning Tree edges:", mst_edges)

# Plot the resulting MST
plot_graph(graph, mst_edges)
