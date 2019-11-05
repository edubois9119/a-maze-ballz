package com.ericadubois.amazeballz.controller;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.ericadubois.amazeballz.R;
import com.ericadubois.amazeballz.model.MazeBuilder;
import com.ericadubois.amazeballz.model.SketchView;

public class MazeFragment extends Fragment {

  public static final String ROWS_KEY = "rows";
  public static final String COLUMNS_KEY = "columns";
  public static final int DEFAULT_SIZE = 10;
  private SketchView mazeView;
  private int rows;
  private int columns;

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
    View view= inflater.inflate(R.layout.fragment_maze, container, false);
    mazeView = view.findViewById(R.id.maze_view);
    rows = getArguments().getInt(ROWS_KEY, DEFAULT_SIZE);
    columns = getArguments().getInt(COLUMNS_KEY, DEFAULT_SIZE);
    MazeBuilder mazeBuilder= new MazeBuilder();
    mazeView.setCells(mazeBuilder.getCells());
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }

  //TODO implement constructor or new instance method to build maze based on parameters
  //Android Canvas class(draw)
  // , graphics package
}
