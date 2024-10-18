/*
 * Author: Christian Byrne
 * Course: CSc 345 — Analysis of Discrete Structures
 * Assignment: Program #2: Bones Battle
 * Instructor: McCann
 * TAs: Rubin Yang, Lucas Almeida, Hamad Ayaz, Sohan Bhakta, CJ Chen, Hyungji Kim, Hamlet Taraz
 * Due Date: September 19th, 2024
 *
 * A representation of the game board (a rectangular board of territories).
 * Some territories on the board are unplayable ("holes").
 * 
 * The Map class holds the state of the game board as a 2D array of Territory
 * instances. It also maintains a graph that captures neighbor relationships
 * between territories, and a list of the players participating in the game.
 * 
 * The Map class also handles tasks like partitioning the territories among
 * players and distributing dice among the territories.
 *
 * Language/Version: Java 6
 * Compilation: No special compilation details required.
 * Input: This is a dependency of the game engine and does not have a main method.
 *
 * Known Bugs: None.
 * Features Not Implemented: None.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.Random;
import java.util.Stack;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Map class
 * A representation of the game board (a rectangular board of territories).
 * Some territories on the board are unplayable ("holes").
 * 
 * The Map class holds the state of the game board as a 2D array of Territory
 * instances. It also maintains a graph that captures neighbor relationships
 * between territories, and a list of the players participating in the game.
 * 
 * @properties ROWS, COLUMNS, NUMTERRITORIES, OCCUPIED, MAXDICE, map, graph,
 *             players
 * @methods getMap, getGraph, getTerritory, getTerritoryID, countTerritories,
 *          getTerritories, countDice, getNeighbors, getEnemyNeighbors,
 *          getPropertyOf,
 *          partitionTerritories, distributeDice, countConnected, idToRowColumn
 * @dependencies Territory, Graph, Player
 * @author Christian Byrne
 * @course CSc 345 — Analysis of Discrete Structures
 * @assignment Program #2: Bones Battle Program #2
 * @instructor Dr. McCann, TAs Rubin Yang, Lucas Almeida, Hamad Ayaz, Sohan
 *             Bhakta, CJ Chen, Hyungji Kim, Hamlet Taraz
 * @date October 17, 2024
 */
public class Map {

  /** Number of rows in the game board. */
  final int ROWS;

  /** Number of columns in the game board. */
  final int COLUMNS;

  /** Total number of territories on the game board (ROWS * COLUMNS). */
  final int NUMTERRITORIES;

  /** Number of playable territories on the game board. */
  final int OCCUPIED;

  /** Maximum number of dice allowed on a single territory. */
  final int MAXDICE;

  /** 2D array representing the game board's territories. */
  Territory[][] map;

  /** Graph representation of the territories and their neighbor relationships. */
  Graph graph;

  /** List of players in the game. */
  ArrayList<Player> players;

  /** Random number generator for the Map class. */
  private static Random rand = new Random();

  /**
   * Constructor for the Map class.
   * 
   * Initializes the game board (2D array of Territory instances) and assigns
   * territories to players. Also initializes the territory neighbor graph and
   * distributes dice to the players' territories.
   * 
   * @param players List of players participating in the game.
   * @param rows    Number of rows in the game board.
   * @param columns Number of columns in the game board.
   * @param victims Number of unplayable ("hole") territories.
   * @param maxDice Maximum number of dice a territory can hold.
   */
  public Map(ArrayList<Player> players, int rows, int columns, int victims, int maxDice) {
    this.ROWS = rows;
    this.COLUMNS = columns;
    this.NUMTERRITORIES = ROWS * COLUMNS; // Total number of territories on the board
    this.OCCUPIED = NUMTERRITORIES - victims; // Number of territories that are playable
    this.MAXDICE = maxDice; // Maximum number of dice per territory
    this.players = players; // Players list provided during initialization

    // Initialize the map with unowned territories
    this.map = new Territory[ROWS][COLUMNS];
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLUMNS; j++) {
        map[i][j] = new Territory(this); // Assign new Territory objects to each position in the grid
      }
    }

    // Construct the territory neighbor graph
    this.graph = constructGraph(ROWS, COLUMNS, victims);

    // Assign territories to players
    partitionTerritories();

    // Distribute dice across territories
    distributeDice();
  }

  /**
   * Returns the game board map (2D array of Territory instances).
   * 
   * @return the map of the game board
   */
  public Territory[][] getMap() {
    return map;
  }

  /**
   * Returns the graph of the game board (neighbor relationships between
   * territories).
   * 
   * @return the graph of the game board
   */
  public Graph getGraph() {
    return graph;
  }

  /**
   * Returns the territory at the given row and column.
   * 
   * @param row    Row index of the territory.
   * @param column Column index of the territory.
   * @return the territory at the given row and column
   */
  public Territory getTerritory(int row, int column) {
    return map[row][column];
  }

  /**
   * Returns the territory corresponding to the given ID.
   * 
   * @param id ID of the territory.
   * @return the territory with the given ID
   */
  public Territory getTerritory(int id) {
    int[] rowColumn = idToRowColumn(id); // Convert ID to row and column
    return map[rowColumn[0]][rowColumn[1]]; // Return the territory at the computed row and column
  }

  /**
   * Returns the ID of the territory at the given row and column.
   * 
   * @param row    Row index of the territory.
   * @param column Column index of the territory.
   * @return the ID of the territory at the given row and column
   */
  public int getTerritoryID(int row, int column) {
    return row * COLUMNS + column; // Compute ID using row-major order
  }

  /**
   * Counts and returns the number of territories owned by the given player.
   * 
   * @param player Player whose territories are being counted.
   * @return the number of territories owned by the given player
   */
  public int countTerritories(Player player) {
    int count = 0;
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLUMNS; j++) {
        if (map[i][j].getOwner() == player) { // If player owns this territory
          count++;
        }
      }
    }
    return count;
  }

  /**
   * Counts and returns the number of dice owned by the given player across all
   * of their territories.
   * 
   * @param player Player whose dice are being counted.
   * @return the number of dice owned by the given player
   */
  public int countDice(Player player) {
    int count = 0;
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLUMNS; j++) {
        if (map[i][j].getOwner() == player) { // If player owns this territory
          count += map[i][j].getDice(); // Add dice count
        }
      }
    }
    return count;
  }

  /**
   * Returns a list of territories adjacent to the provided territory.
   * 
   * @param cell The territory whose neighbors are being returned.
   * @return a list of territories adjacent to the provided territory
   */
  public ArrayList<Territory> getNeighbors(Territory cell) {
    if (cell == null || cell.getIdNum() < 0 || cell.getIdNum() >= NUMTERRITORIES) {
      return new ArrayList<>(); // Return an empty list for invalid territories
    }
    return this.graph.getAdjacent(cell.getIdNum()).stream()
        .map(this::getTerritory) // Map adjacent territory IDs to their corresponding Territory objects
        .collect(Collectors.toCollection(ArrayList::new));
  }

  /**
   * Returns a list of territories adjacent to the provided territory that are
   * owned
   * by a different player.
   * 
   * @param cell The territory whose enemy neighbors are being returned.
   * @return a list of enemy territories adjacent to the provided territory
   */
  public ArrayList<Territory> getEnemyNeighbors(Territory cell) {
    return getNeighbors(cell).stream()
        .filter(neighbor -> neighbor.getOwner() != cell.getOwner()) // Filter neighbors owned by other players
        .collect(Collectors.toCollection(ArrayList::new));
  }

  /**
   * Returns a list of the territories owned by the given player.
   * 
   * @param player Player whose territories are being returned.
   * @return a list of territories owned by the given player
   */
  public ArrayList<Territory> getPropertyOf(Player player) {
    ArrayList<Territory> property = new ArrayList<>();
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLUMNS; j++) {
        if (map[i][j].getOwner() == player) { // If player owns this territory
          property.add(map[i][j]);
        }
      }
    }
    return property; // Return the list of territories owned by the player
  }

  /**
   * Partitions the territories on the board among the players, assigning
   * territories
   * randomly but ensuring that each player receives the same (or nearly the same)
   * number of territories.
   */
  private void partitionTerritories() {
    List<Integer> activeTerritories = shuffleList(graph.getUsedVertices()); // Shuffle the list of active territories

    for (int i = 0; i < activeTerritories.size(); i++) {
      Player player = players.get(i % players.size()); // Cycle through players
      getTerritory(activeTerritories.get(i)).setOwner(player); // Assign territory to the player
    }
  }

  /**
   * Distributes dice to each player's territories, ensuring that the total number
   * of dice each player receives is equal to three times the number of
   * territories
   * they own.
   */
  private void distributeDice() {
    for (Player player : players) {
      ArrayList<Territory> property = getPropertyOf(player); // Get player's territories
      int dice = property.size() * 3; // Total number of dice for this player

      // Assign at least one die to each territory
      for (Territory territory : property) {
        territory.setDice(1);
        dice--;
      }

      // Remove territories that already have the maximum number of dice
      property.removeIf(territory -> territory.getDice() == MAXDICE);

      // Distribute remaining dice randomly across the player's territories
      while (dice > 0 && !isPlayerMaxPossibleDice(player)) {
        int index = rand.nextInt(property.size()); // Pick a random territory
        Territory territory = property.get(index);
        if (territory.getDice() < MAXDICE) {
          territory.setDice(territory.getDice() + 1); // Add one die to the territory
          dice--;
        } else {
          property.remove(index); // Remove territory if it has reached the maximum dice count
        }
      }
    }
  }

  /**
   * Counts and returns the number of territories in the largest connected cluster
   * of territories owned by the given player.
   * 
   * @param player The player whose territories are being counted.
   * @return the number of territories in the largest connected cluster of
   *         territories
   */
  public int countConnected(Player player) {
    int max = 0;
    ArrayList<Territory> property = getPropertyOf(player); // Get list of territories owned by the player
    HashSet<Territory> visited = new HashSet<>(); // Set to track visited territories

    for (Territory territory : property) {
      if (visited.contains(territory)) {
        continue; // Skip already visited territories
      }
      int count = countConnected(territory, player, visited); // Count connected territories using DFS
      if (count > max) {
        max = count; // Update maximum count
      }
    }
    return max;
  }

  /**
   * Helper method for counting connected territories.
   * Uses Depth-First Search (DFS) to count the number of territories owned by
   * the given player that are connected to the given starting territory.
   * 
   * @param territory The starting territory.
   * @param player    The player whose territories are being counted.
   * @param visited   A set of already visited territories.
   * @return the number of connected territories owned by the player
   */
  private int countConnected(Territory territory, Player player, HashSet<Territory> visited) {
    Stack<Territory> stack = new Stack<>();
    stack.push(territory); // Start DFS from this territory
    int count = 0;

    while (!stack.isEmpty()) {
      Territory current = stack.pop();
      if (current.getOwner() == player && !visited.contains(current)) {
        visited.add(current); // Mark the territory as visited
        count++;
        for (Territory neighbor : getNeighbors(current)) {
          stack.push(neighbor); // Explore all unvisited neighbors
        }
      }
    }
    return count;
  }

  /**
   * Assigns a territory to the next player in the queue and re-adds the player to
   * the end of the queue.
   * 
   * @param territoryId The ID of the territory to assign.
   * @param players     Queue of players.
   */
  private void assignAndPop(int territoryId, LinkedList<Player> players) {
    Territory territory = getTerritory(territoryId); // Get the territory by ID
    Player player = players.pop(); // Get the next player in the queue
    territory.setOwner(player); // Assign territory to the player
    territory.setIdNum(territoryId); // Set the territory's ID number
    territory.setDice(0); // Set initial dice count to zero
    players.add(player); // Add player back to the queue
  }

  /**
   * Shuffles a list of integers using a random order.
   * 
   * @param list The list of integers to shuffle.
   * @return the shuffled list
   */
  private List<Integer> shuffleList(List<Integer> list) {
    for (int i = 0; i < list.size(); i++) {
      int randomIndex = rand.nextInt(list.size()); // Pick a random index
      int temp = list.get(i); // Swap current element with the randomly picked element
      list.set(i, list.get(randomIndex));
      list.set(randomIndex, temp);
    }
    return list; // Return the shuffled list
  }

  /**
   * Constructs and returns the graph representing the active territories in the
   * game. It randomly assigns inactive territories and ensures that all active
   * territories are connected.
   * 
   * @param row     The number of rows.
   * @param cols    The number of columns.
   * @param victims The number of unplayable territories.
   * @return the graph representing the active territories
   */
  public Graph constructGraph(int row, int cols, int victims) {
    int numVertices = row * cols; // Total number of vertices (territories)
    this.graph = new Graph(numVertices); // Initialize the graph with the given number of vertices

    LinkedList<Player> playersQueue = new LinkedList<>(this.players); // Create a queue of players
    int unassignedActive = numVertices - victims; // Number of active territories left to assign
    int start = rand.nextInt(numVertices); // Pick a random starting point
    assignAndPop(start, playersQueue); // Assign the first territory

    List<Integer> assigned = new ArrayList<>(); // Track assigned territories

    while (unassignedActive > 0) {
      if (!hasUnoccupiedNeighbor(start)) { // If no unoccupied neighbors, pick a new starting point
        assigned.remove(Integer.valueOf(start));
        start = assigned.get(rand.nextInt(assigned.size()));
        continue;
      }

      // Shuffle adjacent territories and pick an unoccupied one
      List<Integer> adjacentIds = shuffleList(getAdjacentTerritoryIds(start));
      int randomAdjacentTerritory = -1;
      for (int adjacentId : adjacentIds) {
        if (getTerritory(adjacentId).getOwner() == null) { // Find the first unoccupied adjacent territory
          randomAdjacentTerritory = adjacentId;
          break;
        }
      }

      assignAndPop(randomAdjacentTerritory, playersQueue); // Assign the new territory to the next player
      for (int adjacentId : getAdjacentTerritoryIds(randomAdjacentTerritory)) {
        if (getTerritory(adjacentId).getOwner() != null) {
          graph.addEdge(randomAdjacentTerritory, adjacentId); // Add edges between neighbors
        }
      }

      unassignedActive--; // Decrease the count of unassigned territories
      assigned.add(randomAdjacentTerritory); // Add the newly assigned territory to the list
      start = assigned.get(rand.nextInt(assigned.size())); // Pick a new starting point
    }

    return graph; // Return the constructed graph
  }

  /**
   * Returns a list of IDs of territories adjacent to the given territory.
   * 
   * @param id The ID of the territory.
   * @return a list of adjacent territory IDs
   */
  private List<Integer> getAdjacentTerritoryIds(int id) {
    int row = id / COLUMNS; // Compute the row based on ID
    int col = id % COLUMNS; // Compute the column based on ID
    ArrayList<Integer> adjacentTerritoryIds = new ArrayList<>();

    if (row > 0) {
      adjacentTerritoryIds.add(id - COLUMNS); // Add the territory above if it exists
    }
    if (row < ROWS - 1) {
      adjacentTerritoryIds.add(id + COLUMNS); // Add the territory below if it exists
    }
    if (col > 0) {
      adjacentTerritoryIds.add(id - 1); // Add the territory to the left if it exists
    }
    if (col < COLUMNS - 1) {
      adjacentTerritoryIds.add(id + 1); // Add the territory to the right if it exists
    }

    return adjacentTerritoryIds; // Return the list of adjacent territory IDs
  }

  /**
   * Checks if the given territory has at least one unoccupied neighbor.
   * 
   * @param id The ID of the territory.
   * @return true if the given territory has an unoccupied neighbor, false
   *         otherwise
   */
  private boolean hasUnoccupiedNeighbor(int id) {
    List<Integer> adjacentTerritoryIds = getAdjacentTerritoryIds(id); // Get IDs of adjacent territories
    for (int adjacentId : adjacentTerritoryIds) {
      if (getTerritory(adjacentId).getOwner() == null) { // Check if any neighbors are unoccupied
        return true;
      }
    }
    return false;
  }

  /**
   * Checks if the player has the maximum possible number of dice across all their
   * territories.
   * 
   * @param player The player whose territories are being checked.
   * @return true if the player has the maximum possible dice, false otherwise
   */
  private boolean isPlayerMaxPossibleDice(Player player) {
    return countTerritories(player) * MAXDICE == countDice(player); // Compare total dice with max possible
  }

  /**
   * Converts a territory ID into a row and column pair.
   * 
   * @param id The ID of the territory.
   * @return an array containing the row and column of the territory
   */
  private int[] idToRowColumn(int id) {
    return new int[] { id / COLUMNS, id % COLUMNS }; // Compute row and column from ID
  }
}
