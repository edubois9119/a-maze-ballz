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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import com.ericadubois.amazeballz.R;
import com.ericadubois.amazeballz.model.BallView;
import com.ericadubois.amazeballz.model.Direction;
import com.ericadubois.amazeballz.model.MazeBuilder;
import com.ericadubois.amazeballz.model.MazeView;
import com.ericadubois.amazeballz.model.entity.Maze;
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
  private int rows;
  private int columns;
  private int level;

  private Chronometer mazeTimer;
  private long startTime;
  private long countUp;
  private long pauseOffset;
  private boolean running;

  private SensorManager manager;
  private Sensor accelerometer;


  /**
   * New instance maze fragment. This new instance bundles the necessary items for storing a maze to 
   * the data base.
   *
   * @param level   the level
   * @param rows    the rows
   * @param columns the columns
   * @return the maze fragment
   */
  public static MazeFragment newInstance(int level, int rows, int columns) {
    MazeFragment fragment = new MazeFragment();
    Bundle args = new Bundle();
    args.putInt(ROWS_KEY, rows);
    args.putInt(COLUMNS_KEY, columns);
    args.putInt(LEVEL_KEY, level);
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
//    ballView.setMazeFragment(this);
    mazeView.setBallView(ballView);

    rows = getArguments().getInt(ROWS_KEY, DEFAULT_SIZE);
    columns = getArguments().getInt(COLUMNS_KEY, DEFAULT_SIZE);
    level = getArguments().getInt(LEVEL_KEY);
    setHasOptionsMenu(true);
    manager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
    accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    //set up mazeTimer
//    setupMazeTimer();
    return view;
  }

  private void setupMazeTimer() {
//    mazeTimer = view.findViewById(R.id.chrono);
//    startTime = SystemClock.elapsedRealtime();
//    mazeTimer.setOnChronometerTickListener(arg0 -> {
////      countUp = (SystemClock.elapsedRealtime() - arg0.getBase()) / 1000;
////      String asText = (countUp / 60) + ":" + (countUp % 60);
//    });

    toggleChronometer();

  }

  /**
   * Starts the chronometer that measures time taken to complete a maze.
   */
  public void startChronometer() {
    if (!running) {
      mazeTimer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
      mazeTimer.start();
      running = true;
    }
  }

  /**
   * This method toggles between the pause and resume functionality on the chronometer.
   */
  public void toggleChronometer() {
    if (running) {
      mazeTimer.stop();
      pauseOffset = SystemClock.elapsedRealtime() - mazeTimer.getBase();
      running = false;
    } else {
      if (pauseOffset == 0) {
        mazeTimer.setBase(SystemClock.elapsedRealtime());
      }
      mazeTimer.start();
      running = true;
    }
  }
  


  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    inflater.inflate(R.menu.maze_options, menu);
    MenuItem item = menu.findItem(R.id.chrono);
    View view = item.getActionView();
    mazeTimer = view.findViewById(R.id.chronometer);
    setupMazeTimer();
    super.onCreateOptionsMenu(menu, inflater);
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
//        pauseChronometer();
//        getActivity().invalidateOptionsMenu();
//        break;
      case R.id.resume:
//       toggleChronometer();
        getActivity().invalidateOptionsMenu();
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
//    viewModel= ViewModelProviders.of(this).get(MazeViewModel.class);
    MazeBuilder mazeBuilder = new MazeBuilder(this.rows, this.columns);
    mazeView.setCells(mazeBuilder.getCells());
    Maze maze = new Maze();
    maze.setGridColumns(columns);
    maze.setGridRows(rows);
    maze.setLevel(level);
    maze.setWalls(mazeBuilder.getCells());
    viewModel.saveMaze(maze);
//    mazeTimer = viewModel.getStopWatch();
//    startChronometer();
  }

  /**
   * This method allows for the switch between the maze fragment and the completion fragment. The 
   * completion fragment is the fragment displayed when the user completes the maze. 
   */
  public void switchFragment() {
    CompletionFragment fragment = new CompletionFragment();
    FragmentTransaction ft = getFragmentManager().beginTransaction();
    ft.replace(R.id.fragment_container, fragment, fragment.getTag());
    ft.addToBackStack(fragment.getTag());
    ft.commit();
  }

  @Override
  public void onSensorChanged(SensorEvent event) {
    mazeView.checkWin();
    float x = event.values[0];
    float y = event.values[1];
    if (Math.abs(x) > Math.abs(y)) {
//      if (x < 0) {
      if (x < -3) {
        mazeView.moveBall(Direction.EAST);
        System.out.println("You tilted the device right");
      }
      if (x > 3) {
        mazeView.moveBall(Direction.WEST);
        System.out.println("You tilted the device left");
      }
    } else {
      if (y < -3) {
        mazeView.moveBall(Direction.NORTH);
        System.out.println("You tilted the device up");
      }
      if (y > 3) {
        mazeView.moveBall(Direction.SOUTH);
        System.out.println("You tilted the device down");
      }
    }
    if (x > (-2) && x < (2) && y > (-2) && y < (2)) {
      System.out.println("Not tilting device");
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

}
