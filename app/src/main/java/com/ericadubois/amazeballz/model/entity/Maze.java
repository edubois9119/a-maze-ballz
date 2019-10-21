package com.ericadubois.amazeballz.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity
public class Maze {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "maze_id")
  private long id;

  private String walls;

  private int difficulty;

  @Embedded(prefix = "entrance_")
  private Point entrance;

  @Embedded(prefix = "exit_")
  private Point exit;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getWalls() {
    return walls;
  }

  public void setWalls(String walls) {
    this.walls = walls;
  }

  public int getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(int difficulty) {
    this.difficulty = difficulty;
  }

  public Point getEntrance() {
    return entrance;
  }

  public void setEntrance(Point entrance) {
    this.entrance = entrance;
  }

  public Point getExit() {
    return exit;
  }

  public void setExit(Point exit) {
    this.exit = exit;
  }

  public static class Point {
    int x;
    int y;
  }


}
