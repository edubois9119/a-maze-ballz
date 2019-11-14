package com.ericadubois.amazeballz.model;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import com.ericadubois.amazeballz.model.entity.Maze;

public class MazeBuilder {

  public Cell [][] cells;
  final int START_ROW = 0;
  final int START_COL = 0;

  public MazeBuilder(int rows, int columns){
    setupMaze(rows, columns);
  }

  public Cell[][] getCells() {
    return cells;
  }

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
//  public void printMaze() {
//    //need to instantiate each Cell inside
//    for (int row = 0; row < rows; row++) {
//      for (int col = 0; col < columns; col++) {
//        System.out.println(cells[row][col]);
//      }
//    }
//  }
}
