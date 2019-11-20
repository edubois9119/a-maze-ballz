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
import androidx.recyclerview.widget.RecyclerView;
import com.ericadubois.amazeballz.R;
import com.ericadubois.amazeballz.controller.StatsAdapter.Item;
import com.ericadubois.amazeballz.model.entity.Attempt;
import java.util.List;

/**
 * Stats adapter class, used for showing attempts in recyclerView.
 */
public class StatsAdapter extends RecyclerView.Adapter<Item> {

  private Context context;
  private List<Attempt> attempts;

  /**
   * Instantiates a new Stats adapter.
   *
   * @param context  the context
   * @param attempts the attempts
   */
  StatsAdapter(Context context, List<Attempt> attempts) {
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

  @Override
  public void onBindViewHolder(@NonNull Item holder, int position) {
    holder.textView.setText(attempts.get(position).statInfo());
  }

  @Override
  public int getItemCount() {
    return attempts.size();
  }

  /**
   * nested static class for viewHolder.
   */
  static class Item extends RecyclerView.ViewHolder {

    /**
     * TextView object
     */
    TextView textView;

    /**
     * Instantiates a new textItem in the view.
     *
     * @param itemView the item view
     */
    public Item(@NonNull View itemView) {
      super(itemView);
      textView = (TextView) itemView;
    }
  }
}
