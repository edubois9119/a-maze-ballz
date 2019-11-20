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
import com.ericadubois.amazeballz.model.entity.Attempt;
import com.ericadubois.amazeballz.model.entity.Maze;
import com.ericadubois.amazeballz.model.entity.User;
import com.ericadubois.amazeballz.pojos.MazeBuilder;
import com.ericadubois.amazeballz.service.AMazeBallzDatabase;
import com.ericadubois.amazeballz.service.GoogleSignInService;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

/**
 * MazeViewModel that provides an interface to database data.
 */
public class MazeViewModel extends AndroidViewModel implements LifecycleObserver {

  private boolean touchEnabled;
  private MutableLiveData<User> user = new MutableLiveData<>(null);
  private MutableLiveData<Maze> maze = new MutableLiveData<>(null);
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
    database = AMazeBallzDatabase.getInstance();
  }

  /**
   * Loads user from database or adds a new one.
   */
  public void loadUser() {
    String userOauthKey = GoogleSignInService.getInstance().getAccount().getValue().getId();
    database.getUserDao().getByOauth(userOauthKey)
        .subscribeOn(Schedulers.io())
        .subscribe(
            (user) -> {
              this.user.postValue(user);
            },
            (ex) -> {
              Log.e(ex.getClass().getSimpleName(), ex.getMessage(), ex);
            },
            () -> {
              User user = new User();
              user.setOauthKey(userOauthKey);
              long id = database.getUserDao().insert(user);
              user.setId(id);
              this.user.postValue(user);
            });
  }

  /**
   * Load maze into fragment from database, or inserts a new one.
   *
   * @param rows    the rows
   * @param columns the columns
   * @param level   the level
   */
  public void loadMaze(int rows, int columns, int level) {
    database.getMazeDao().mazesByDifficulty(rows, columns, level)
        .subscribeOn(Schedulers.io())
        .subscribe(
            (maze) -> {
              this.maze.postValue(maze);
              createAttempt(maze.getId());
            },
            (ex) -> {
              Log.e(ex.getClass().getSimpleName(), ex.getMessage(), ex);
            },
            () -> {
              MazeBuilder mazeBuilder = new MazeBuilder(rows, columns);
              Maze maze = new Maze();
              maze.setGridColumns(columns);
              maze.setGridRows(rows);
              maze.setLevel(level);
              maze.setWalls(mazeBuilder.getCells());
              long id = database.getMazeDao().insert(maze);
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

  /**
   * Records attempt and timeSpent
   *
   * @param duration the duration
   */
  public void recordSuccess(long duration) {
    attempt.setSolved(true);
    attempt.setUserId(user.getValue().getId());
    attempt.setTimeSpent(duration);
    new Thread(() -> database.getAttemptDao().update(attempt)).start();
  }

  /**
   * Set flag for touch/tilt functionality.
   *
   * @return the boolean
   */
  public boolean isTouchEnabled() {
    return touchEnabled;
  }

  /**
   * Sets touch enabled flag. If disabled, it app is tilt only.
   *
   * @param touchEnabled the touch enabled
   */
  public void setTouchEnabled(boolean touchEnabled) {
    this.touchEnabled = touchEnabled;
  }

  /**
   * Gets user from database.
   *
   * @return the user
   */
  public LiveData<User> getUser() {
    return user;
  }

  /**
   * Gets maze from database.
   *
   * @return the maze
   */
  public LiveData<Maze> getMaze() {
    return maze;
  }

  /**
   * Gets attempts from database for a User.
   *
   * @return the attempts
   */
  public LiveData<List<Attempt>> getAttempts() {
    attempts = database.getAttemptDao().getUserAttempts(user.getValue().getId());
    return attempts;
  }
}

