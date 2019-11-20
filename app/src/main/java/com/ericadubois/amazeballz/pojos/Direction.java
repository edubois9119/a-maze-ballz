/**
 * This work is Copyright 2019, Erica DuBois. ALL RIGHTS RESERVED.
 */

package com.ericadubois.amazeballz.pojos;

/**
 * The enum Direction. This used for building cell walls and creating mazes. It is also used in
 * navigating the ball through the maze.
 */
public enum Direction {
  /**
   * North direction.
   */
  NORTH(-1, 0),
  /**
   * East direction.
   */
  EAST(0, 1),
  /**
   * South direction.
   */
  SOUTH(1, 0),
  /**
   * West direction.
   */
  WEST(0, -1);

  /**
   * The Row offset for each direction.
   */
  public final int rowOffset;
  /**
   * The Column offset for each direction.
   */
  public final int columnOffset;


  Direction(int rowOffset, int columnOffset) {
    this.rowOffset = rowOffset;
    this.columnOffset = columnOffset;
  }

  /**
   * Gets row offset for any given direction
   *
   * @return the row offset for any given direction
   */
  public int getRowOffset() {
    return rowOffset;
  }

  /**
   * Gets column offset for any given direction.
   *
   * @return the column offset for any given direction
   */
  public int getColumnOffset() {
    return columnOffset;
  }

  /**
   * Gets opposite direction.
   *
   * @return the opposite direction.
   */
  public Direction getOpposite() {
    return Direction.values()[(ordinal() + 2) % Direction.values().length];
  }
}
