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
import com.ericadubois.amazeballz.model.dao.AttemptDao;
import com.ericadubois.amazeballz.model.dao.MazeDao;
import com.ericadubois.amazeballz.model.dao.UserDao;
import com.ericadubois.amazeballz.model.entity.Attempt;
import com.ericadubois.amazeballz.model.entity.Maze;
import com.ericadubois.amazeballz.model.entity.User;
import com.ericadubois.amazeballz.service.AMazeBallzDatabase.Converters;
import java.util.Date;

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

  }

}
