/**
 * This work is Copyright 2019, Erica DuBois. ALL RIGHTS RESERVED.
 */

package com.ericadubois.amazeballz.service;

import android.app.Application;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import com.ericadubois.amazeballz.pojos.Cell;
import com.ericadubois.amazeballz.pojos.Direction;
import com.ericadubois.amazeballz.model.dao.AttemptDao;
import com.ericadubois.amazeballz.model.dao.MazeDao;
import com.ericadubois.amazeballz.model.dao.UserDao;
import com.ericadubois.amazeballz.model.entity.Attempt;
import com.ericadubois.amazeballz.model.entity.Maze;
import com.ericadubois.amazeballz.model.entity.User;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


@Database(
    entities = {Maze.class, Attempt.class, User.class},
    version = 1, exportSchema = true
)
@TypeConverters(AMazeBallzDatabase.Converters.class)
public abstract class AMazeBallzDatabase extends RoomDatabase {

  private static Application applicationContext;

  /**
   * Sets application context.
   *
   * @param applicationContext the application context
   */
  public static void setApplicationContext(Application applicationContext) {
    AMazeBallzDatabase.applicationContext = applicationContext;
  }

  /**
   * Gets instance of AMazeBallz database
   *
   * @return the instance
   */
  public static AMazeBallzDatabase getInstance() {
    return InstanceHolder.INSTANCE;
  }

  /**
   * Gets maze dao.
   *
   * @return the maze dao
   */
  public abstract MazeDao getMazeDao();

  /**
   * Gets attempt dao.
   *
   * @return the attempt dao
   */
  public abstract AttemptDao getAttemptDao();

  /**
   * Gets user dao.
   *
   * @return the user dao
   */
  public abstract UserDao getUserDao();

  private static class InstanceHolder {

    private static final AMazeBallzDatabase INSTANCE;

    static {
      INSTANCE =
          Room.databaseBuilder(applicationContext, AMazeBallzDatabase.class,
              "a_maze_ballz_db").build();
    }
  }

  /**
   * This class uses TypeConverters to communicate with the database.
   */
  public static class Converters {

    /**
     * Maze start time.
     *
     * @param date the date
     * @return the long
     */
    @TypeConverter
    public Long mazeStarted(Date date) {
      return (date != null) ? date.getTime() : null;
    }

    /**
     * Maze end time.
     *
     * @param milliseconds the milliseconds
     * @return the date
     */
    @TypeConverter
    public Date mazeEnded(Long milliseconds) {
      return (milliseconds != null) ? new Date(milliseconds) : null;
    }

    /**
     * Converts the cell array of hex digits to a string.
     *
     * @param cells the cells
     * @return the string
     */
    @TypeConverter
    public String cellsToString(Cell[][] cells) {
      if (cells == null) {
        return null;
      }
      int rows = cells.length;
      int columns = cells[0].length;
      StringBuilder builder = new StringBuilder(rows * columns + 4);
      builder.append(String.format("%02x", rows)).append(String.format("%02x", columns));
      for (int row = 0; row < rows; row++) {
        for (int col = 0; col < columns; col++) {
          builder.append(cells[row][col].value());
        }
      }
      return builder.toString();
    }

    /**
     * Converts the String to the cells array of hex digits.
     *
     * @param value the value
     * @return the cell [ ] [ ]
     */
    @TypeConverter
    public Cell[][] stringToCells(String value) {
      if (value == null) {
        return new Cell[0][];
      }
      int rows = Integer.parseInt(value.substring(0, 2), 16);
      int columns = Integer.parseInt(value.substring(2, 4), 16);
      Cell[][] cells = new Cell[rows][columns];
      char[] chars = value.substring(4).toCharArray();
      for (int row = 0; row < rows; row++) {
        for (int col = 0; col < columns; col++) {
          Cell cell = new Cell(row, col);
          char c = chars[row * columns + col];
          int directionValue = Integer.parseInt("" + c, 16);
          List<Direction> directions = new LinkedList<>();
          for (Direction dir : Direction.values()) {
            if ((directionValue & (1 << dir.ordinal())) != 0) {
              directions.add(dir);
            }
          }
          cell.setWalls(directions);
          cells[row][col] = cell;
        }
      }
      return cells;
    }


  }
}
