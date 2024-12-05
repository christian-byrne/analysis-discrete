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

from collections import deque
from rich.console import Console
from rich.table import Table
import graphviz
import numpy as np

console = Console()
class Graph:
    def __init__(self, nodes):
        """Initializes the directed graph with a dictionary of nodes, whose names/identifiers can be any type."""
        self.nodes = nodes
        self.edges = {}
        self.size = len(nodes)
        self.adjacency_matrix = np.zeros((self.size, self.size))

    def add_edge(self, node1, node2):
        """Adds an edge between two nodes."""
        if node1 not in self.edges:
            self.edges[node1] = []
        self.edges[node1].append(node2)

    def get_source_vertices(self):
        """Returns a list of source vertices."""
        source_vertices = []
        for node in self.nodes:
            has_no_incoming_edges = True
            for node2 in self.edges:
                if node in self.edges[node2]:
                    has_no_incoming_edges = False
                    break
            if has_no_incoming_edges:
                source_vertices.append(node)
        return source_vertices

    def visualize(self, save=True):
        """Visualizes the graph using Graphviz."""
        dot = graphviz.Digraph()
        for node in self.nodes:
            dot.node(node)
        for node1 in self.edges:
            for node2 in self.edges[node1]:
                dot.edge(node1, node2)

        # save in same location as script
        if save:
            import os
            path = os.path.join(os.path.dirname(__file__), "graph.gv")
            dot.render(path, view=True)
        else:
            dot.render("graph.gv", view=True)


# Add nodes
nodes = ["PGA", "IGM", "FLG", "YUM", "PHX", "SAD", "TUS", "FHU"]
graph = Graph(nodes)

# Add edges
graph.add_edge("FLG", "PGA")
graph.add_edge("FLG", "IGM")
graph.add_edge("PHX", "FLG")
graph.add_edge("PHX", "YUM")
graph.add_edge("PHX", "SAD")
graph.add_edge("TUS", "PHX")
graph.add_edge("TUS", "FHU")

def topological_sort_dfs(graph):
    """Performs a topological sort of the graph using depth-first search (DFS)."""
    visited = set()
    stack = deque()


    def dfs(node, current_recursive_lvl):
        """Recursive DFS helper function that visits nodes and adds them to the topological sort."""
        visited.add(node)

        if node in graph.edges:
            for neighbor in reversed(graph.edges[node]):
                if neighbor not in visited:
                    print(f"{'——' * current_recursive_lvl}Visiting neighbor: {neighbor}")
                    dfs(neighbor, current_recursive_lvl + 1)

        print(f"{'——' * current_recursive_lvl}Adding to stack: {node}")
        stack.appendleft(node)
        # Print current state after visiting the node
        # console.print(f"- Current Stack: $\\langle$ " + ", ".join(stack) + " $\\rangle$")
        # console.print("- Visited Nodes: $\\{{" + ", ".join(visited) + "\\}}$")

    # Start DFS from each source vertex
    for source_vertex in graph.get_source_vertices():
        if source_vertex not in visited:
            console.print(f"\n\nStarting DFS from source vertex: {source_vertex}\n\n")
            dfs(source_vertex, 0)

    return stack

def visualize_topological_sort():
    """Visualizes the topological sort of the graph (in console output)."""
    
    topological_sort = topological_sort_dfs(graph)
    
    DELIMITER = "$\\prec$"
    print(DELIMITER.join(topological_sort) + "\n\n")
    
    table = Table(title="Topological Sort", show_header=True, header_style="bold magenta")
    table.add_column("Index", style="dim", width=12)
    table.add_column("Node", style="dim", width=12)
    for i, node in enumerate(topological_sort):
        table.add_row(str(i), node)
    console.print(table)

# ---------------------------- algorithm analysis ---------------------------- #


# graph.visualize()

console.print(Panel(f"Source vertices: {graph.get_source_vertices()}"))

visualize_topological_sort()