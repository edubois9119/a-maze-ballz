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
//  /**
//   * Creates timestamp for start of maze.
//   */
//  @NonNull
//  @ColumnInfo(name= "maze_started", index = true)
//  private Date mazeStarted = new Date();
//  /**
//   * Creates timestamp for start of maze.
//   */
//  @NonNull
//  @ColumnInfo(name= "maze_ended", index = true)
//  private Date mazeEnded = new Date();
//  /**
//   * Creates timestamp for start of pause during completion of a maze.
//   */
//  @NonNull
//  @ColumnInfo(name= "maze_pause_start", index = true)
//  private Date mazePauseStart = new Date();
//
//  /**
//   * Creates timestamp for end of pause during completion of a maze.
//   */
//  @NonNull
//  @ColumnInfo(name= "maze_pause_end", index = true)
//  private Date mazePauseEnd = new Date();

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

  public long getTimeSpent() {
    return timeSpent;
  }

  public void setTimeSpent(long timeSpent) {
    this.timeSpent = timeSpent;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

//  @NonNull
//  public Date getMazeStarted() {
//    return mazeStarted;
//  }
//
//  public void setMazeStarted(@NonNull Date created) {
//    this.mazeStarted = created;
//  }
//
//  @NonNull
//  public Date getMazeEnded() {
//    return mazeEnded;
//  }
//
//  public void setMazeEnded(@NonNull Date updated) {
//    this.mazeEnded = updated;
//  }

  public long getMazeId() {
    return mazeId;
  }

  public void setMazeId(long mazeId) {
    this.mazeId = mazeId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Boolean getSolved() {
    return solved;
  }

  public void setSolved(Boolean solved) {
    this.solved = solved;
  }

//  @NonNull
//  public Date getMazePauseStart() {
//    return mazePauseStart;
//  }
//
//  public void setMazePauseStart(@NonNull Date mazePauseStart) {
//    this.mazePauseStart = mazePauseStart;
//  }
//
//  @NonNull
//  public Date getMazePauseEnd() {
//    return mazePauseEnd;
//  }
//
//  public void setMazePauseEnd(@NonNull Date mazePauseEnd) {
//    this.mazePauseEnd = mazePauseEnd;
//  }
}
