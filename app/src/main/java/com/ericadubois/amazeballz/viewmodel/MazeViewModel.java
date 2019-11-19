package com.ericadubois.amazeballz.viewmodel;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.ericadubois.amazeballz.model.MazeBuilder;
import com.ericadubois.amazeballz.model.entity.Attempt;
import com.ericadubois.amazeballz.model.entity.Maze;
import com.ericadubois.amazeballz.service.AMazeBallzDatabase;
import io.reactivex.schedulers.Schedulers;

/**
 * The type Maze view model.
 */
public class MazeViewModel extends AndroidViewModel implements LifecycleObserver {

  private MazeBuilder mazeBuilder;
  private boolean touchEnabled;
  private MutableLiveData<Maze> maze= new MutableLiveData<>(null);

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

  public void loadMaze(int rows, int columns, int level){
    database.getMazeDao().mazesByDifficulty(rows, columns, level)
        .subscribeOn(Schedulers.io())
        .subscribe(
            (maze)-> {
              this.maze.postValue(maze);
              // TODO Create a new attempt against this maze.
            },
            (ex)-> {
              Log.e(ex.getClass().getSimpleName(), ex.getMessage(), ex);
            },
            ()-> {
              MazeBuilder mazeBuilder = new MazeBuilder(rows, columns);
              Maze maze = new Maze();
              maze.setGridColumns(columns);
              maze.setGridRows(rows);
              maze.setLevel(level);
              maze.setWalls(mazeBuilder.getCells());
              long id= database.getMazeDao().insert(maze);
              maze.setId(id);
              this.maze.postValue(maze);
              // TODO Create a new attempt against this maze.
            });

  }

  public boolean isTouchEnabled() {
    return touchEnabled;
  }

  public void setTouchEnabled(boolean touchEnabled) {
    this.touchEnabled = touchEnabled;
  }

  public LiveData<Maze> getMaze() {
    return maze;
  }
}

