package com.ericadubois.amazeballz.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;
import com.ericadubois.amazeballz.model.entity.Maze;
import com.ericadubois.amazeballz.model.entity.User;

@Dao
public interface UserDao {
  @Insert
  public long insert (User user);

  @Update
  public int update(User user);

  @Delete
  public int delete(User user);


}
