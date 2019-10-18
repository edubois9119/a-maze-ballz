package com.ericadubois.amazeballz.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.ericadubois.amazeballz.model.entity.Maze;
import com.ericadubois.amazeballz.model.entity.User;
import java.util.List;

@Dao
public interface UserDao {
  @Insert
  public long insert (User user);

  @Query("SELECT * FROM User ORDER BY user_id ASC")
  List<User> getAll();
//check to see if user is in database
  @Update
  public int update(User user);

  @Delete
  public int delete(User user);


}
