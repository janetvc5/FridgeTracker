package com.example.fridge_tracker;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * An adapter used by the RecyclerView elements in GroceryListActivity and MainActivity
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        private ArrayList<FoodItem> mDataset;

    /**
     * Provide a reference to the views for each data item (each data item is just a string in this case). You provide access to all the views for a data item in a view holder.
     */
        public static class MyViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView textView;
            public MyViewHolder(TextView v) {
                super(v);
                textView = v;
            }
        }

    /**
     * Provide a suitable constructor (depends on the kind of dataset)
     *
     * @param myDataset
     */

        public MyAdapter(ArrayList<FoodItem> myDataset) {
            mDataset = myDataset;
        }

    /**
     * Create new views to be invoked by the layout manager
     * @param parent
     * @param viewType
     * @return
     */
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            TextView v = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_view_row, parent, false);
            MyViewHolder vh = new MyViewHolder(v);
            return vh;
        }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     * @param holder
     * @param position
     */

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.textView.setText(mDataset.get(position).toString());

        }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     * @return
     */
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
}

