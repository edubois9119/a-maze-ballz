package entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;

@Entity
public class Maze {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "maze_id")
  private long id;

  @NonNull
  @ColumnInfo(index = true)
  private Date created= new Date();

  @NonNull
  @ColumnInfo(index= true)
  private Date updated= new Date();

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
}
