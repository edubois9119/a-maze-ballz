/**
 * This work is Copyright 2019, Erica DuBois. ALL RIGHTS RESERVED.
 */

package com.ericadubois.amazeballz.controller;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.ericadubois.amazeballz.R;

/**
 * Necessary for shared preferences.
 */
public class SettingsActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.settings_activity);
  }

}