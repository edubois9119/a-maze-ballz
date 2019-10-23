package com.ericadubois.amazeballz.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.ericadubois.amazeballz.model.entity.User;
import java.util.List;

@Dao
public interface UserDao {
  @Insert
  public long insert (User user);

  @Update
  public int update(User user);

  @Delete
  public int delete(User user);

  //check to see if user is in database
  @Query("SELECT * FROM User ORDER BY user_id ASC")
  LiveData<List<User>> getAll();

  @Query("SELECT * FROM user WHERE oauth_key=:oauthKey")
  LiveData<User> getByOauth(String oauthKey);

  @Query("SELECT * FROM user WHERE user_id=:userId")
  LiveData<User> getById(long userId);

}
