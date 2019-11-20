/**
 * This work is Copyright 2019, Erica DuBois. ALL RIGHTS RESERVED.
 */

package com.ericadubois.amazeballz.pojos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import com.ericadubois.amazeballz.controller.MazeFragment;
import java.util.Random;

/**
 * This class creates a BallView. It allows the ball to move fluidly on the screen.
 */
public class BallView extends View {
  private Paint ballPaint;
  private PointF velocity;
  private PointF upperLeft;
  private PointF destination;

  private float radius;
  private float radiusGrowth;
  private float time = 1f;
  private float elapsedTime = 0.1f;
  private MazeFragment mazeFragment;
  private boolean movable = true;
  private Random random;

  /**
   * Instantiates a new Ball view.
   *
   * @param context the context
   * @param attrs   the attrs
   */
  public BallView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);

    ballPaint = new Paint();
    ballPaint.setColor(Color.BLUE);
    upperLeft = new PointF (0,0);
    destination = new PointF (0,0);
    velocity = new PointF (0,0);
    radius = 10f;
    random = new Random();
  }

  @Override
  protected void onDraw(Canvas canvas){
//    ballPaint.setAlpha((ballPaint.getAlpha()+ 5)%200 + 50);
    canvas.drawCircle(upperLeft.x + radius, upperLeft.y + radius, radius, ballPaint);
    updateLocation();
  }

  /**
   * Sets maze fragment.
   *
   * @param mazeFragment the maze fragment
   */
  public void setMazeFragment(MazeFragment mazeFragment) {
    this.mazeFragment = mazeFragment;
  }

  private void updateLocation(){
    float TOLERANCE = .01f;
    if (Math.abs(upperLeft.x - destination.x) < TOLERANCE &&
        Math.abs(upperLeft.y - destination.y) < TOLERANCE) {
      upperLeft.x = destination.x;
      upperLeft.y = destination.y;
      velocity.x = 0;
      velocity.y = 0;
      movable = true;
    }
    else{
      upperLeft.x += velocity.x * elapsedTime;
      upperLeft.y += velocity.y * elapsedTime;
      invalidate();
    }
  }

  /**
   * Is movable boolean.
   *
   * @return the boolean
   */
  public boolean isMovable() {
    return movable;
  }

  private void updateVelocity(PointF acceleration){
    velocity.x += acceleration.x * elapsedTime;
    velocity.y += acceleration.y * elapsedTime;
  }

  /**
   * Gets velocity of the ball.
   *
   * @return the velocity
   */
  public PointF getVelocity() {
    return velocity;
  }

  /**
   * Sets velocity of the ball.
   *
   * @param velocity the velocity
   */
  public void setVelocity(PointF velocity) {
    this.velocity = velocity;
  }

  /**
   * Sets upper left point of the ball.
   *
   * @param x the x
   * @param y the y
   */
  public void setUpperLeft(float x, float y) {
    upperLeft = new PointF(x, y);
    invalidate();
  }

  /**
   * Sets destination point of the ball.
   *
   * @param x the x coordinate of the ball's destination
   * @param y the y coordinate of the ball's destination
   */
  public void setDestination(float x, float y) {
    velocity.x = (x - upperLeft.x) / time;
    velocity.y = (y - upperLeft.y) / time;
    destination.x = x;
    destination.y = y;
    movable = false;
    invalidate();
  }

//  public void setTopLeftLocation(PointF topLeftLocation) {
//    this.centerLocation = new PointF(topLeftLocation.x + radius,
//        topLeftLocation.y + radius);
//  }

  /**
   * Gets the radius of the ball.
   *
   * @return the radius of the ball
   */
  public float getRadius() {
    return radius;
  }

  /**
   * Sets radius of ball.
   *
   * @param radius the radius of the ball
   */
  public void setRadius(float radius) {
    this.radius = radius;
  }

  public PointF getUpperLeft() {
    return upperLeft;
  }

  public PointF getDestination() {
    return destination;
  }
}
