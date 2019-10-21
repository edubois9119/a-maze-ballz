package com.ericadubois.amazeballz.viewmodel;

import android.app.Application;
import com.ericadubois.amazeballz.service.AMazeBallzDatabase;
import com.facebook.stetho.Stetho;

public class AMazeBallzApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    AMazeBallzDatabase.setApplicationContext(this);
    final AMazeBallzDatabase database = AMazeBallzDatabase.getInstance();
    new Thread(new Runnable() {
      @Override
      public void run() {
        database.getMazeDao().delete();
      }
    }).start();
  }

}
