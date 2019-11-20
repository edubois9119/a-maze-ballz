/**
 * This work is Copyright 2019, Erica DuBois. ALL RIGHTS RESERVED.
 */

package com.ericadubois.amazeballz.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * Keeps track of all attempts made by a user. Stores number of attempts, outcome, and time spent.
 */
@Entity(
    foreignKeys = {
        @ForeignKey(
            entity = Maze.class,
            childColumns = "maze_id",
            parentColumns = "maze_id",
            onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = User.class,
            childColumns = "user_id",
            parentColumns= "user_id",
            onDelete = ForeignKey.CASCADE
        )
    }
)

public class Attempt {

  /**
   * Creates primary key for Attempt Id.
   */
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "attempt_id")
  private long id;

  /**
   * Creates foreign key for Maze Id. Connects attempts to specific mazes.
   */
  @ColumnInfo(name= "maze_id", index = true)
  private long mazeId;
  /**
   * Creates foreign key for User Id. Connects attempts to specific user.
   */
  @ColumnInfo(name = "user_id", index = true)
  private Long userId;

  /**
   * Stores outcome of maze. Did you user solve it or quit?
   */
  @ColumnInfo(name= "solved", index = true)
  public Boolean solved;

  /**
   * Stores time spent on maze.
   */
  @ColumnInfo(name= "time_spent", index = true)
  private long timeSpent;

  /**
   * Gets time spent on a Maze in milliseconds.
   *
   * @return the time spent
   */
  public long getTimeSpent() {
    return timeSpent;
  }

  /**
   * Sets time spent on a Maze in milliseconds.
   *
   * @param timeSpent the time spent
   */
  public void setTimeSpent(long timeSpent) {
    this.timeSpent = timeSpent;
  }

  /**
   * Gets attempt id.
   *
   * @return the id
   */
  public long getId() {
    return id;
  }

  /**
   * Sets attempt id.
   *
   * @param id the id
   */
  public void setId(long id) {
    this.id = id;
  }

  /**
   * Gets maze id.
   *
   * @return the maze id
   */
  public long getMazeId() {
    return mazeId;
  }

  /**
   * Sets maze id.
   *
   * @param mazeId the maze id
   */
  public void setMazeId(long mazeId) {
    this.mazeId = mazeId;
  }

  /**
   * Gets user id.
   *
   * @return the user id
   */
  public Long getUserId() {
    return userId;
  }

  /**
   * Sets user id.
   *
   * @param userId the user id
   */
  public void setUserId(Long userId) {
    this.userId = userId;
  }

  /**
   * Gets flag for completion of maze.
   *
   * @return the solved
   */
  public Boolean getSolved() {
    return solved;
  }

  /**
   * Sets flag for completion of maze
   *
   * @param solved the solved
   */
  public void setSolved(Boolean solved) {
    this.solved = solved;
  }

  /**
   * Formats the attempt on the User Stats page.
   *
   * @return the string
   */
  public String statInfo() {
    //timeSpent is milliseconds
    int minutes= (int)((timeSpent / 1000) / 60);
    int seconds= (int)((timeSpent / 1000) % 60);

    String statInfo = "Time: " + String.format("%02d:%02d", minutes, seconds) + ", Solved: " + this.solved;
    return statInfo;
  }
}
