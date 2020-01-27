/**
 * This work is Copyright 2019, Erica DuBois. ALL RIGHTS RESERVED.
 */

package com.ericadubois.amazeballz.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.ericadubois.amazeballz.R;

/**
 * This fragment displays when a user has completed a maze. Allows user to go back to level select
 * fragment to choose another maze.
 */
public class CompletionFragment extends Fragment implements View.OnClickListener {
  private Double timestamp = 0.0;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_completion, container, false);
    view.getBackground().setAlpha(250);
    view.findViewById(R.id.next_level).setOnClickListener(this);
    view.findViewById(R.id.stats).setOnClickListener(this);
    TextView ts = view.findViewById(R.id.timestamp);
    int minutes= (int)(timestamp / 60);
    int seconds= (int)(timestamp % 60);
    ts.setText(String.format("%02d:%02d", minutes, seconds));
    return view;
  }

  @Override
  public void onClick(View v) {
    String buttonTag = v.getTag().toString();
    if (buttonTag.equals("level")) {
      LevelSelectFragment levelSelectFragment = new LevelSelectFragment();
      FragmentTransaction ft = getFragmentManager().beginTransaction();
      ft.addToBackStack(LevelSelectFragment.class.getSimpleName());
      ft.replace(R.id.fragment_container, levelSelectFragment).commit();
    } else {
      StatsFragment statsFragment = new StatsFragment();
      FragmentTransaction fragTrans = getFragmentManager().beginTransaction();
      fragTrans.addToBackStack(LevelSelectFragment.class.getSimpleName());
      fragTrans.replace(R.id.fragment_container, statsFragment).commit();
    }
  }

  /**
   * Sets timestamp for displaying timeSpent in seconds
   *
   * @param timestamp the timestamp
   */
  public void setTimestamp(Double timestamp) {
    this.timestamp = timestamp;
  }
}
