package com.ericadubois.amazeballz.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import com.ericadubois.amazeballz.R;
import com.ericadubois.amazeballz.model.MazeBuilder;
import com.ericadubois.amazeballz.model.entity.Maze;
import com.ericadubois.amazeballz.service.GoogleSignInService;
import com.ericadubois.amazeballz.viewmodel.MazeViewModel;

public class MainActivity extends AppCompatActivity {
//    implements SensorEventListener {

  //
  private View view;
  private MazeViewModel mazeViewModel;
  private SensorManager sensorManager;
  private Sensor sensor;
  private Sensor sensor2;
  private Chronometer stopWatch;
  long startTime;
  long countUp;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mazeViewModel = ViewModelProviders.of(this).get(MazeViewModel.class);
    getLifecycle().addObserver(mazeViewModel);

    sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//    sensor2 = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    view = this.getWindow().getDecorView();
//    view.setBackgroundColor(R.color.colorPrimaryDark);
    view.setBackgroundColor(Color.BLACK);

//    MazeFragment mazeFrag= new MazeFragment();
//    addFragment(mazeFrag, true);

    LevelSelectFragment levelFrag = new LevelSelectFragment();
    addFragment(levelFrag, true);

    stopWatch = findViewById(R.id.chrono);
    startTime = SystemClock.elapsedRealtime();
    stopWatch.setOnChronometerTickListener(arg0 -> {
      countUp = (SystemClock.elapsedRealtime() - arg0.getBase()) / 1000;
      String asText = (countUp / 60) + ":" + (countUp % 60);
    });
//    stopWatch.start();
    mazeViewModel.setStopWatch(stopWatch);
  }

  public Chronometer getStopWatch() {
    return stopWatch;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.options, menu);
    return true | super.onCreateOptionsMenu(menu);
    //full evaluation boolean OR, lets super class do what it needs to do after returning true
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    boolean handled = true;
    switch (item
        .getItemId()) { //all primitive types except float, long double, wrappers for same times, enums, strings
      case R.id.sign_out:
        signOut();
        break;
      default:
        handled = super.onOptionsItemSelected(item);
    }
    return handled;
  }

  private void signOut() {
    GoogleSignInService.getInstance().signOut()
        .addOnCompleteListener((task) -> {                           //success or fail
          Intent intent = new Intent(this, LoginActivity.class);
          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
          startActivity(intent);
        });
  }

  private void addFragment(Fragment fragment, boolean useStack) {
    FragmentManager manager = getSupportFragmentManager();
    String tag = fragment.getClass().getSimpleName();
    if (manager.findFragmentByTag(tag) != null) {
      manager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    FragmentTransaction transaction = manager.beginTransaction();
    transaction.add(R.id.fragment_container, fragment, tag);
    if (useStack) {
      transaction.addToBackStack(tag);
    }
    transaction.commit();

  }
//
//  @Override
//  protected void onResume() {
//    super.onResume();
//    int SENSOR_RATE = 100000000;
//    sensorManager.registerListener(this,sensor, SENSOR_RATE);
////    sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
//  }
//
//  @Override
//  protected void onPause() {
//    super.onPause();
//    sensorManager.unregisterListener(this);
//  }
//
//  @Override
//  public void onSensorChanged(SensorEvent event) {
//    float x = event.values[0];
//    float y = event.values[1];
//    if (Math.abs(x) > Math.abs(y)) {
////      if (x < 0) {
//      if (x < -3) {
//        System.out.println("You tilted the device right");
//      }
//      if (x > 3) {
//        System.out.println("You tilted the device left");
//      }
//    } else {
//      if (y < -3) {
//        System.out.println("You tilted the device up");
//      }
//      if (y > 3) {
//        System.out.println("You tilted the device down");
//      }
//    }
////    if (x > (-2) && x < (2) && y > (-2) && y < (2)) {
////      System.out.println("Not tilting device");
////    }
//  }
//
//  @Override
//  public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//  }


}

