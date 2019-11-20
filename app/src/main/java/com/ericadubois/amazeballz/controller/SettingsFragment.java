/**
 * This work is Copyright 2019, Erica DuBois. ALL RIGHTS RESERVED.
 */

package com.ericadubois.amazeballz.controller;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import androidx.preference.PreferenceFragmentCompat;
import com.ericadubois.amazeballz.R;

/**
 * The type Settings fragment.
 */
public class SettingsFragment extends PreferenceFragmentCompat {


  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    setPreferencesFromResource(R.xml.root_preferences, rootKey);
  }
}

