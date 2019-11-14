package com.ericadubois.amazeballz.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.ericadubois.amazeballz.model.entity.Maze;
import java.util.List;

@Dao
public interface MazeDao {

  @Insert
  public long insert (Maze maze);

  @Update
  public int update(Maze maze);

  @Delete
  public int delete(Maze... maze);

  @Query("SELECT * FROM Maze WHERE maze_id = :mazeId")
  LiveData<Maze> findById(long mazeId);

  @Query("SELECT * FROM Maze WHERE level = :level")
  LiveData<List<Maze>> mazesByDifficulty(int level);

  @Query("SELECT * FROM Maze ORDER BY level DESC")
  LiveData<List<Maze>> allMazesByDifficulty();

  //Where = if condition
}
