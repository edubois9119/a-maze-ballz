package com.ericadubois.amazeballz.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.ericadubois.amazeballz.model.entity.Attempt;
import java.util.List;

@Dao
public interface AttemptDao {

  @Insert
  public long insert(Attempt attempt);

  @Update
  public int update(Attempt attempt);


  /**
   * number of attempts per maze, by user
   * @param userId
   * @param mazeId
   * @return
   */
  @Query("SELECT * FROM Attempt WHERE user_id=:userId and maze_Id=:mazeId ORDER BY attempt_id DESC")
  LiveData<List<Attempt>> getNumAttempts(long userId, long mazeId);

  /**
   * number of attempts by user
   * @param userId
   * @return
   */
  @Query("SELECT * FROM Attempt WHERE user_id=:userId ORDER BY attempt_id DESC")
  List<Attempt> getNumAttempts(long userId);

  @Query("SELECT COUNT(*) FROM Attempt WHERE user_id = :userId AND maze_id = :mazeId AND NOT solved")
  LiveData<Integer> getCountUnsuccessfulAttempts(long userId, long mazeId);

  @Query("SELECT COUNT(*) FROM Attempt WHERE user_id= :userId AND solved")
  LiveData<List<Integer>> getCountSuccessful(long userId);

  //get outcome
  //get duration
  //TODO need to create pojo for this duration calculation


}

