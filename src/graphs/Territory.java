/*
 * Author: Christian Byrne
 * Course: CSc 345 — Analysis of Discrete Structures
 * Assignment: Program #2: Bones Battle
 * Instructor: McCann
 * TAs: Rubin Yang, Lucas Almeida, Hamad Ayaz, Sohan Bhakta, CJ Chen, Hyungji Kim, Hamlet Taraz
 * Due Date: September 19th, 2024
 *
 * This is the Territory class, which represents a territory on the game board for the Bones 
 * Battle game.
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

/**
 * Represents a territory on the game board. A territory is identified by its
 * ID,
 * the number of dice currently on it, and the player who owns it.
 * Territories are organized in row-major order on the map.
 * Each territory can perform actions like retrieving its row, column, dice
 * count, etc.
 * 
 * @properties id, currentDiceCount, owner, map
 * @methods getCol, getRow, getDice, getIdNum, getMap, getOwner, setDice,
 *          setIdNum, setOwner
 * @dependencies Map, Player
 * @author Christian Byrne
 * @course CSc 345 — Analysis of Discrete Structures
 * @assignment Program #2: Bones Battle Program #2
 * @instructor Dr. McCann, TAs Rubin Yang, Lucas Almeida, Hamad Ayaz, Sohan
 *             Bhakta, CJ Chen, Hyungji Kim, Hamlet Taraz
 * @date October 17, 2024
 */
public class Territory {

  /** The unique ID of this territory, based on row-major order. */
  private int id;

  /** The current number of dice on this territory. */
  private int currentDiceCount;

  /** The player who currently owns this territory. */
  private Player owner;

  /** The map that this territory belongs to. */
  private final Map map;

  /**
   * Constructor that initializes a territory with only its map.
   * 
   * @param map The map to which this territory belongs.
   */
  public Territory(Map map) {
    this.map = map;
    this.owner = null; // Territory has no owner initially
    this.id = -1; // Unassigned ID
    this.currentDiceCount = -1; // No dice initially assigned
  }

  /**
   * Constructor that initializes a territory with all attributes.
   * 
   * @param map   The map to which this territory belongs.
   * @param owner The player who owns this territory.
   * @param dice  The initial number of dice on this territory.
   * @param idNum The ID of this territory.
   */
  public Territory(Map map, Player owner, int dice, int idNum) {
    this.map = map;
    this.owner = owner;
    this.currentDiceCount = dice;
    this.id = idNum;
  }

  /**
   * Retrieves the column number of this territory based on its ID.
   * 
   * @return The column number of the territory.
   */
  public int getCol() {
    return this.id % map.COLUMNS;
  }

  /**
   * Retrieves the row number of this territory based on its ID.
   * 
   * @return The row number of the territory.
   */
  public int getRow() {
    return this.id / map.COLUMNS;
  }

  /**
   * Retrieves the number of dice currently on this territory.
   * 
   * @return The number of dice on this territory.
   */
  public int getDice() {
    return currentDiceCount;
  }

  /**
   * Retrieves the ID number of this territory.
   * 
   * @return The ID number of this territory.
   */
  public int getIdNum() {
    return id;
  }

  /**
   * Retrieves the map that this territory is on.
   * 
   * @return The map that this territory is on.
   */
  public Map getMap() {
    return map;
  }

  /**
   * Retrieves the player who owns this territory.
   * 
   * @return The owner of this territory.
   */
  public Player getOwner() {
    return owner;
  }

  /**
   * Sets the number of dice on this territory.
   * 
   * @param dice The new number of dice to set on this territory.
   */
  public void setDice(int dice) {
    currentDiceCount = dice;
  }

  /**
   * Sets the ID number of this territory.
   * 
   * @param idNum The new ID number to set.
   */
  public void setIdNum(int idNum) {
    this.id = idNum;
  }

  /**
   * Sets the owner of this territory.
   * 
   * @param owner The new owner of the territory.
   */
  public void setOwner(Player owner) {
    this.owner = owner;
  }
}
