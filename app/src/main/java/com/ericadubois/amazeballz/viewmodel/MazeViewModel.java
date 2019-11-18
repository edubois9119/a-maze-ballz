package com.ericadubois.amazeballz.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleObserver;
import com.ericadubois.amazeballz.model.MazeBuilder;
import com.ericadubois.amazeballz.model.entity.Attempt;
import com.ericadubois.amazeballz.model.entity.Maze;
import com.ericadubois.amazeballz.service.AMazeBallzDatabase;

/**
 * The type Maze view model.
 */
public class MazeViewModel extends AndroidViewModel implements LifecycleObserver {

  private MazeBuilder mazeBuilder;

  private final AMazeBallzDatabase database;

  /**
   * Instantiates a new Maze view model.
   *
   * @param application the application
   */
  public MazeViewModel(@NonNull Application application) {
    super(application);
    database= AMazeBallzDatabase.getInstance();

   //  mazeBuilder = new MazeBuilder();
  //   mazeBuilder.printMaze();
  }

  /**
   * Saves a maze to the database.
   *
   * @param maze the maze
   */
  public void saveMaze(Maze maze){
    new Thread(()->database.getMazeDao().insert(maze)).start();
  }

  /**
   * Saves an attempt to the database.
   *
   * @param attempt the attempt to save
   */
  public void saveAttempt(Attempt attempt){
    new Thread(()->database.getAttemptDao().insert(attempt)).start();
  }

  /**
   * Updates an attempt in the database.
   *
   * @param attempt the attempt to update
   */
  public void updateAttempt(Attempt attempt){
    new Thread(()->database.getAttemptDao().update(attempt)).start();
  }

}

