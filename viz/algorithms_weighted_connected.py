import numpy as np
import graphviz
from collections import deque
from typing import List
from rich import print
import heapq

from rich.console import Console
from rich.table import Table
from rich.panel import Panel
import rich.box
import heapq


ALGORITHM_CHOICE = "kruskal"
# ALGORITHM_CHOICE = "dijkstra"
START = "4"
END = "2"

console = Console()


class Graph:
    def __init__(self, nodes):
        """Initializes the graph with nodes and an adjacency matrix."""
        self.nodes = nodes
        self.size = len(nodes)
        self.node_indices = {node: i for i, node in enumerate(nodes)}
        self.adjacency_matrix = np.zeros((self.size, self.size))
        self.edges = []

    def add_edge(self, node1, node2, weight):
        """Adds an undirected edge with the given weight between two nodes."""
        # Use node_indices to map the node names to matrix indices
        i, j = self.node_indices[node1], self.node_indices[node2]
        self.adjacency_matrix[i][j] = weight
        self.adjacency_matrix[j][i] = weight  # Ensure undirected edge

        # Add the edge using node names and weights without converting to integers
        self.edges.append((node1, node2, weight))

    def visualize_graph(self):
        """Visualizes the graph using Graphviz."""
        graph = graphviz.Graph()

        # Add nodes to the graph
        for node in self.nodes:
            graph.node(str(node))  # Use str to ensure compatibility with Graphviz

        # Add edges based on the adjacency matrix
        for i in range(self.size):
            for j in range(i + 1, self.size):  # Avoid duplicates in undirected graph
                weight = self.adjacency_matrix[i][j]
                if weight > 0:
                    graph.edge(
                        str(self.nodes[i]), str(self.nodes[j]), label=str(weight)
                    )

        graph.render("graph", format="png", cleanup=True)
        graph.view()


# Define nodes

# Add edges - Figure 11.19
nodes = ["1", "2", "3", "4", "5", "6"]
graph = Graph(nodes)
graph.add_edge("1", "2", 10)
graph.add_edge("1", "4", 20)
graph.add_edge("1", "6", 2)
graph.add_edge("2", "3", 3)
graph.add_edge("2", "4", 5)
graph.add_edge("3", "5", 15)
graph.add_edge("4", "6", 10)
graph.add_edge("4", "5", 11)
graph.add_edge("5", "6", 3)

# Add edges - Figure 11.16
# nodes = ["A", "B", "C", "D", "E"]
# graph = Graph(nodes)
# graph.add_edge("A", "B", 10)
# graph.add_edge("A", "D", 20)
# graph.add_edge("A", "C", 3)
# graph.add_edge("B", "D", 5)
# graph.add_edge("C", "B", 2)
# graph.add_edge("C", "E", 15)
# graph.add_edge("D", "E", 11)

# Visualize the graph
# graph.visualize_graph()


def recursive_DFS_SP(start_node: int, end_node: int):
    paths = []

    def shortest_path(
        start_node: int, end_node: int, cur_weight: int, cur_path: List[int]
    ):
        edges = [
            (index, cell)
            for (index, cell) in enumerate(graph.adjacency_matrix[start_node])
            if cell != 0
        ]

        edges = deque(edges)
        while edges:
            probeto, weight = edges.pop()

            if probeto == end_node:
                paths.append((cur_weight + weight, cur_path + [end_node]))
                return
            elif probeto in cur_path:
                return
            else:
                shortest_path(
                    probeto, end_node, cur_weight + weight, cur_path + [probeto]
                )

    shortest_path(start_node, end_node, 0, [])
    for path in sorted(paths, key=lambda x: x[0]):
        print(path)

    return sorted(paths, key=lambda x: x[0])[0]


from rich.console import Console
from rich.table import Table
from rich.panel import Panel
import heapq

console = Console()


def dijkstra_shortest_path(graph, start_node, end_node, verbose=False):
    pq = [
        (0, start_node, [], [])
    ]  # (cumulative weight, node, path of nodes, path of edges)
    visited = set()

    while pq:
        # Display priority queue status
        pq_table = Table(title="Priority Queue")
        pq_table.add_column("Weight", justify="center")
        pq_table.add_column("Node", justify="center")
        pq_table.add_column("Path (Nodes)", justify="center")
        pq_table.add_column("Path (Edges)", justify="center")
        for weight, node, path_nodes, path_edges in pq:
            pq_table.add_row(
                str(weight),
                str(node),
                " -> ".join(map(str, path_nodes)),
                " -> ".join(
                    f"({edge[0]}, {edge[1]}, {edge[2]})" for edge in path_edges
                ),
            )

        if verbose:
            console.print(pq_table)

        (cur_weight, node, path_nodes, path_edges) = heapq.heappop(pq)

        # Current node processing
        if verbose:
            console.print(
                Panel(
                    f"[bold green]Current Node: {node}[/bold green]\n"
                    f"Weight: [bold yellow]{cur_weight}[/bold yellow]\n"
                    f"Path (Nodes): {' -> '.join(map(str, path_nodes))}\n"
                    f"Path (Edges): {' -> '.join(f'({e[0]}, {e[1]}, {e[2]})' for e in path_edges)}"
                )
            )

        if node in visited:
            if verbose:
                console.print(f"[red]Node {node} already visited. Skipping...[/red]\n")
            continue

        visited.add(node)

        if verbose:
            console.print(f"[green]Visited nodes:[/green] {visited}\n")

        path_nodes = path_nodes + [node]

        # End node reached
        if node == end_node:
            if verbose:
                console.print(
                    Panel(
                        f"[bold green]Reached end node {end_node}![/bold green]\n"
                        f"Shortest Path (Nodes): {' -> '.join(map(str, path_nodes))}\n"
                        f"Shortest Path (Edges): {' -> '.join(f'({e[0]}, {e[1]}, {e[2]})' for e in path_edges)}\n"
                        f"Total Weight: {cur_weight}",
                        border_style="bold green",
                    )
                )
            return cur_weight, path_nodes, path_edges

        # Exploring neighbors
        neighbor_table = Table(title=f"Neighbors of Node {node}", box="SIMPLE")
        neighbor_table.add_column("Neighbor", justify="center")
        neighbor_table.add_column("Weight", justify="center")
        neighbor_table.add_column("Action", justify="center")

        node_index = (
            int(node) - 1
        )  # Convert `node` from string to integer for adjacency matrix indexing

        for neighbor_index, weight in enumerate(graph.adjacency_matrix[node_index]):
            neighbor = str(neighbor_index + 1)

            if weight != 0 and neighbor not in visited:
                new_edge = (node, neighbor, weight)  # Record this edge
                heapq.heappush(
                    pq,
                    (
                        cur_weight + weight,
                        neighbor,
                        path_nodes,
                        path_edges + [new_edge],
                    ),
                )
                neighbor_table.add_row(
                    neighbor, str(weight), "[green]Added to PQ[/green]"
                )
            else:
                action = (
                    "[yellow]Already visited[/yellow]"
                    if neighbor in visited
                    else "[red]No path[/red]"
                )
                neighbor_table.add_row(neighbor, str(weight), action)

        if verbose:
            console.print(neighbor_table)
            console.print("\n" + "-" * 60 + "\n")

    # When no path exists between start_node and end_node
    console.print(
        f"[bold red]No path exists between node {start_node} and {end_node}.[/bold red]"
    )
    return None


def bellman_ford_shortest_path(
    graph, start_node, end_node, verbose=False, directed=False
):

    def has_edge(node1, node2, edge_list):
        return any(
            (edge[0] == node1 and edge[1] == node2)
            or (edge[0] == node2 and edge[1] == node1)
            for edge in edge_list
        )

    def reflect_edges(edges):
        """For Bellman-Ford, the edge list needs to include both directions."""
        reflected_edges = []
        for node1, node2, weight in edges:
            if not has_edge(node2, node1, reflected_edges):
                reflected_edges.append((node2, node1, weight))
            reflected_edges.append((node1, node2, weight))
        return reflected_edges

    def add_distances_row(table, distances):
        table.add_row(
            *[
                str(dist) if dist != float("inf") else "∞"
                for dist in list(distances.values())
            ]
        )

    tb = Table(title="Bellman-Ford Shortest Path", box=rich.box.SIMPLE)

    # Step 0: Reflect edges if necessary
    if directed:
        edges = graph.edges
    else:
        edges = reflect_edges(graph.edges)

    # Visualize the edge list
    print(
        f"$G = (V, E)$ where $V = "
        + "{"
        + ", ".join(graph.nodes)
        + "}$, $E = "
        + "{"
        + ", ".join([f"({edge[0]}, {edge[1]})" for edge in graph.edges])
        + "}$"
    )
    for node1, node2, weight in graph.edges:
        print(f"\n\n$w({node1}, {node2}) = {weight}$")

    # Step 1: Initialize distances
    distances = {node: float("inf") for node in graph.nodes}
    distances[start_node] = 0

    for _ in range(graph.size):
        tb.add_column(f"Node {_ + 1}", justify="center")

    # Step 2: Relax edges repeatedly
    for _ in range(graph.size - 1):
        new_row = {}
        for node1, node2, weight in edges:
            console.print(
                f"Testing edge between {node1} and {node2} with weight {weight}"
            )
            if distances[node1] + weight < distances[node2]:
                new_row[node2] = distances[node1] + weight
                # NOTE: Don't in-place edit (so we can visualize the step-by-step changes)
                # distances[node2] = distances[node1] + weight

        add_distances_row(tb, distances)

        # Apply changes after checking all edges (this would be done inplace normally and not require a new row)
        for node, dist in new_row.items():
            distances[node] = dist

    add_distances_row(tb, distances)

    console.print(tb)
    # Step 3: Check for negative-weight cycles
    for node1, node2, weight in edges:
        if distances[node1] + weight < distances[node2]:
            console.print(
                f"[bold red]Negative-weight cycle detected between nodes {node1} and {node2}.[/bold red]"
            )
            return None

    return distances[end_node]


def kruskal_mst(graph):
    def print_equivalence_array(equivalence_array):
        """Print using markdown formmatting for a table."""
        for node in graph.nodes:
            print(f"| {node}", end="")
        print("|")
        for node in graph.nodes:
            print("|---", end="")
        print("|")
        for node in graph.nodes:
            print(f"| {equivalence_array[node]}", end="")
        print("|")

    equivalence_array = {node: node for node in graph.nodes}
    mst = []

    # Sort edges by weight
    edges = sorted(graph.edges, key=lambda x: x[2])

    print("\n\nEdges sorted by weight:\n\n")
    print(
        "{" + ", ".join([f"({int(edge[0])}, {int(edge[1])})" for edge in edges]) + "}"
    )

    print("\n\nInitial Equivalence Array:\n\n")
    print_equivalence_array(equivalence_array)

    for i, edge in enumerate(edges):
        node1, node2, weight = edge
        skipped = []
        if equivalence_array[node1] != equivalence_array[node2]:
            mst.append(edge)
            old_equivalence = equivalence_array[node2]
            for node, equivalence in equivalence_array.items():
                if equivalence == old_equivalence:
                    equivalence_array[node] = equivalence_array[node1]

            if skipped:
                print(
                    f"\n\nSkipped edges after adding edge $({int(edge[0])}, {int(edge[1])})$:\n\n"
                )
                for edge in skipped:
                    print(f"$({int(edge[0])}, {int(edge[1])})$")
            skipped = []
            print(
                f"\n\n    Equivalence class of $v_{node1}_equivclass = {equivalence_array[node1]} \\neq v_{node2}_equivclass = {equivalence_array[node2]} \rightarrow$"
            )
            print(f"    Added edge $({int(edge[0])}, {int(edge[1])})$ to MST")

            print(f"    Equivalence array after adding edge:\n\n")
            print_equivalence_array(equivalence_array)

        else:
            skipped.append(edge)

    return mst


def prims_mst(graph, start_index):
    visited = set()
    mst = []
    pq = []

    # Reflect edges to ensure undirected behavior
    edges = graph.edges  # Ensure this returns bidirectional edges

    # Initialize priority queue with edges from the start node
    for edge in edges:
        print(f"Edge: {edge}")
        print(f"Start index: {start_index}")
        if edge[0] == start_index:
            heapq.heappush(pq, (edge[2], edge[0], edge[1]))
        elif edge[1] == start_index:
            heapq.heappush(pq, (edge[2], edge[1], edge[0]))

    visited.add(start_index)
    console.print(
        f"Starting at node {int(start_index)}, initial edges added to PQ: {pq}"
    )

    # Process until all nodes are visited
    while len(visited) < len(graph.nodes) and pq:
        # Visualize current priority queue
        console.print(
            Panel(
                f"[bold]Priority Queue[/bold]\n{pq}",
                title="Current Priority Queue",
                border_style="bold",
            )
        )

        # Pop the smallest edge from the priority queue
        weight, node1, node2 = heapq.heappop(pq)
        console.print(f"Popped edge ({node1}, {node2}, {weight}) from PQ")

        # Only add to MST if node2 is not yet visited
        if node2 not in visited:
            console.print(f"Adding edge ({node1}, {node2}, {weight}) to MST")
            mst.append((node1, node2, weight))
            visited.add(node2)

            # Add edges from the newly visited node to the priority queue
            for edge in edges:
                if edge[0] == node2 and edge[1] not in visited:
                    heapq.heappush(pq, (edge[2], edge[0], edge[1]))
                    console.print(f"Added edge ({edge[0]}, {edge[1]}, {edge[2]}) to PQ")
                elif edge[1] == node2 and edge[0] not in visited:
                    heapq.heappush(pq, (edge[2], edge[1], edge[0]))
                    console.print(f"Added edge ({edge[1]}, {edge[0]}, {edge[2]}) to PQ")
        else:
            console.print(
                f"Skipping edge ({node1}, {node2}, {weight}) as both nodes are in MST"
            )

    console.print(f"Final MST: {mst}")
    return mst


# match ALGORITHM_CHOICE:
#     case "kruskal":
#         print(f"Minimum Spanning Tree: {kruskal_mst(graph)}")
#     case "prim":
#         print(f"Minimum Spanning Tree: {prims_mst(graph, '3')}")
#     case "recursive-dfs":
#         print(
#             f"Shortest path from Node {START} to Node {END}: {recursive_DFS_SP(1, 5)}"
#         )
#     case "dijkstra":
#         print(
#             f"Shortest path from Node {START} to Node {END}: {dijkstra_shortest_path(graph, START, END)}"
#         )
#     case "bellman-ford":
#         print(
#             f"Shortest path from Node {START} to Node {END}: {bellman_ford_shortest_path(graph, START, END)}"
#         )
#     case _:
#         print(
#             "Invalid algorithm choice. Please choose 'recursive-dfs', 'dijkstra', or 'bellman-ford'."
#         )

# Iterate over every other vertex besides the start vertex, run dijkstra's algorithm, accumulate all the edges in each of the calculated shortest paths, and add them to a single set, then print the set:
edges = set()
for i in range(1, 7):
    if i != 4:
        # print(f"Shortest path from Node 4 to Node {i}: {dijkstra_shortest_path(graph, START, str(i))}")
        ret_edges = dijkstra_shortest_path(graph, START, str(i))
        console.print(f"- Shortest path from $V_{int(START)}$ to $V_{int(i)}$: " + "{" + ", ".join([f"({edge[0]}, {edge[1]})" for edge in ret_edges[2]]) + "}" + f", Weight: {ret_edges[0]}")
        for edge in ret_edges[2]:
            edges.add((edge[0], edge[1]))

print(f"Edges in shortest paths: " + "{" + ", ".join([f'({edge[0]}, {edge[1]})' for edge in edges]) + "}")


