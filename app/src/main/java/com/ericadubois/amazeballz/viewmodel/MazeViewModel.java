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
import com.ericadubois.amazeballz.model.entity.User;
import com.ericadubois.amazeballz.service.AMazeBallzDatabase;
import com.ericadubois.amazeballz.service.GoogleSignInService;
import io.reactivex.schedulers.Schedulers;

/**
 * The type Maze view model.
 */
public class MazeViewModel extends AndroidViewModel implements LifecycleObserver {

  private MazeBuilder mazeBuilder;
  private boolean touchEnabled;
  private MutableLiveData<User> user= new MutableLiveData<>(null);
  private MutableLiveData<Maze> maze= new MutableLiveData<>(null);
  private MutableLiveData<Attempt> attempt = new MutableLiveData<>(null);
  private MutableLiveData<Long> userId;
  private MutableLiveData<Long> mazeId;

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

  public void loadUser(){
    String userOauthKey = GoogleSignInService.getInstance().getAccount().getValue().getId();
    database.getUserDao().getByOauth(userOauthKey)
        .subscribeOn(Schedulers.io())
        .subscribe(
            (user)-> {
              this.user.postValue(user);
            },
            (ex)-> {
              Log.e(ex.getClass().getSimpleName(), ex.getMessage(), ex);
            },
            ()-> {
              User user = new User();
              user.setOauthKey(userOauthKey);
              long id = database.getUserDao().insert(user);
              user.setId(id);
              this.user.postValue(user);
            });
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
//              loadAttempt(user, maze, 0);
              // TODO Create a new attempt against this maze.
//              Attempt attempt = new Attempt();
//              attempt.setMazeId(id);
//              attempt.setUserId(123);
//              long attemptID = database.getAttemptDao().insert(attempt);
//              attempt.setId(attemptID);
//              this.attempt.postValue(attempt);
            });
  }

  public void loadAttempt(User user, Maze maze, long attemptId){
    database.getAttemptDao().findById(attemptId)
        .subscribeOn(Schedulers.io())
        .subscribe(
            (attempt)-> {
              this.attempt.postValue(attempt);
            },
            (ex)-> {
              Log.e(ex.getClass().getSimpleName(), ex.getMessage(), ex);
            },
            ()-> {
              Attempt attempt = new Attempt();
//              attempt.setUserId(user.getId());
//              attempt.setMazeId(maze.getId());
//              attempt.setUserId(getUser().getValue().getId());
//              attempt.setMazeId(getMaze().getValue().getId());
              long id = database.getAttemptDao().insert(attempt);
              attempt.setId(id);
              this.attempt.postValue(attempt);
            });
  }

  public boolean isTouchEnabled() {
    return touchEnabled;
  }

  public void setTouchEnabled(boolean touchEnabled) {
    this.touchEnabled = touchEnabled;
  }

  public LiveData<User> getUser() {
    return user;
  }
  public LiveData<Maze> getMaze() {
    return maze;
  }
  public LiveData<Attempt> getAttempt() {
    return attempt;
  }

}

