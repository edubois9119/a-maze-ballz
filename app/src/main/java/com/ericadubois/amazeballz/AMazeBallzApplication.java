/**
 * This work is Copyright 2019, Erica DuBois. ALL RIGHTS RESERVED.
 */

package com.ericadubois.amazeballz;

import android.app.Application;
import com.ericadubois.amazeballz.service.AMazeBallzDatabase;
import com.ericadubois.amazeballz.service.GoogleSignInService;
import com.facebook.stetho.Stetho;

/**
 *  AMazeBallzApplication is the entry point to the game.
 */
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
  }
}
