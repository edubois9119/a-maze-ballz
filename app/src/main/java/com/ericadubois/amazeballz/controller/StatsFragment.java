package com.ericadubois.amazeballz.controller;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import com.ericadubois.amazeballz.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatsFragment extends Fragment  {

  private RecyclerView recyclerView;
  private String [] items= {"item 0", "item 1", "item 2", "item 3", "item 4", "item 5", "item 6"};


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
    recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    recyclerView.setAdapter(new StatsAdapter(this, items));
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_stats, container, false);
  }

}
