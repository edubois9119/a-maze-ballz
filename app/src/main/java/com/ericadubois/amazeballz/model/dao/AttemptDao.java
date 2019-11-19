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
   * List of Attempts of user for maze
   * @param userId
   * @param mazeId
   * @return LiveData list of attempts
   */
  @Query("SELECT * FROM Attempt WHERE user_id=:userId and maze_Id=:mazeId ORDER BY attempt_id DESC")
  LiveData<List<Attempt>> getUserMazeAttempts(long userId, long mazeId);

  /**
   * Calculates the number of unsuccessful attempts by a user on a given maze.
   * @param userId
   * @param mazeId
   * @return Integer for number of unsuccessful attempts
   */
  @Query("SELECT COUNT(*) FROM Attempt WHERE user_id = :userId AND maze_id = :mazeId AND NOT solved")
  LiveData<Integer> getCountUnsuccessfulAttempts(long userId, long mazeId);

  /**
   * The number of mazes solved for a user.
   * @param userId
   * @return
   */
  @Query("SELECT COUNT(DISTINCT maze_id) FROM Attempt WHERE user_id= :userId AND solved")
  LiveData<Integer> getCountSuccessful(long userId);

  /**
   * The list of mazes the user has solved.
   */
  @Query("SELECT * FROM Attempt WHERE user_id= :userId AND solved GROUP BY maze_id")
  LiveData<List<Attempt>> getSuccessfulMazes(long userId);



}

