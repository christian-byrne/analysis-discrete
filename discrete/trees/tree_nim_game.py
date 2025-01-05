import itertools
import networkx as nx
import matplotlib.pyplot as plt

def nim_sum(position):
    return position[0] ^ position[1] ^ position[2]

def generate_moves(position):
    moves = []
    for i in range(len(position)):
        for x in range(1, position[i] + 1):
            new_position = list(position)
            new_position[i] -= x
            moves.append(tuple(sorted(new_position)))
    return moves

def evaluate_position(position, memo):
    if position in memo:
        return memo[position]

    if all(x == 0 for x in position):
        memo[position] = 'L'
        return 'L'

    for move in generate_moves(position):
        if evaluate_position(move, memo) == 'L':
            memo[position] = 'W'
            return 'W'
    
    memo[position] = 'L'
    return 'L'

def build_game_tree(start_position):
    tree = nx.DiGraph()
    memo = {}
    evaluate_position(start_position, memo)

    def add_edges(position):
        for move in generate_moves(position):
            if move not in tree:
                add_edges(move)
            tree.add_edge(position, move)

    add_edges(start_position)
    tree.add_node(start_position)
    return tree, memo

def plot_game_tree(tree, memo, start_position):
    pos = nx.spring_layout(tree)
    labels = {node: f"{node}\n{memo[node]}" for node in tree.nodes()}
    color_map = ['lightgreen' if memo[node] == 'W' else 'lightcoral' for node in tree.nodes()]
    
    # Highlight the starting position
    color_map[list(tree.nodes()).index(start_position)] = 'yellow'

    plt.figure(figsize=(12, 8))
    nx.draw(tree, pos, labels=labels, with_labels=True, node_color=color_map, node_size=2000, font_size=10, font_weight='bold')
    plt.title("Nim Game Tree")
    # Save the figure to a file
    plt.savefig("nim_game_tree.png")
    plt.show()

# Define the starting position
start_position = (1, 2, 3)

# Build the game tree and evaluate each position
game_tree, memo = build_game_tree(start_position)

# Plot the game tree
plot_game_tree(game_tree, memo, start_position)

# Determine the winner if both players play optimally
starting_position_value = memo[start_position]
winner = "First player" if starting_position_value == 'W' else "Second player"
print(f"The winner is: {winner}")


