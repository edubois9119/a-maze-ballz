/**
 * This work is Copyright 2019, Erica DuBois. ALL RIGHTS RESERVED.
 */

package com.ericadubois.amazeballz.pojos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import com.ericadubois.amazeballz.controller.MazeFragment;

/**
 * The type Maze view. This is the class that handles all of the drawing of the maze.
 */
public class MazeView extends View {

  private MazeFragment mazeFragment;
  private Cell[][] cells;
  private float cellWidth, cellHeight;
//  private Cell ball, exit;
  private Cell exit;
  private static final float WALL_THICKNESS = 4;
  private Paint wallPaint, ballPaint, exitPaint;
  private float radius;
  private BallView ballView;
  private int ballViewColumn;
  private int ballViewRow;

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
      cellHeight = (height - WALL_THICKNESS) / cells.length;
      cellWidth = (width - WALL_THICKNESS) / cells[0].length;
      radius = cellWidth / 2.15F;
      ballView.setRadius(radius);

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
//      canvas.drawCircle( (ball.getColumn() + .5f) * cellWidth,
//          (ball.getRow() + .5f) * cellHeight, radius, ballPaint);
      canvas.drawCircle( (exit.getColumn() + .5f) * cellWidth,
          (exit.getRow() + .5f) * cellHeight, radius, exitPaint);
    }
  }

  /**
   * Sets cells of the maze.
   *
   * @param cells the cells
   */
  public void setCells(Cell[][] cells) {
    this.cells = cells;   // TODO Investigate safe copy.
//    this.ball = cells[0][0];
//    int width = getWidth();
//    int height = getHeight();
//    cellHeight = (height - WALL_THICKNESS) / cells.length;
//    cellWidth = (width - WALL_THICKNESS) / cells[0].length;
//    radius = cellWidth / 2.15F;
    this.exit = cells[cells.length - 1][cells[0].length - 1];
    this.ballView.setUpperLeft(0, 0);
//    this.ballView.setRadius(radius);

    postInvalidate();
  }

  /**
   * Move ball.
   *
   * @param direction the direction
   */
  public void moveBall(Direction direction) {
    if (!ballView.isMovable()) {
      return;
    }
    ballViewColumn = (int) (ballView.getUpperLeft().x / cellWidth);
    ballViewRow = (int) (ballView.getUpperLeft().y / cellHeight);

    if (!cells[ballViewRow][ballViewColumn].getWalls().contains(direction)) {
//      ball = cells[ball.getRow() + direction.getRowOffset()][ball.getColumn() + direction
//          .getColumnOffset()];
      ballView.setDestination((ballViewColumn + direction.getColumnOffset()) * cellWidth,
          (ballViewRow + direction.getRowOffset()) * cellHeight);
      invalidate();
    }
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    //if not touchEnabled, return
    if (mazeFragment != null && mazeFragment.getViewModel() != null &&
        !mazeFragment.getViewModel().isTouchEnabled() || !mazeFragment.isRunning()){
      return super.onTouchEvent(event);
    }

    checkCompletion();
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
      float ballCenterX = (ballViewColumn + 0.5f) * cellWidth;
      float ballCenterY = (ballViewRow + 0.5f) * cellHeight;

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

  /**
   * Sets maze fragment.
   *
   * @param mazeFragment the maze fragment
   */
  public void setMazeFragment(MazeFragment mazeFragment) {
    this.mazeFragment = mazeFragment;
  }

  /**
   * Set ball view.
   *
   * @param ballView the ball view
   */
  public void setBallView(BallView ballView){
    this.ballView = ballView;
  }

  /**
   * Checks for completion of maze. When maze is successfully completed, maze fragment is switched
   * with completion fragment
   */
//  public void checkCompletion() {
//    if (ball != null && ball.getColumn() == exit.getColumn() && ball.getRow() == exit.getRow()) {
//
//      mazeFragment.recordSuccess();
//      mazeFragment.switchFragment();
//      System.out.println("Winner");
//
//    }
//  }
  public void checkCompletion() {
     ballViewColumn = (int) (ballView.getUpperLeft().x / cellWidth);
     ballViewRow = (int) (ballView.getUpperLeft().y / cellHeight);
    if (ballViewColumn == exit.getColumn() && ballViewRow == exit.getRow()) {
      mazeFragment.recordSuccess();
      mazeFragment.switchFragment();
      System.out.println("Winner");
    }
  }
}
