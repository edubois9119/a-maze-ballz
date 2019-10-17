package com.ericadubois.amazeballz.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.ericadubois.amazeballz.model.entity.Maze;

@Dao
public interface MazeDao {

  @Insert
  public long insert (Maze maze);

  @Update
  public int update(Maze maze);

  @Delete
  public int delete(Maze maze);

 // @Query("SELECT")
  //@Query(levels, mazes) separate query for each set of data retrieval

  //get maze
  //get difficulty
  // get grid size
  // get ball size


}
