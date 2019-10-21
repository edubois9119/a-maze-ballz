package com.ericadubois.amazeballz.service;

//What is the main navigation mechanism going to use? What happens after the login?
//use tab navigation for card selection in dominion
//Json file/application?

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.ericadubois.amazeballz.model.dao.AttemptDao;
import com.ericadubois.amazeballz.model.dao.MazeDao;
import com.ericadubois.amazeballz.model.dao.UserDao;
import com.ericadubois.amazeballz.model.entity.Attempt;
import com.ericadubois.amazeballz.model.entity.Maze;
import com.ericadubois.amazeballz.model.entity.User;

@Database(
    entities = {Maze.class, Attempt.class, User.class},
    version = 1, exportSchema = true
)
public abstract class AMazeBallzDatabase extends RoomDatabase {

  public abstract MazeDao getMazeDao();
  public abstract AttemptDao getAttemptDao();
  public abstract UserDao getUserDao();












}
