package com.ericadubois.amazeballz.controller;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Chronometer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.ericadubois.amazeballz.R;
import com.ericadubois.amazeballz.model.MazeBuilder;
import com.ericadubois.amazeballz.model.MazeView;

/**
 * The type Maze fragment. This establishes the area where the maze will be drawn. The actual
 * drawing
 */
public class MazeFragment extends Fragment {

  /**
   * The constant ROWS_KEY dictionary argument rows
   */
  public static final String ROWS_KEY = "rows";
  /**
   * The constant COLUMNS_KEY dictionary argument columns
   */
  public static final String COLUMNS_KEY = "columns";
  /**
   * The constant DEFAULT_SIZE of a maze.
   */
  public static final int DEFAULT_SIZE = 10;


  private MazeView mazeView;
  private int rows;
  private int columns;
  private Chronometer chronometer;
  private long pauseOffset;
  private boolean running;


  /**
   * New instance maze fragment.
   *
   * @param rows    the rows
   * @param columns the columns
   * @return the maze fragment
   */
  public static MazeFragment newInstance(int rows, int columns) {
    MazeFragment fragment = new MazeFragment();
    Bundle args = new Bundle();
    args.putInt(ROWS_KEY, rows);
    args.putInt(COLUMNS_KEY, columns);
    fragment.setArguments(args);
    return fragment;
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_maze, container, false);
    mazeView = view.findViewById(R.id.maze_view);
    //  mazeView = view.findViewWithTag(SketchView.class.getSimpleName());
    rows = getArguments().getInt(ROWS_KEY, DEFAULT_SIZE);
    columns = getArguments().getInt(COLUMNS_KEY, DEFAULT_SIZE);
    MazeBuilder mazeBuilder = new MazeBuilder(this.rows, this.columns);
    mazeView.setCells(mazeBuilder.getCells());
    return view;
  }
//
//  public void startChronometer(View v) {
//    if (!running) {
//      chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
//      chronometer.start();
//      running = true;
//    }
//  }
//
//  public void pauseChronometer(View v) {
//    if (running) {
//      chronometer.stop();
//      pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
//      running = false;
//    }
//  }

//TODO add in sensor monitors

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  //TODO implement constructor or new instance method to build maze based on parameters
  //Android Canvas class(draw)
  // , graphics package


}
