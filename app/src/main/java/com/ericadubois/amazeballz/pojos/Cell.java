/**
 * This work is Copyright 2019, Erica DuBois. ALL RIGHTS RESERVED.
 */

package com.ericadubois.amazeballz.pojos;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * The primary element of a maze. Cell has walls.
 */
public class Cell {

  private final int row;
  private final int column;
  private boolean visited;
  private List<Direction> walls;
  private final boolean UNVISITED_ONLY = true;


  /**
   * Instantiates a new Cell.
   *
   * @param row    the row
   * @param column the column
   */
  public Cell(int row, int column) {
    this.row = row;
    this.column = column;
    this.visited = false;
    this.walls = new ArrayList<Direction>(Arrays.asList(Direction.values()));
  }

  /**
   * Flag that specifies if a cell has been visited.
   *
   * @return the boolean; true if cell has been visited, otherwise false.
   */
  public boolean isVisited() {
    return this.visited;
  }

  /**
   * Sets if a cell has been visited.
   *
   * @param visited the visited cell.
   */
  public void setVisited(boolean visited) {
    this.visited = visited;
  }

  /**
   * Gets row in maze.
   *
   * @return the row
   */
  public int getRow() {
    return row;
  }

  /**
   * Gets column in maze.
   *
   * @return the column
   */
  public int getColumn() {
    return column;
  }

  /**
   * Gets walls of cell.
   *
   * @return the walls of the cell
   */
  public List<Direction> getWalls() {
    return walls;
  }

  /**
   * Sets walls of cell.
   *
   * @param directions the directions
   */
  public void setWalls(List<Direction> directions) {
    this.walls = directions;
  }

  /**
   * Remove wall of cell.
   *
   * @param direction the direction
   */
  public void removeWall(Direction direction) {
    this.walls.remove(direction);
  }

  /**
   * Finds the unvisited adjacent cells.
   *
   * @param cells         the cells
   * @param direction     the direction
   * @param unvisitedOnly the unvisited only
   * @return the unvisited, adjacent cell. Return is null otherwise.
   */
  public Cell neighbor(Cell[][] cells, Direction direction, boolean unvisitedOnly) {
    Cell adjacent;
    int adjacentRow = this.row + direction.rowOffset;
    int adjacentColumn = this.column + direction.columnOffset;
    if (adjacentRow >= 0 && adjacentRow < cells.length &&
        adjacentColumn >= 0 && adjacentColumn < cells[adjacentRow].length) {
      adjacent = cells[adjacentRow][adjacentColumn];
      if (unvisitedOnly && adjacent.isVisited()) {
        adjacent = null;
      }
    } else {
      adjacent = null;
    }
    return adjacent;
  }

  /**
   * Array list of unvisited neighbors.
   *
   * @param cells         the cells
   * @param unvisitedOnly the unvisited only
   * @return the array list of unvisited neighbors.
   */
  ArrayList<Route> neighbors(Cell[][] cells, boolean unvisitedOnly) {
    ArrayList<Route> neighborsList = new ArrayList<Route>();
    //loop over each direction in walls and check to see if that neighbor is unvisited
    for (Direction direction : walls) {
      Cell newNeighbor = neighbor(cells, direction, unvisitedOnly);
      //if neighbor is !null, add to neighbors
      if (newNeighbor != null) {
        neighborsList.add(new Route(direction, newNeighbor));
      }
    }
    return neighborsList;
  }

  /**
   * Builds the maze from the given array of cells.
   *
   * @param cells the cells
   */
  public void build(Cell[][] cells) {
    // 1. Mark this Cell instance as visited.
    this.visited = true;
    //reachable = arrayList of reachable neighbors
    ArrayList<Route> reachable;
    // While there are any unvisited neighbors of this instance, repeat the following:
    for (reachable = this.neighbors(cells, UNVISITED_ONLY);
        !reachable.isEmpty();
        reachable = this.neighbors(cells, UNVISITED_ONLY)) {
      //   a. Select one unvisited neighbor at random.
      final int selection = (int) Math.floor(Math.random() * reachable.size());
      final Direction direction = reachable.get(selection).direction;
      final Cell destination = reachable.get(selection).destination;
      //   b. Remove the wall between this cell and the
      //      selected neighbor. (Note that this requires
      //      removal of 2 walls in total - why?)
      this.removeWall(direction);
      destination.removeWall(direction.getOpposite());
      //   c. Recursively invoke build(cells) on the selected
      //      neighbor.
      destination.build(cells);
    }
  }

  /**
   * Wall string decoder.
   *
   * @return the char
   */
  public char value() {
    int value = 0;
    for (Direction direction : getWalls()) {
      value += 1 << direction.ordinal();
    }
    return Integer.toHexString(value).charAt(0);
  }

  @NonNull
  @Override
  public String toString() {
    return "Cell [" + row + "][" + column + "]: has " + walls;
  }
}

