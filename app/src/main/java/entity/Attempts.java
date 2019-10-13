package entity;

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

public class Attempts {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "attempts_id")
  private long id;

  @NonNull
  @ColumnInfo(index = true)
  private Date created = new Date();

  @NonNull
  @ColumnInfo(index = true)
  private Date updated = new Date();

  @ColumnInfo(name= "mazeId", index = true)
  private Long mazeId;

  @ColumnInfo
  private Long userId;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @NonNull
  public Date getCreated() {
    return created;
  }

  public void setCreated(@NonNull Date created) {
    this.created = created;
  }

  @NonNull
  public Date getUpdated() {
    return updated;
  }

  public void setUpdated(@NonNull Date updated) {
    this.updated = updated;
  }

  public Long getMazeId() {
    return mazeId;
  }

  public void setMazeId(Long mazeId) {
    this.mazeId = mazeId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
}
