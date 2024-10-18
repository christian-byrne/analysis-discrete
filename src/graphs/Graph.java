/* 
 * Author: Christian Byrne
 * Course: CSc 345 — Analysis of Discrete Structures
 * Assignment: Program #2: Bones Battle
 * Instructor: McCann
 * TAs: Rubin Yang, Lucas Almeida, Hamad Ayaz, Sohan Bhakta, CJ Chen, Hyungji Kim, Hamlet Taraz
 * Due Date: October 17, 2024
 * 
 * This file contains the Graph class, which represents the game board as a graph
 * with territories as vertices and neighboring territories as edges. The class provides methods
 * for adding and removing edges, checking for edges, and checking for vertex connectivity.
 * 
 * Known Bugs: None
 * Features Not Implemented: None
 */

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Set;
import java.util.HashSet;
import java.util.Deque;
import java.util.LinkedList;

/**
 * Graph class for the game board.
 * 
 * The Map class relies on an instance of this class to store the neighbor
 * relationships between individual territories. Each territory is represented
 * in the graph by a vertex, and neighboring territories are connected by edges.
 * This class also tracks which vertices are inactive/dead for the current game.
 * 
 * The vertices in the graph are numbered in the same way as the territories in
 * the map using row-major order. The vertex number of a territory is the same
 * as the territory's ID number (row * columns + column).
 * 
 * @properties numVertices, adjacencyMatrix
 * @methods getUnusedVertices, getUsedVertices, isEdge, addEdge, removeEdge,
 *          isInGraph, removeVertex, getAdjacent, degree, connected,
 *          isVertexConnected, getConnected
 * @dependencies None
 * @author Christian Byrne
 * @course CSc 345 — Analysis of Discrete Structures
 * @assignment Program #2: Bones Battle Program #2
 * @instructor Dr. McCann, TAs Rubin Yang, Lucas Almeida, Hamad Ayaz, Sohan
 *             Bhakta, CJ Chen, Hyungji Kim, Hamlet Taraz
 * @date October 17, 2024
 */
public class Graph {

  /** The number of vertices in the graph, representing the territories. */
  private final int numVertices;

  /**
   * The adjacency matrix representation of the graph.
   * Each element adjacencyMatrix[i][j] = 1 indicates an edge between vertex i and
   * j,
   * and adjacencyMatrix[i][j] = 0 indicates no edge.
   */
  private final int[][] adjacencyMatrix;

  /**
   * Constructor for the Graph class.
   * 
   * Initializes the graph for the given number of vertices (territories) and
   * creates an empty adjacency matrix.
   * 
   * @param numVertices the number of vertices in the graph
   */
  Graph(int numVertices) {
    this.numVertices = numVertices;

    // Initialize the adjacency matrix with zeros (no edges initially)
    this.adjacencyMatrix = new int[numVertices][numVertices];
    for (int i = 0; i < numVertices; i++) {
      for (int j = 0; j < numVertices; j++) {
        adjacencyMatrix[i][j] = 0;
      }
    }
  }

  /**
   * Returns a list of the IDs of inactive (dead) vertices in the graph.
   * 
   * @return a list of IDs of inactive vertices
   */
  List<Integer> getUnusedVertices() {
    return IntStream.range(0, numVertices)
        .filter(i -> getAdjacent(i).isEmpty()) // A vertex with no neighbors is inactive
        .boxed()
        .collect(Collectors.toList());
  }

  /**
   * Returns a list of the IDs of active (alive) vertices in the graph.
   * 
   * @return a list of IDs of active vertices
   */
  List<Integer> getUsedVertices() {
    return IntStream.range(0, numVertices)
        .filter(i -> !getAdjacent(i).isEmpty()) // A vertex with neighbors is active
        .boxed()
        .collect(Collectors.toList());
  }

  /**
   * Checks if there is an edge between two vertices in the graph.
   * 
   * @param source      the ID of the source territory
   * @param destination the ID of the destination territory
   * @return true if there is an edge between source and destination, false
   *         otherwise
   */
  boolean isEdge(int source, int destination) {
    return adjacencyMatrix[source][destination] == 1;
  }

  /**
   * Adds an edge between two vertices in the graph.
   * 
   * @param source      the ID of the source territory
   * @param destination the ID of the destination territory
   */
  void addEdge(int source, int destination) {
    adjacencyMatrix[source][destination] = 1;
    adjacencyMatrix[destination][source] = 1; // Ensure symmetry for undirected graph
  }

  /**
   * Removes an edge between two vertices in the graph.
   * 
   * @param source      the ID of the source territory
   * @param destination the ID of the destination territory
   */
  void removeEdge(int source, int destination) {
    adjacencyMatrix[source][destination] = 0;
    adjacencyMatrix[destination][source] = 0; // Ensure symmetry for undirected graph
  }

  /**
   * Checks if a vertex is part of the graph (i.e., has neighbors).
   * 
   * @param vertex the ID of the vertex
   * @return true if the vertex is in the graph, false otherwise
   */
  boolean isInGraph(int vertex) {
    return vertex >= 0 && !getAdjacent(vertex).isEmpty(); // Check for any neighbors
  }

  /**
   * Removes a vertex from the graph, marking it as inactive.
   * Removes all edges connected to the vertex.
   * 
   * @param vertex the ID of the vertex to remove
   */
  void removeVertex(int vertex) {
    for (int i = 0; i < numVertices; i++) {
      removeEdge(vertex, i); // Remove all edges involving this vertex
    }
  }

  /**
   * Returns a list of the IDs of the neighbors of a vertex.
   * 
   * @param vertex the ID of the vertex whose neighbors are being returned
   * @return a list of the IDs of the neighbors
   */
  List<Integer> getAdjacent(int vertex) {
    return IntStream.range(0, numVertices)
        .filter(i -> adjacencyMatrix[vertex][i] == 1) // Collect all neighbors (edges = 1)
        .boxed()
        .collect(Collectors.toList());
  }

  /**
   * Returns the degree of a vertex, which is the number of neighbors it has.
   * 
   * @param vertex the ID of the vertex
   * @return the degree of the vertex
   */
  int degree(int vertex) {
    return getAdjacent(vertex).size();
  }

  /**
   * Checks if the graph is fully connected. A graph is connected if for every
   * pair
   * of vertices (a, b), there exists a path from a to b.
   * 
   * @return true if the graph is connected, false otherwise
   */
  boolean connected() {
    for (int i = 0; i < numVertices; i++) {
      if (!isVertexConnected(i)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks if a vertex is connected to all other vertices in the graph.
   * 
   * @param vertex the ID of the vertex to check
   * @return true if the vertex is connected to all other vertices, false
   *         otherwise
   */
  private boolean isVertexConnected(int vertex) {
    return getConnected(vertex).size() == numVertices; // Check if all vertices are reachable
  }

  /**
   * Returns a set of all vertices connected to the given vertex using
   * Breadth-First Search (BFS).
   * 
   * @param vertex the ID of the vertex
   * @return a set of all vertices connected to the given vertex
   */
  private Set<Integer> getConnected(int vertex) {
    // Set to store visited vertices
    HashSet<Integer> visited = new HashSet<>();

    // Queue for BFS
    Deque<Integer> queue = new LinkedList<>();
    queue.add(vertex); // Start BFS from the given vertex

    // Perform BFS to explore all connected vertices
    while (!queue.isEmpty()) {
      int current = queue.poll(); // Get the next vertex to visit
      visited.add(current); // Mark as visited

      // Add all unvisited neighbors to the queue
      for (int neighbor : getAdjacent(current)) {
        if (!visited.contains(neighbor)) {
          queue.add(neighbor);
        }
      }
    }

    return visited; // Return the set of all connected vertices
  }
}
