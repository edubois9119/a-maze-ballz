package com.ericadubois.amazeballz.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import com.ericadubois.amazeballz.model.MazeBuilder;

public class MazeViewModel extends AndroidViewModel implements LifecycleObserver {

  private MazeBuilder mazeBuilder;

  public MazeViewModel(@NonNull Application application) {
    super(application);
   //  mazeBuilder = new MazeBuilder();
  //   mazeBuilder.printMaze();
  }
}

