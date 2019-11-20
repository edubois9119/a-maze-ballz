/**
 * This work is Copyright 2019, Erica DuBois. ALL RIGHTS RESERVED.
 */

package com.ericadubois.amazeballz.pojos;

import android.util.Log;

/**
 * This class is responsible for building the Mazes.
 */
public class MazeBuilder {

  private Cell [][] cells;
  private final int START_ROW = 0;
  private final int START_COL = 0;

  public MazeBuilder(int rows, int columns){
    setupMaze(rows, columns);
  }
  public Cell[][] getCells() {
    return cells;
  }

  /**
   * Sets up maze using the number of rows and columns provided.
   *
   * @param rows    the rows
   * @param columns the columns
   */
  public void setupMaze(int rows, int columns){
    //sets up empty maze
    cells = new Cell[rows][columns];
    //need to instantiate each Cell inside
    for (int row = 0; row < rows; row++){
      for (int col = 0; col < columns; col++){
        cells[row][col] = new Cell(row, col);
      }
    }
    //builds a maze from cell START ROW COL
    cells[START_ROW][START_COL].build(cells);
    StringBuilder builder= new StringBuilder(rows * columns);
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < columns; col++) {
        builder.append(cells[row][col].value());
      }
    }
    Log.d(getClass().getSimpleName(), builder.toString());
  }

}
