/**
 * This work is Copyright 2019, Erica DuBois. ALL RIGHTS RESERVED.
 */

package com.ericadubois.amazeballz.controller;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import com.ericadubois.amazeballz.R;
import com.ericadubois.amazeballz.model.entity.Attempt;
import com.ericadubois.amazeballz.model.entity.Maze;
import com.ericadubois.amazeballz.model.entity.User;
import com.ericadubois.amazeballz.pojos.BallView;
import com.ericadubois.amazeballz.pojos.Direction;
import com.ericadubois.amazeballz.pojos.MazeView;
import com.ericadubois.amazeballz.viewmodel.MazeViewModel;

/**
 * This fragment holds the maze view and the ball view.
 */
public class MazeFragment extends Fragment implements SensorEventListener {

  private static final String ROWS_KEY = "rows";
  private static final String COLUMNS_KEY = "columns";
  private static final int DEFAULT_SIZE = 10;
  private static final String LEVEL_KEY = "level";
  private MazeViewModel viewModel;

  private View view;
  private MazeView mazeView;
  private BallView ballView;
  private Double timestamp;
  private int rows;
  private int columns;
  private int level;

  private Chronometer mazeTimer;
  private long pauseOffset;
  private boolean running;

  private SensorManager manager;
  private Sensor accelerometer;
  private boolean mazeNeeded;
  private User user;
  private Maze maze;

  /**
   * New instance maze fragment.
   *
   * @param level   the level
   * @param rows    the rows
   * @param columns the columns
   * @return the maze fragment
   */
  static MazeFragment newInstance(int level, int rows, int columns) {
    MazeFragment fragment = new MazeFragment();
    Bundle args = new Bundle();
    args.putInt(ROWS_KEY, rows);
    args.putInt(COLUMNS_KEY, columns);
    args.putInt(LEVEL_KEY, level);
    args.putBoolean("maze_needed", true);
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_maze, container, false);
    mazeView = view.findViewById(R.id.maze_view);
    mazeView.setMazeFragment(this);
    ballView = view.findViewById(R.id.ball_view);
    timestamp = 0.0;

    mazeView.setBallView(ballView);

    rows = getArguments().getInt(ROWS_KEY, DEFAULT_SIZE);
    columns = getArguments().getInt(COLUMNS_KEY, DEFAULT_SIZE);
    level = getArguments().getInt(LEVEL_KEY);
    mazeNeeded = getArguments().getBoolean("maze_needed", false);
    getArguments().remove("maze_needed");
    setHasOptionsMenu(true);
    manager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
    accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

//    view.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.woodplanks));

    return view;
  }

  /**
   * This method toggles between the pause and resume functionality on the chronometer.
   */
  public void pauseTimer() {
    if (running) {
      pauseOffset = SystemClock.elapsedRealtime() - mazeTimer.getBase();
      mazeTimer.stop();
      running = false;
      getActivity().invalidateOptionsMenu();
    }
  }

  private void resumeTimer() {
    if (!running) {
      mazeTimer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
      mazeTimer.start();
      running = true;
      getActivity().invalidateOptionsMenu();
    }
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.maze_options, menu);
    MenuItem item = menu.findItem(R.id.chrono);
    ViewGroup layout = (ViewGroup) item.getActionView();
    if (mazeTimer == null) {
      mazeTimer = layout.findViewById(R.id.chronometer);
      resumeTimer(); // Assumes you want to start running as soon as activity is loaded.
    } else {
      layout.removeView(
          layout.findViewById(R.id.chronometer)); // Remove inflated chronometer from new layout.
      ((ViewGroup) mazeTimer.getParent())
          .removeView(mazeTimer); // Detach previously loaded chronometer from its previous layout.
      layout.addView(mazeTimer); // Attach previously loaded chronometer to new layout.
    }
  }

  @Override
  public void onPrepareOptionsMenu(@NonNull Menu menu) {
    if (running) {
      menu.findItem(R.id.pause).setVisible(true);
      menu.findItem(R.id.resume).setVisible(false);
    } else {
      menu.findItem(R.id.pause).setVisible(false);
      menu.findItem(R.id.resume).setVisible(true);
    }
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    boolean handled = true;
    switch (item.getItemId()) {
      case R.id.pause:
        pauseTimer();
        break;
      case R.id.resume:
        resumeTimer();
        break;
      default:
        handled = super.onOptionsItemSelected(item);
    }
    return handled;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel = ViewModelProviders.of(getActivity()).get(MazeViewModel.class);
    viewModel.getUser().observe(this, (user) -> {
      this.user = user;
      if (user == null) {
        viewModel.loadUser();
      }
    });
    viewModel.getMaze().observe(this, (maze) -> {
      this.maze = maze;
      if (!mazeNeeded && maze != null) {
        mazeView.setCells(maze.getWalls());
      } else {
        mazeNeeded = false;
        viewModel.loadMaze(rows, columns, level);
      }
    });
  }

  /**
   * Records timeSpent for a successful maze attempt.
   */
  public void recordSuccess() {
    viewModel.recordSuccess(SystemClock.elapsedRealtime() - mazeTimer.getBase());
    timestamp = (Double) ((SystemClock.elapsedRealtime() - mazeTimer.getBase()) / 1000.0);
  }

  /**
   * This method allows for the switch between the maze fragment and the completion fragment. The
   * completion fragment is the fragment displayed when the user completes the maze.
   */
  public void switchFragment() {
    CompletionFragment fragment = new CompletionFragment();
    fragment.setTimestamp(timestamp);
    FragmentTransaction ft = getFragmentManager().beginTransaction();
    ft.replace(R.id.fragment_container, fragment, fragment.getTag());
    ft.addToBackStack(fragment.getTag());
    ft.commit();
  }

  /**
   * Gets view model of this MazeFragment.
   *
   * @return the view model
   */
  public MazeViewModel getViewModel() {
    return viewModel;
  }

  @Override
  public void onSensorChanged(SensorEvent event) {
    if (getViewModel().isTouchEnabled() || !running) {
      return;
    }
    if (maze != null) {
      float x = event.values[0];
      float y = event.values[1];
      if (Math.abs(x) > Math.abs(y)) {
//      if (x < 0) {
        if (x < -3) {
          mazeView.moveBall(Direction.EAST);
        }
        if (x > 3) {
          mazeView.moveBall(Direction.WEST);
        }
      } else {
        if (y < -3) {
          mazeView.moveBall(Direction.NORTH);
        }
        if (y > 3) {
          mazeView.moveBall(Direction.SOUTH);
        }
      }
      if (x > (-2) && x < (2) && y > (-2) && y < (2)) {
      }
    }
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {
  }

  @Override
  public void onPause() {
    super.onPause();
    manager.unregisterListener(this);
  }

  @Override
  public void onResume() {
    super.onResume();
    manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
  }

  /**
   * Flag that specifies whether or not the game is paused.
   *
   * @return the boolean
   */
  public boolean isRunning() {
    return running;
  }

  /**
   * Gets timestamp of the time spent on the maze.
   *
   * @return the timestamp
   */
  public Double getTimestamp() {
    return timestamp;
  }
}
