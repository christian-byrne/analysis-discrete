import heapq
import matplotlib.pyplot as plt
import networkx as nx

class Node:
    def __init__(self, symbol, frequency):
        self.symbol = symbol
        self.frequency = frequency
        self.left = None
        self.right = None

    # Define the comparison operators for the priority queue
    def __lt__(self, other):
        return self.frequency < other.frequency

def huffman_tree(symbols_with_freqs):
    heap = [Node(symbol, freq) for symbol, freq in symbols_with_freqs.items()]
    heapq.heapify(heap)
    
    while len(heap) > 1:
        node1 = heapq.heappop(heap)
        node2 = heapq.heappop(heap)
        
        merged = Node(None, node1.frequency + node2.frequency)
        merged.left = node1
        merged.right = node2
        
        heapq.heappush(heap, merged)
    
    return heap[0]

def generate_codes(node, prefix="", codebook={}):
    if node:
        if node.symbol is not None:
            codebook[node.symbol] = prefix
        generate_codes(node.left, prefix + "0", codebook)
        generate_codes(node.right, prefix + "1", codebook)
    return codebook

def calculate_average_bits(symbols_with_freqs, codebook):
    total_bits = 0
    for symbol, freq in symbols_with_freqs.items():
        total_bits += freq * len(codebook[symbol])
    return total_bits

def plot_huffman_tree(root):
    def add_edges(node, parent=None, pos=None, x=0, y=0, layer=1):
        if pos is None:
            pos = {}
        pos[node] = (x, y)
        if parent:
            graph.add_edge(parent, node)
        if node.left:
            pos = add_edges(node.left, node, pos=pos, x=x-2**layer, y=y-1, layer=layer-1)
        if node.right:
            pos = add_edges(node.right, node, pos=pos, x=x+2**layer, y=y-1, layer=layer-1)
        return pos

    graph = nx.DiGraph()
    pos = add_edges(root)
    
    # Use the coded symbols as the label
    labels = {node: node.symbol if node.symbol else '' for node in graph.nodes()}
    # labels = {node: codebook[node.symbol] if node.symbol else '' for node in graph.nodes()}
    
    nx.draw(graph, pos, labels=labels, with_labels=True, node_size=2000, node_color="lightblue", font_size=10, font_weight="bold")

    # Save the plot to a file
    plt.savefig("huffman_tree.png")
    plt.show()

# Given symbols and their frequencies
symbols_with_freqs = {'A': 0.10, 'B': 0.25, 'C': 0.05, 'D': 0.15, 'E': 0.30, 'F': 0.07, 'G': 0.08}

# Step 1: Build Huffman Tree
huffman_root = huffman_tree(symbols_with_freqs)

# Step 2: Generate Huffman Codes
huffman_codes = generate_codes(huffman_root)


# Step 3: Calculate average number of bits required to encode a symbol
average_bits = calculate_average_bits(symbols_with_freqs, huffman_codes)

# Print the Huffman Codes and average bits
print("Huffman Codes:", huffman_codes)
print("Average number of bits required to encode a symbol:", average_bits)

# Step 4: Plot the Huffman Tree
plot_huffman_tree(huffman_root)

