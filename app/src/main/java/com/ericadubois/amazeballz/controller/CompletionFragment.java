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

public class CompletionFragment extends Fragment implements View.OnClickListener {

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_completion, container, false);
    view.findViewById(R.id.next_level).setOnClickListener(this);
    return view;
  }

  @Override
  public void onClick(View v) {
    LevelSelectFragment levelSelectFragment=  new LevelSelectFragment();
    FragmentTransaction ft= getFragmentManager().beginTransaction();
    ft.addToBackStack(LevelSelectFragment.class.getSimpleName());
    ft.replace(R.id.fragment_container, levelSelectFragment).commit();
  }
}
