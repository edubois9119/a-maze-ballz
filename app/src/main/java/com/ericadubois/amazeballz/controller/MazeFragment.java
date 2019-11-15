package com.ericadubois.amazeballz.controller;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import com.ericadubois.amazeballz.R;
import com.ericadubois.amazeballz.model.MazeBuilder;
import com.ericadubois.amazeballz.model.MazeView;
import com.ericadubois.amazeballz.model.entity.Maze;
import com.ericadubois.amazeballz.viewmodel.MazeViewModel;
import java.sql.Time;
import java.util.Timer;

/**
 * The type Maze fragment. This establishes the area where the maze will be drawn. The actual
 * drawing
 */
public class MazeFragment extends Fragment {

  /**
   * The constant ROWS_KEY dictionary argument rows
   */
  private static final String ROWS_KEY = "rows";
  /**
   * The constant COLUMNS_KEY dictionary argument columns
   */
  private static final String COLUMNS_KEY = "columns";
  /**
   * The constant DEFAULT_SIZE of a maze.
   */
  private static final int DEFAULT_SIZE = 10;

  private static final  String LEVEL_KEY= "level";

  private MazeViewModel viewModel;


  private View view;
  private MazeView mazeView;
  private int rows;
  private int columns;
  private int level;
  private Chronometer stopWatch;
  private long pauseOffset;
  private boolean running;


  /**
   * New instance maze fragment.
   *
   * @param rows    the rows
   * @param columns the columns
   * @return the maze fragment
   */
  public static MazeFragment newInstance( int level, int rows, int columns) {
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
    //  mazeView = view.findViewWithTag(SketchView.class.getSimpleName());
    rows = getArguments().getInt(ROWS_KEY, DEFAULT_SIZE);
    columns = getArguments().getInt(COLUMNS_KEY, DEFAULT_SIZE);
    level= getArguments().getInt(LEVEL_KEY);
    setHasOptionsMenu(true);
    return view;
  }

  public void startChronometer() {
    if (!running) {
      stopWatch.setBase(SystemClock.elapsedRealtime() - pauseOffset);
      stopWatch.start();
      running = true;
    }
  }

  public void pauseChronometer() {
    if (running) {
      stopWatch.stop();
      pauseOffset = SystemClock.elapsedRealtime() - stopWatch.getBase();
      running = false;
    } else {
      stopWatch.start();
      running= true;
    }
  }

//TODO add in sensor monitors

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    inflater.inflate(R.menu.maze_options, menu);
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override
  public void onPrepareOptionsMenu(@NonNull Menu menu) {
    super.onPrepareOptionsMenu(menu);
    //TODO update menu when paused or not, create timer using long Systemmilllis Start when maze
    // fragment starts until maze is paused or completed. If paused, stop timer and restart on resume
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel= ViewModelProviders.of(getActivity()).get(MazeViewModel.class);
//    viewModel= ViewModelProviders.of(this).get(MazeViewModel.class);
    MazeBuilder mazeBuilder = new MazeBuilder(this.rows, this.columns);
    mazeView.setCells(mazeBuilder.getCells());
    Maze maze = new Maze();
    maze.setGridColumns(columns);
    maze.setGridRows(rows);
    maze.setLevel(level);
    maze.setWalls(mazeBuilder.getCells());
    viewModel.saveMaze(maze);
    stopWatch = viewModel.getStopWatch();
    startChronometer();
  }

  public void switchFragment() {
    CompletionFragment fragment = new CompletionFragment();
    FragmentTransaction ft= getFragmentManager().beginTransaction();
    ft.replace(R.id.fragment_container, fragment, fragment.getTag());
    ft.addToBackStack(fragment.getTag());
    ft.commit();
  }
}
