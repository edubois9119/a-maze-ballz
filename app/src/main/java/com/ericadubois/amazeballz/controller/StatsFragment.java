/**
 * This work is Copyright 2019, Erica DuBois. ALL RIGHTS RESERVED.
 */

package com.ericadubois.amazeballz.controller;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import com.ericadubois.amazeballz.R;
import com.ericadubois.amazeballz.model.entity.Attempt;
import com.ericadubois.amazeballz.viewmodel.MazeViewModel;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatsFragment extends Fragment {

  private RecyclerView recyclerView;
//  private String[] items = {"item 0", "item 1", "item 2", "item 3", "item 4", "item 5", "item 6"};
  private MazeViewModel viewModel;
  private List<Attempt> attempts;
  private StatsAdapter statsAdapter= new StatsAdapter(getContext(), new ArrayList<>());

  /**
   * Instantiates a new Stats fragment.
   */
  public StatsFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_stats, container, false);
    recyclerView = view.findViewById(R.id.recycler_view);
    // Inflate the layout for this fragment
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    viewModel = ViewModelProviders.of(getActivity()).get(MazeViewModel.class);
//    viewModel = ViewModelProviders.of(this).get(MazeViewModel.class);
    viewModel.getAttempts().observe(this, (attempts) -> {
      this.attempts = attempts;
      StatsAdapter adapter = new StatsAdapter(getContext(), attempts);
      recyclerView.setAdapter(adapter);
    });
  }

  private void observeAttempts(){
    viewModel.getAttempts().observe(this, new Observer<List<Attempt>>() {
      @Override
      public void onChanged(List<Attempt> attempts) {
        statsAdapter.updateAttempts(attempts);
      }
    });
  }

}
