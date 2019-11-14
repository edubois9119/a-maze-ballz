package com.ericadubois.amazeballz.model;

import static com.ericadubois.amazeballz.model.Direction.NORTH;
import static com.ericadubois.amazeballz.model.Direction.SOUTH;
import static com.ericadubois.amazeballz.model.Direction.WEST;
import static com.ericadubois.amazeballz.model.Direction.EAST;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;

/**
 * The type Maze view. This is the class that handles all of the drawing of the maze.
 */
public class MazeView extends View {

  private Cell[][] cells;
  private Cell ball, exit;
  private static final float WALL_THICKNESS = 4;
  private Paint wallPaint, ballPaint, exitPaint;
  private float radius;

  /**
   * Instantiates a new Maze view.
   *
   * @param context the context
   * @param attrs   the attrs
   */
  public MazeView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);

    wallPaint = new Paint();
    wallPaint.setColor(Color.BLACK);
    wallPaint.setStrokeWidth(WALL_THICKNESS);
    ballPaint = new Paint();
    ballPaint.setColor(Color.GREEN);
    exitPaint = new Paint();
    exitPaint.setColor(Color.RED);
  }

  /**
   * Required for the onDraw
   */
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
      canvas.drawColor(Color.DKGRAY);
      int width = getWidth();
      int height = getHeight();
      float cellHeight = (height - WALL_THICKNESS) / cells.length;
      float cellWidth = (width - WALL_THICKNESS) / cells[0].length;
      radius = cellWidth / 2.15F;
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
            canvas.drawCircle((float) (ball.getColumn() + .5) * cellWidth,
                (float) (ball.getRow() + .5) * cellHeight, radius, ballPaint);
            canvas.drawCircle((float) (exit.getColumn() + .5) * cellWidth,
                (float) (exit.getRow() + .5) * cellHeight, radius, exitPaint);
          }
        }
      }
    }
  }


  /**
   * Sets cells.
   *
   * @param cells the cells
   */
  public void setCells(Cell[][] cells) {
    this.cells = cells;   // TODO Investigate safe copy.
    this.ball = cells[0][0];
    this.exit = cells[cells.length - 1][cells[0].length - 1];
    postInvalidate();
  }

  private void moveBall(Direction direction) {
    if (!ball.getWalls().contains(direction)) {
      ball = cells[ball.getRow() + direction.getRowOffset()][ball.getColumn() + direction
          .getColumnOffset()];
      invalidate();
    }
  }


  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      return true;
    }
    if (event.getAction() == MotionEvent.ACTION_MOVE) {
      float x = event.getX();
      float y = event.getY();

      int width = getWidth();
      int height = getHeight();
      float cellHeight = (height - WALL_THICKNESS) / cells.length;
      float cellWidth = (width - WALL_THICKNESS) / cells[0].length;
      float ballCenterX = (float) (ball.getColumn() + 0.5f) * cellWidth;
      float ballCenterY = (float) (ball.getRow() + 0.5f) * cellHeight;

      float dx = x - ballCenterX;
      float dy = y - ballCenterY;

      float absDX = Math.abs(dx);
      float absDY = Math.abs(dy);

      if (absDX > cellHeight / 2 || absDY > cellHeight / 2) {
        if (absDX > absDY) {
          //move in x-direction
          if (dx > 0) {
            moveBall(Direction.EAST);
          } else {
            moveBall(Direction.WEST);
          }
        } else {
          //move in y-direction
          if (dy > 0) {
            moveBall(Direction.SOUTH);
          } else {
            moveBall(Direction.NORTH);
          }
        }
      }
      return true;
    }
    return super.onTouchEvent(event);
  }

//  @Override
//  public void onSensorChanged(SensorEvent event) {
//    float x = event.values[0];
//    float y = event.values[1];
//    if (Math.abs(x) > Math.abs(y)) {
//      if (x < 0) {
//        moveBall(EAST);
//        System.out.println("You tilt the device right");
//      }
//      if (x > 0) {
//        moveBall(WEST);
//        System.out.println("You tilt the device left");
//      }
//    } else {
//      if (y < 0) {
//        moveBall(NORTH);
//        System.out.println("You tilt the device up");
//      }
//      if (y > 0) {
//        moveBall(SOUTH);
//        System.out.println("You tilt the device down");
//      }
//    }
//    if (x > (-2) && x < (2) && y > (-2) && y < (2)) {
//      System.out.println("Not tilt device");
//    }
//  }
//
//  @Override
//  public void onAccuracyChanged (Sensor sensor,int accuracy){
//
//  }
}

//
//
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
