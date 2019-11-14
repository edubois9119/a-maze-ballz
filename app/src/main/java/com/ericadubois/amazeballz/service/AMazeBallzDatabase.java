package com.ericadubois.amazeballz.service;

//What is the main navigation mechanism going to use? What happens after the login?
//use tab navigation for card selection in dominion
//Json file/application?

import android.app.Application;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import com.ericadubois.amazeballz.model.Cell;
import com.ericadubois.amazeballz.model.Direction;
import com.ericadubois.amazeballz.model.dao.AttemptDao;
import com.ericadubois.amazeballz.model.dao.MazeDao;
import com.ericadubois.amazeballz.model.dao.UserDao;
import com.ericadubois.amazeballz.model.entity.Attempt;
import com.ericadubois.amazeballz.model.entity.Maze;
import com.ericadubois.amazeballz.model.entity.User;
import com.ericadubois.amazeballz.service.AMazeBallzDatabase.Converters;
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

  public static void setApplicationContext(Application applicationContext) {
    AMazeBallzDatabase.applicationContext = applicationContext;
  }

  public static AMazeBallzDatabase getInstance() {
    return InstanceHolder.INSTANCE;
  }

  public abstract MazeDao getMazeDao();

  public abstract AttemptDao getAttemptDao();

  public abstract UserDao getUserDao();

  private static class InstanceHolder {

    private static final AMazeBallzDatabase INSTANCE;

    static {
      INSTANCE =
          Room.databaseBuilder(applicationContext, AMazeBallzDatabase.class,
              "a_maze_ballz_db").build();
    }
  }

  public static class Converters {

    @TypeConverter
    public Long mazeStarted(Date date) {
      return (date != null) ? date.getTime() : null;
    }

    @TypeConverter
    public Date mazeEnded(Long milliseconds) {
      return (milliseconds != null) ? new Date(milliseconds) : null;
    }

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

    @TypeConverter
    public Cell[][] stringToCells(String value) {
      int rows = 0;
      int columns = 0;
      Cell[][] cells = new Cell[rows][columns];
      if (value == null) {
        return cells;
      }
      rows = Integer.parseInt(value.substring(0, 2), 16);
      columns = Integer.parseInt(value.substring(2, 4), 16);
      char[] chars = value.substring(4).toCharArray();
      Cell cell = new Cell(rows, columns);
      for (int row = 0; row < rows; row++) {
        for (int col = 0; col < columns; col++) {
          char c = chars[row * columns + col];
          int directionValue = Integer.parseInt("" + c, 16);
          List<Direction> directions = new LinkedList<>();
          for (Direction dir : Direction.values()) {
            if (directionValue != 0 & (1 << dir.ordinal()) != 0) {
              directions.add(dir);
            }
            cell.setWalls(directions);
            cells[row][col] = cell;
          }
        }
      }
      return cells;
    }


  }
}
