import networkx as nx
import matplotlib.pyplot as plt

# Define the two sets of nodes (employees and responsibilities)
employees = ["Zamora", "Agraharam", "Smith", "Chou", "Macintyre"]
responsibilities = ["planning", "publicity", "sales", "marketing", "development", "industry relations"]

# Create the bipartite graph
B = nx.Graph()

# Add nodes to the graph (separating them into two sets)
B.add_nodes_from(employees, bipartite=0)  
B.add_nodes_from(responsibilities, bipartite=1)

# Define the edges (who can do what)
edges = [
    ("Zamora", "planning"), ("Zamora", "sales"), ("Zamora", "marketing"), ("Zamora", "industry relations"),
    ("Agraharam", "planning"), ("Agraharam", "development"),
    ("Smith", "publicity"), ("Smith", "sales"), ("Smith", "industry relations"),
    ("Chou", "planning"), ("Chou", "sales"), ("Chou", "industry relations"),
    ("Macintyre", "planning"), ("Macintyre", "publicity"), ("Macintyre", "sales"), ("Macintyre", "industry relations"),
]
B.add_edges_from(edges)

# Visualize the graph
# pos = nx.bipartite_layout(B, employees)  # Position nodes based on bipartite structure
# nx.draw(B, pos, with_labels=True, node_size=1500, node_color=["skyblue"] * 5 + ["lightcoral"] * 6, font_size=10)
# plt.title("Employee-Responsibility Bipartite Graph")

# plt.show()



# ---------------------------------------------------------------------------- #
#                                   Matching                                   #
# ---------------------------------------------------------------------------- #


# Find a maximum matching
matching = nx.bipartite.maximum_matching(B)

# Filter out the employee-responsibility pairs from the matching
assignment = {employee: responsibility for employee, responsibility in matching.items() if employee in employees}
# unassigned_responsibilities = set(responsibilities) - set(assignment.values())
print("Possible assignment of responsibilities:")
for employee, responsibility in assignment.items():
    print(f"- {employee}: {responsibility}")

# for assignment_ in unassigned_responsibilities:
#     print(f"- Unassigned: {assignment_}")


# ----------------------------- Model the Matchin ---------------------------- #

edges_ = [(employee, responsibility) for employee, responsibility in assignment.items()]
C = nx.Graph()
C.add_nodes_from(employees, bipartite=0)
C.add_nodes_from(responsibilities, bipartite=1)
C.add_edges_from(edges_)



# Visualize the graph
pos = nx.bipartite_layout(C, employees)  # Position nodes based on bipartite structure
nx.draw(C, pos, with_labels=True, node_size=1500, node_color=["skyblue"] * 5 + ["lightcoral"] * 6, font_size=10)
plt.title("Employee-Responsibility Bipartite Graph with Assignment")

plt.show()

# ------------------------ Properties of the Matching ------------------------ #

# A complete matching is one where every node in one of the sets (in this case, the employees) is matched. 

is_complete = len(assignment) == len(employees)
print(f"\nIs the matching complete? {is_complete}")

# The matching we found is a maximum matching because we explicitly used nx.bipartite.maximum_matching. This means there is no other matching in the graph with a larger number of edges.


print(f"Is Maximum Matching: {nx.is_maximal_matching(B, matching)}")