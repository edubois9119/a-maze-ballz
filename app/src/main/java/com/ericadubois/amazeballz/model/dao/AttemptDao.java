package com.ericadubois.amazeballz.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.ericadubois.amazeballz.model.entity.Attempt;
import com.ericadubois.amazeballz.model.entity.Maze;
import java.util.List;

@Dao
public interface AttemptDao {

  @Insert
  public long insert(Attempt attempt);

  @Update
  public int update(Attempt attempt);


  @Query("SELECT * FROM Attempt WHERE user_id=:userId and maze_Id=:mazeId ORDER BY attempt_id DESC")
  List<Attempt> getNumAttempts(long userId, long mazeId);

  @Query("SELECT * FROM Attempt WHERE user_id=:user_id ORDER BY attempt_id DESC")
  List<Attempt> getNumAttempts(long user_id);


  //get outcome
// will be part of attempt query
  //@Query("SELECT ")

  //get duration
  //TODO need to create pojo for this duration calculation


}

