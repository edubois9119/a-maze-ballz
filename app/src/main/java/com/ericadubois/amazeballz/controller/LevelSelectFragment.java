/**
 * This work is Copyright 2019, Erica DuBois. ALL RIGHTS RESERVED.
 */

package com.ericadubois.amazeballz.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.ericadubois.amazeballz.R;

/**
 * This fragment displays the level options for the user to choose from. The higher the number
 * selected, the harder the maze will be.
 */
public class LevelSelectFragment extends Fragment implements View.OnClickListener {

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view= inflater.inflate(R.layout.fragment_level_select, container, false);
    view.findViewById(R.id.button_one).setOnClickListener(this);
    view.findViewById(R.id.button_two).setOnClickListener(this);
    view.findViewById(R.id.button_three).setOnClickListener(this);
    view.findViewById(R.id.button_four).setOnClickListener(this);
    view.findViewById(R.id.button_five).setOnClickListener(this);
    view.findViewById(R.id.button_six).setOnClickListener(this);
    view.findViewById(R.id.button_seven).setOnClickListener(this);
    view.findViewById(R.id.button_eight).setOnClickListener(this);
    view.findViewById(R.id.button_nine).setOnClickListener(this);
    view.findViewById(R.id.button_ten).setOnClickListener(this);
    view.findViewById(R.id.button_eleven).setOnClickListener(this);
    view.findViewById(R.id.button_twelve).setOnClickListener(this);
    view.findViewById(R.id.button_thirteen).setOnClickListener(this);
    view.findViewById(R.id.button_fourteen).setOnClickListener(this);
    view.findViewById(R.id.button_fifteen).setOnClickListener(this);
    return view;
  }

  @Override
  public void onClick(View v) {
    int level = Integer.parseInt(v.getTag().toString());
    int sizeColumns = level + 3;
    int sizeRows= (int)(sizeColumns * 4/3);
    System.out.println("v.getTag='" + v.getTag() +"', level = " + level);

    MazeFragment maze = MazeFragment.newInstance(level, sizeRows, sizeColumns);
    FragmentTransaction ft= getFragmentManager().beginTransaction();
    ft.addToBackStack(MazeFragment.class.getSimpleName());
    ft.replace(R.id.fragment_container, maze).commit();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }
}
