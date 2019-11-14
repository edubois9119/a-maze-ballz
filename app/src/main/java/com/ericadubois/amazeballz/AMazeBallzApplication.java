package com.ericadubois.amazeballz;

import android.app.Application;
import com.ericadubois.amazeballz.model.entity.Attempt;
import com.ericadubois.amazeballz.service.AMazeBallzDatabase;
import com.ericadubois.amazeballz.service.GoogleSignInService;
import com.facebook.stetho.Stetho;

public class AMazeBallzApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    GoogleSignInService.setApplicationContext(this);
    Stetho.initializeWithDefaults(this);
    AMazeBallzDatabase.setApplicationContext(this);
    final AMazeBallzDatabase database = AMazeBallzDatabase.getInstance();
    new Thread(new Runnable() {
      @Override
      public void run() {
        database.getMazeDao().delete();
      }
    }).start();
//    Attempt temp = new Attempt();
//    database.getAttemptDao().insert(temp);
  }

}
