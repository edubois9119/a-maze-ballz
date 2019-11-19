package com.ericadubois.amazeballz.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.ericadubois.amazeballz.R;

public class StatsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  Context context;
  String[] items;

  public StatsAdapter(StatsFragment statsFragment, String [] items){
    this.context = context;
    this.items = items;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);
    View stats = inflater.inflate(R.layout.fragment_stats, parent, false);
    Item item = new Item(stats);
    return item;
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    ((Item)holder).textView.setText(items[position]);

  }

  @Override
  public int getItemCount() {
    return items.length;
  }

  public class Item extends RecyclerView.ViewHolder {
    TextView textView;
    public Item(@NonNull View itemView) {
      super(itemView);
      textView=itemView.findViewById(R.id.items);
    }
  }
}
