/**
 * This work is Copyright 2019, Erica DuBois. ALL RIGHTS RESERVED.
 */

package com.ericadubois.amazeballz.pojos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.ericadubois.amazeballz.controller.MazeFragment;
import java.util.Random;

/**
 * This class creates a BallView. It allows the ball to move fluidly on the screen.
 */
public class BallView extends View {

  private Paint ballPaint;
  private PointF velocity;
  private PointF ballCenter;
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
    ballPaint.setColor(Color.LTGRAY);
//    Shader shader = new LinearGradient(0, 0, 100, 400, Color.WHITE, Color.BLACK, TileMode.CLAMP);
//    ballPaint.setShader(shader);
    radius = 10f;
    ballCenter = new PointF(radius, radius);
    destination = new PointF(radius, radius);
    velocity = new PointF(radius, radius);
    random = new Random();
  }

  @Override
  protected void onDraw(Canvas canvas) {
//    ballPaint.setAlpha((ballPaint.getAlpha()+ 5)%200 + 50);
    canvas.drawCircle(ballCenter.x, ballCenter.y, radius, ballPaint);
    updateLocation();
  }

  private void updateLocation() {
    float TOLERANCE = .01f;
    if (Math.abs(ballCenter.x - destination.x) < TOLERANCE &&
        Math.abs(ballCenter.y - destination.y) < TOLERANCE) {
      ballCenter.x = destination.x;
      ballCenter.y = destination.y;
      velocity.x = 0;
      velocity.y = 0;
      movable = true;
    } else {
      ballCenter.x += velocity.x * elapsedTime;
      ballCenter.y += velocity.y * elapsedTime;
      invalidate();
    }
  }

  /**
   * Flag for whether or not the ball is movable. Used to prevent multiple events.
   *
   * @return the boolean
   */
  public boolean isMovable() {
    return movable;
  }

  private void updateVelocity(PointF acceleration) {
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
  public void setBallCenter(float x, float y) {
    ballCenter = new PointF(x, y);
    invalidate();
  }

  /**
   * Sets destination point of the ball.
   *
   * @param x the x coordinate of the ball's destination
   * @param y the y coordinate of the ball's destination
   */
  public void setDestination(float x, float y) {
    velocity.x = (x - ballCenter.x) / time;
    velocity.y = (y - ballCenter.y) / time;
    destination.x = x;
    destination.y = y;
    movable = false;
    invalidate();
  }

  /**
   * Sets radius of ball.
   *
   * @param radius the radius of the ball
   */
  public void setRadius(float radius) {
    this.radius = radius;
  }

  public PointF getBallCenter() {
    return ballCenter;
  }
}
