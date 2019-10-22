package com.ericadubois.amazeballz.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;

/**
 * The type Maze.
 */
@Entity
public class Maze {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "maze_id")
  private long id;

  /**
   * This is the number of rows in the maze.
   */
  private int rows;

  /**
   * This is the number of columns in the maze/
   */
  private int columns;

  /**
   * The walls of a maze are constructed using hexcode strings. walls.length() is equal
   * to the number of boxes of the grid. For example, 5 rows X 5 columns = 25 boxes= 25 hexcodes in
   * walls.
   */
  //TODO add in hexcode key (use enum) (16).
  // 0 = no walls, 1= wall on right side etc
  private String walls;

  /**
   * The larger the maze size, the harder the maze. For example, a maze with 5 columns and 5 rows is
   * more difficult than a maze with 3 columns and 3 rows.
   */
  //TODO determine algorithm for difficulty.
  private int difficulty;

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
   * Gets id.
   *
   * @return the id
   */
  public long getId() {
    return id;
  }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(long id) {
    this.id = id;
  }

  public int getRows() {
    return rows;
  }

  public void setRows(int rows) {
    this.rows = rows;
  }

  public int getColumns() {
    return columns;
  }

  public void setColumns(int columns) {
    this.columns = columns;
  }

  /**
   * Gets walls.
   *
   * @return the walls
   */
  public String getWalls() {
    return walls;
  }

  /**
   * Sets walls.
   *
   * @param walls the walls
   */
  public void setWalls(String walls) {
    this.walls = walls;
  }

  /**
   * Gets difficulty.
   *
   * @return the difficulty
   */
  public int getDifficulty() {
    return difficulty;
  }

  /**
   * Sets difficulty.
   *
   * @param difficulty the difficulty
   */
  public void setDifficulty(int difficulty) {
    this.difficulty = difficulty;
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
