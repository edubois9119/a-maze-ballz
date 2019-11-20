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

/**
 * The interface User dao.
 */
@Dao
public interface UserDao {

  /**
   * Inserts User.
   *
   * @param user the user
   * @return the long
   */
  @Insert
  public long insert(User user);

  /**
   * Updates User.
   *
   * @param user the user
   * @return the int
   */
  @Update
  public int update(User user);

  /**
   * Deletes User.
   *
   * @param user the user
   * @return the int
   */
  @Delete
  public int delete(User user);

  /**
   * Gets all Users.
   *
   * @return the all
   */
//check to see if user is in database
  @Query("SELECT * FROM User ORDER BY user_id ASC")
  LiveData<List<User>> getAll();

  /**
   * Gets users by oauthKey.
   *
   * @param oauthKey the oauth key
   * @return the by oauth
   */
  @Query("SELECT * FROM User WHERE oauth_key=:oauthKey")
  Maybe<User> getByOauth(String oauthKey);

  /**
   * Gets User by id.
   *
   * @param userId the user id
   * @return the by id
   */
  @Query("SELECT * FROM User WHERE user_id=:userId")
  LiveData<User> getById(long userId);

}
