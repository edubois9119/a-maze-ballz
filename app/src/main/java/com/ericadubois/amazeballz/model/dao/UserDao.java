/**
 * This work is Copyright 2019, Erica DuBois. ALL RIGHTS RESERVED.
 */

package com.ericadubois.amazeballz.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.ericadubois.amazeballz.model.entity.User;
import io.reactivex.Maybe;
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

  @Query("SELECT * FROM User WHERE oauth_key=:oauthKey")
//  LiveData<User> getByOauth(String oauthKey);
  Maybe<User> getByOauth(String oauthKey);

  @Query("SELECT * FROM User WHERE user_id=:userId")
  LiveData<User> getById(long userId);

}
