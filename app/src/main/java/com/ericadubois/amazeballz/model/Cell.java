package com.ericadubois.amazeballz.model;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * The type Cell.
 */
public class Cell {

  private final int row;
  private final int column;
  private boolean visited;
  //private int [] neighbors;
  //private Direction direction;
  //private Cell destination;
  private List<Direction> walls;
  private final boolean UNVISITED_ONLY=true;
  // private Cell[][] cells;

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
   * Is visited boolean.
   *
   * @return the boolean
   */
  public boolean isVisited(){
    return this.visited;
  }

  /**
   * Sets visited.
   *
   * @param visited the visited
   */
  public void setVisited(boolean visited) {
    this.visited = visited;
  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }

  public List<Direction> getWalls() {
    return walls;
  }

  /**
   * Remove wall.
   *
   * @param direction the direction
   */
  public void removeWall(Direction direction) {
    this.walls.remove(direction);
  }


  /**
   * Neighbor cell.
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
        adjacentColumn>= 0 && adjacentColumn < cells[adjacentRow].length) {
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
   * Neighbors array list.
   *
   * @param cells         the cells
   * @param unvisitedOnly the unvisited only
   * @return the array list of unvisited neighbors.
   */
  ArrayList<Route> neighbors(Cell[][] cells, boolean unvisitedOnly) {
    ArrayList<Route> neighborsList= new ArrayList<Route>();
    //loop over each direction in walls and check to see if that neighbor is unvisited
    for(Direction direction : walls){
      Cell newNeighbor = neighbor(cells, direction, unvisitedOnly);
      //if neighbor is !null, add to neighbors
      if(newNeighbor != null){
        neighborsList.add(new Route(direction, newNeighbor));
      }
    }
    return neighborsList;
  }


  /**
   * Build.
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

  @NonNull
  @Override
  public String toString() {
    return "Cell [" + row + "][" + column +"]: has " + walls;
  }

//  public String cellAsHex(){
//    walls.sort();
    //0 = no walls
    //1 = west wall
    //2 = south wall
    //3 = west and south walls
    //4 = east wall
    //5 = east and west walls
    //6 = south and east walls
    //7 = west, south and east walls
    //8 = north wall
    //9 = north and west walls
    //A = north and south walls
    //B = north, west and south walls
    //C = north and east walls
    //D = west, north, and east walls
    //E = north, east, and south walls
    //F = all walls
//    switch (walls.size()){
//      case 0:
//        return "0";
//      case 1:
//        switch (walls.get(0)){
// //         case Direction.EAST:
//
//      case 2:
//
//      case 3:
//
//      default:
//        return "F";
//    }
//  }
//
  }
