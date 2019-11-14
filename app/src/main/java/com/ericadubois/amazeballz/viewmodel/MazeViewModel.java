package com.ericadubois.amazeballz.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import com.ericadubois.amazeballz.model.MazeBuilder;
import com.ericadubois.amazeballz.model.entity.Maze;
import com.ericadubois.amazeballz.service.AMazeBallzDatabase;

public class MazeViewModel extends AndroidViewModel implements LifecycleObserver {

  private MazeBuilder mazeBuilder;

  private final AMazeBallzDatabase database;

  public MazeViewModel(@NonNull Application application) {
    super(application);
    database= AMazeBallzDatabase.getInstance();

   //  mazeBuilder = new MazeBuilder();
  //   mazeBuilder.printMaze();
  }
  public void saveMaze(Maze maze){
    new Thread(()->database.getMazeDao().insert(maze)).start();
  }

}

