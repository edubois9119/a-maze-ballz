/**
 * This work is Copyright 2019, Erica DuBois. ALL RIGHTS RESERVED.
 */
package com.ericadubois.amazeballz.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.ericadubois.amazeballz.pojos.Cell;

/**
 * This entity creates the maze table in the database.
 */
@Entity
public class Maze {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "maze_id")
  private long id;

  /**
   * This is the number of rows that make up the grid size of the maze.
   */
  @ColumnInfo(name = "grid_rows")
  private int gridRows;

  /**
   * This is the number of columns that make up the grid size of the maze.
   */
  @ColumnInfo(name = "grid_columns")
  private int gridColumns;

  /**
   * The walls of a maze are constructed using hexcode strings. walls.length() is equal to the
   * number of boxes of the grid. For example, 5 rows X 5 columns = 25 boxes= 25 hexcodes in walls.
   */
  @ColumnInfo(name = "walls")
  private Cell[][] walls;

  /**
   * The larger the maze size, the harder the maze. For example, a maze with 5 columns and 5 rows is
   * more difficult than a maze with 3 columns and 3 rows.
   */
  @ColumnInfo(index = true)
  private int level;

  /**
   * Designates the row/column that is the beginning of the maze.
   */
  @Embedded(prefix = "entrance_")
  private Point entrance;

  /**
   * Designates the row/column that is the end of the maze.
   */
  @Embedded(prefix = "exit_")
  private Point exit;

  /**
   * Gets maze id.
   *
   * @return the id
   */
  public long getId() {
    return id;
  }

  /**
   * Sets maze id.
   *
   * @param id the id
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Gets grid rows.
   *
   * @return the grid rows
   */
  public int getGridRows() {
    return gridRows;
  }

  /**
   * Sets grid rows.
   *
   * @param gridRows the grid rows
   */
  public void setGridRows(int gridRows) {
    this.gridRows = gridRows;
  }

  /**
   * Gets grid columns.
   *
   * @return the grid columns
   */
  public int getGridColumns() {
    return gridColumns;
  }

  /**
   * Sets grid columns.
   *
   * @param gridColumns the grid columns
   */
  public void setGridColumns(int gridColumns) {
    this.gridColumns = gridColumns;
  }

  /**
   * Gets walls.
   *
   * @return the walls
   */
  public Cell[][] getWalls() {
    return walls;
  }

  /**
   * Sets walls.
   *
   * @param walls the walls
   */
  public void setWalls(Cell[][] walls) {
    this.walls = walls;
  }

  /**
   * Gets difficulty.
   *
   * @return the difficulty
   */
  public int getLevel() {
    return level;
  }

  /**
   * Sets difficulty.
   *
   * @param level the difficulty
   */
  public void setLevel(int level) {
    this.level = level;
  }

  /**
   * Gets entrance.
   *
   * @return the entrance
   */
  public Point getEntrance() {
    return entrance;
  }

  /**
   * Sets entrance.
   *
   * @param entrance the entrance
   */
  public void setEntrance(Point entrance) {
    this.entrance = entrance;
  }

  /**
   * Gets exit.
   *
   * @return the exit
   */
  public Point getExit() {
    return exit;
  }

  /**
   * Sets exit.
   *
   * @param exit the exit
   */
  public void setExit(Point exit) {
    this.exit = exit;
  }

  /**
   * The type Point.
   */
  public static class Point {

    private int x;
    private int y;

    /**
     * Gets x.
     *
     * @return the x
     */
    public int getX() {
      return x;
    }

    /**
     * Sets x.
     *
     * @param x the x
     */
    public void setX(int x) {
      this.x = x;
    }

    /**
     * Gets y.
     *
     * @return the y
     */
    public int getY() {
      return y;
    }

    /**
     * Sets y.
     *
     * @param y the y
     */
    public void setY(int y) {
      this.y = y;
    }


  }


}
