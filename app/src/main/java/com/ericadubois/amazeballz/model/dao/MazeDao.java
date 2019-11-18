package com.ericadubois.amazeballz.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.ericadubois.amazeballz.model.entity.Maze;
import io.reactivex.Maybe;
import io.reactivex.Single;
import java.util.List;

/**
 * The interface Maze dao.
 */
@Dao
public interface MazeDao {

  /**
   * Inserts the maze.
   *
   * @param maze the maze
   * @return the long
   */
  @Insert
  public long insert (Maze maze);

  /**
   * Updates the maze
   *
   * @param maze the maze
   * @return
   */
  @Update
  public int update(Maze maze);

  /**
   * Deletes the maze.
   *
   * @param maze the maze
   * @return
   */
  @Delete
  public int delete(Maze... maze);

  /**
   * Finds maze by id.
   *
   * @param mazeId the maze id
   * @return the live data
   */
  @Query("SELECT * FROM Maze WHERE maze_id = :mazeId")
  LiveData<Maze> findById(long mazeId);

  /**
   * Finds mazes by difficulty (level).
   *
   * @param level the level
   * @return the live data
   */
  @Query("SELECT * FROM Maze WHERE level = :level")
  LiveData<List<Maze>> mazesByDifficulty(int level);

  @Query("SELECT m.* FROM Maze m LEFT JOIN Attempt att ON m.maze_id= att.maze_id AND att.solved "
      + "WHERE m.grid_rows= :rows and m.grid_columns= :columns and m.level = :level and att.attempt_id IS NULL ORDER BY RANDOM() LIMIT 1")
  Maybe<Maze> mazesByDifficulty(int rows, int columns, int level);
  /**
   * Finds all mazes by difficulty(level) live data.
   *
   * @return the live data
   */
  @Query("SELECT * FROM Maze ORDER BY level DESC")
  LiveData<List<Maze>> allMazesByDifficulty();

}
