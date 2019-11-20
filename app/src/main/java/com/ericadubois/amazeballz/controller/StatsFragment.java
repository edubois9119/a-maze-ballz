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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import com.ericadubois.amazeballz.R;
import com.ericadubois.amazeballz.model.entity.Attempt;
import com.ericadubois.amazeballz.viewmodel.MazeViewModel;
import java.util.List;

/**
 * The fragment that displays the user stats.
 */
public class StatsFragment extends Fragment {

  private RecyclerView recyclerView;
  private MazeViewModel viewModel;
  private List<Attempt> attempts;

  /**
   * Instantiates a new Stats fragment.
   */
  public StatsFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_stats, container, false);
    recyclerView = view.findViewById(R.id.recycler_view);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    viewModel = ViewModelProviders.of(getActivity()).get(MazeViewModel.class);
    viewModel.getAttempts().observe(this, (attempts) -> {
      this.attempts = attempts;
      StatsAdapter adapter = new StatsAdapter(getContext(), attempts);
      recyclerView.setAdapter(adapter);
    });
  }
}
