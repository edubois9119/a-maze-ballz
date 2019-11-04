package com.ericadubois.amazeballz.model;

import com.ericadubois.amazeballz.model.entity.Maze;

public class MazeBuilder {

  public Cell [][] cells;
  int rows = 3;
  int columns = 3;
  final int START_ROW = 0;
  final int START_COL = 0;

  public MazeBuilder(){
    setupMaze();
  }

  public static void main(String[] args) {
    MazeBuilder mazeBuilder = new MazeBuilder();
    mazeBuilder.printMaze();
  }

  public void setupMaze(){
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
  }
  public void printMaze() {
    //need to instantiate each Cell inside
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < columns; col++) {
        System.out.println(cells[row][col]);
      }
    }
  }
}
