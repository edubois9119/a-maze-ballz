package com.ericadubois.amazeballz.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.util.Date;

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

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "attempt_id")
  private long id;

  @ColumnInfo(name= "maze_Id", index = true)
  private long mazeId;

  @ColumnInfo(name = "user_id", index = true)
  private long userId;

  @NonNull
  @ColumnInfo(name= "game_started", index = true)
  private Date gameStarted = new Date();

  @NonNull
  @ColumnInfo(name= "game_ended", index = true)
  private Date gameEnded = new Date();

  @ColumnInfo(name= "solved", index = true)
  public Boolean solved;

  @ColumnInfo(name= "num_Attempts", index = true)
  private long numAttempts;

  public long getNumAttempts() {
    return numAttempts;
  }

  public void setNumAttempts(long numAttempts) {
    this.numAttempts = numAttempts;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @NonNull
  public Date getCreated() {
    return gameStarted;
  }

  public void setCreated(@NonNull Date created) {
    this.gameStarted = created;
  }

  @NonNull
  public Date getUpdated() {
    return gameEnded;
  }

  public void setUpdated(@NonNull Date updated) {
    this.gameEnded = updated;
  }

  public long getMazeId() {
    return mazeId;
  }

  public void setMazeId(long mazeId) {
    this.mazeId = mazeId;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public Boolean getSolved() {
    return solved;
  }

  public void setSolved(Boolean solved) {
    this.solved = solved;
  }
}
