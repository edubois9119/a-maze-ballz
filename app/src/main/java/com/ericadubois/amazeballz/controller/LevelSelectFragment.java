package com.ericadubois.amazeballz.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.ericadubois.amazeballz.R;

public class LevelSelectFragment extends Fragment implements View.OnClickListener {
  private Button buttonOne;

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
    int level = (Integer) v.getTag();
    // TODO Load maze fragment with parameters for level
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
  }


}
