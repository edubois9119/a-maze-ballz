package com.ericadubois.amazeballz.model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

public class SketchView extends View {

  private Cell[][] cells;
  private static final float WALL_THICKNESS = 4;
  private Paint wallPaint;

  public SketchView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);

    wallPaint = new Paint();
    wallPaint.setColor(Color.BLACK);
    wallPaint.setStrokeWidth(WALL_THICKNESS);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int contentSize = Math.max(getSuggestedMinimumWidth(), getSuggestedMinimumHeight());
    int width = resolveSizeAndState(getPaddingLeft() + getPaddingRight() + contentSize,
        widthMeasureSpec, 0);
    int height = resolveSizeAndState(getPaddingTop() + getPaddingBottom() + contentSize,
        heightMeasureSpec, 0);
    contentSize = Math.max(width - getPaddingRight() - getPaddingLeft(),
        height - getPaddingTop() - getPaddingBottom());
    width = resolveSizeAndState(getPaddingLeft() + getPaddingRight() + contentSize,
        widthMeasureSpec, 0);
    height = resolveSizeAndState(getPaddingTop() + getPaddingBottom() + contentSize,
        heightMeasureSpec, 0);
    setMeasuredDimension(width, height);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    if (cells != null) {
      canvas.drawColor(Color.BLUE);
      int width = getWidth();
      int height = getHeight();
      float cellHeight = (float) (height - WALL_THICKNESS) / cells.length;
      float cellWidth = (float) (width - WALL_THICKNESS) / cells[0].length;
      for (int row = 0; row < cells.length; row++) {
        for (int col = 0; col < cells[row].length; col++) {
          Cell current = cells[row][col];
          for (Direction d : current.getWalls()) {
            int startCol =
                current.getColumn() + (d.getColumnOffset() + 1) / 2 - (d.getRowOffset() - 1) / 2;
            int endCol = startCol + d.getRowOffset();
            int startRow =
                current.getRow() + (d.getRowOffset() + 1) / 2 - (d.getColumnOffset() - 1) / 2;
            int endRow = startRow + d.getColumnOffset();
            canvas.drawLine(startCol * cellWidth, startRow * cellHeight,
                endCol * cellWidth, endRow * cellHeight, wallPaint);
          }
        }
      }
    }
  }

  public void setCells(Cell[][] cells) {
    this.cells = cells; // TODO Investigate safe copy.
    postInvalidate();
  }

}
//
//  @NonNull
//  @Override
//  public String toString() {
//    String numbers = "";
//    for (int row = 0; row < rows; row++){
//      for (int col = 0; col < columns; columns++){
//        numbers += cells[row][col].toString();
//      }
//    }
//    return super.toString();
//  }
//
//  public static void main(String[] args) {
//    Sketch sketch = new Sketch();
//    sketch.
//  }

//  const WORLD_SIZE = 800;
//const MAZE_SIZE = 50;
//
//  let cells = [];
//  let maze = null;
//
//  function setup() {
//    createCanvas(WORLD_SIZE, WORLD_SIZE);
//    for (let r = 0; r < MAZE_SIZE; r++) {
//      row = [];
//      for (let c = 0; c < MAZE_SIZE; c++) {
//        row.push(new Cell(r, c));
//      }
//      cells.push(row);
//    }
//    cells[0][0].build(cells);
//  }
//
//  function draw() {
//    if (maze === null) {
//      maze = mazeImage(WORLD_SIZE, cells);
//    }
//    image(maze, 0, 0);
//    noLoop();
//  }
//
//  function mazeImage(worldSize, cells) {
//  const cellSize = Math.floor(worldSize / cells.length);
//  const buffer = createGraphics(worldSize, worldSize);
//    buffer.stroke(100);
//    for (let row of cells) {
//      for (let cell of row) {
//        cell.draw(buffer, cellSize);
//      }
//    }
//    return buffer;
//

