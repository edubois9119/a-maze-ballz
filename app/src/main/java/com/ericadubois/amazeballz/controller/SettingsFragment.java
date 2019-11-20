/**
 * This work is Copyright 2019, Erica DuBois. ALL RIGHTS RESERVED.
 */

package com.ericadubois.amazeballz.controller;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;
import com.ericadubois.amazeballz.R;

/**
 * Necessary for shared preferences.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    setPreferencesFromResource(R.xml.root_preferences, rootKey);
  }
}

