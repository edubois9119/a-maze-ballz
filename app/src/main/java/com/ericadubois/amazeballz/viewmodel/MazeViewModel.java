/**
 * This work is Copyright 2019, Erica DuBois. ALL RIGHTS RESERVED.
 */

package com.ericadubois.amazeballz.viewmodel;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.ericadubois.amazeballz.pojos.MazeBuilder;
import com.ericadubois.amazeballz.model.entity.Attempt;
import com.ericadubois.amazeballz.model.entity.Maze;
import com.ericadubois.amazeballz.model.entity.User;
import com.ericadubois.amazeballz.service.AMazeBallzDatabase;
import com.ericadubois.amazeballz.service.GoogleSignInService;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/**
 * The type Maze view model.
 */
public class MazeViewModel extends AndroidViewModel implements LifecycleObserver {

  private MazeBuilder mazeBuilder;
  private boolean touchEnabled;
  private MutableLiveData<User> user= new MutableLiveData<>(null);
  private MutableLiveData<Maze> maze= new MutableLiveData<>(null);
  private Attempt attempt;
  private LiveData<List<Attempt>> attempts;
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
             createAttempt(maze.getId());
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
              createAttempt(id);
            });
  }

  private void createAttempt(long id) {
    attempt = new Attempt();
    attempt.setMazeId(id);
    long attemptID = database.getAttemptDao().insert(attempt);
    attempt.setId(attemptID);
  }

  public void recordSuccess(long duration){
    attempt.setSolved(true);
    attempt.setUserId(user.getValue().getId());
    attempt.setTimeSpent(duration);
    new Thread(() -> database.getAttemptDao().update(attempt)).start();
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

  public LiveData<List<Attempt>> getAttempts() {
    attempts= database.getAttemptDao().getUserAttempts(user.getValue().getId());
    return attempts;
  }

  //  public LiveData<List<Attempt>> getFinMazes(){
//
//  }

}

