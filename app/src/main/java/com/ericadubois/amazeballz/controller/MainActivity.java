/**
 * This work is Copyright 2019, Erica DuBois. ALL RIGHTS RESERVED.
 */

package com.ericadubois.amazeballz.controller;


import android.content.ClipData.Item;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;
import com.ericadubois.amazeballz.R;
import com.ericadubois.amazeballz.service.GoogleSignInService;
import com.ericadubois.amazeballz.viewmodel.MazeViewModel;

/**
 * The Main activity is the controller for the UI.
 */
public class MainActivity extends AppCompatActivity
    implements SharedPreferences.OnSharedPreferenceChangeListener{
  private View view;
  private MazeViewModel mazeViewModel;
  private SharedPreferences preferences;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mazeViewModel = ViewModelProviders.of(this).get(MazeViewModel.class);
    getLifecycle().addObserver(mazeViewModel);

    Toolbar toolbar= findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);


    view = this.getWindow().getDecorView();
    view.setBackgroundColor(Color.BLACK);

    preferences= PreferenceManager.getDefaultSharedPreferences(this);
    preferences.registerOnSharedPreferenceChangeListener(this);
    mazeViewModel.setTouchEnabled(preferences.getBoolean("touch_movement", false));

    LevelSelectFragment levelFrag = new LevelSelectFragment();
    addFragment(levelFrag, true);
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
    Intent intent;
    switch (item.getItemId()) { //all primitive types except float, long double, wrappers for same times, enums, strings
      case R.id.sign_out:
        signOut();
        break;
      case R.id.preferences:
        intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
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

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    mazeViewModel.setTouchEnabled(preferences.getBoolean("touch_movement", false));
  }

}

