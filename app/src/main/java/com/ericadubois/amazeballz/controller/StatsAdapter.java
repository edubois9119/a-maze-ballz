/**
 * This work is Copyright 2019, Erica DuBois. ALL RIGHTS RESERVED.
 */

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
import com.ericadubois.amazeballz.controller.StatsAdapter.Item;
import com.ericadubois.amazeballz.model.entity.Attempt;
import java.util.ArrayList;
import java.util.List;

public class StatsAdapter extends RecyclerView.Adapter<Item> {
  Context context;
//  String[] items;
  List<Attempt> attempts = new ArrayList<>();

//  public StatsAdapter(StatsFragment statsFragment, String [] items){
//    this.context = statsFragment.getContext();
//    this.items = items;
//  }


  public StatsAdapter(Context context, List<Attempt> attempts){
    this.context = context;
    this.attempts = attempts;
  }

  @NonNull
  @Override
  public Item onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(context);
    View stats = inflater.inflate(R.layout.item_stats, parent, false);
    Item item = new Item(stats);
    return item;
  }

  public void updateAttempts(List<Attempt> attempts){
    this.attempts.clear();
    this.attempts.addAll(attempts);
    notifyDataSetChanged();
  }
//  @Override
//  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//    ((Item)holder).textView.setText(items[position]);
//  }

  @Override
  public void onBindViewHolder(@NonNull Item holder, int position) {
    holder.textView.setText(attempts.get(position).statInfo());
  }

//
//  @Override
//  public int getItemCount() {
//    return items.length;
//  }

  @Override
  public int getItemCount() {
    return attempts.size();
  }

  static class Item extends RecyclerView.ViewHolder {
    TextView textView;
    public Item(@NonNull View itemView) {
      super(itemView);
      textView= (TextView) itemView;
    }
  }
}
