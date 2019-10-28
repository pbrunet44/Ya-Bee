package com.example.yabeeprototypes;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListingAdapter extends RecyclerView.Adapter
{
    private ArrayList<Listing> listing;

    public ListingAdapter(Context context, ArrayList<Listing> list)
    {
        listing = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        //give id names in (for instance activity_items_selling.xml) layout to items to be defined
        //add items definition for recyclerview

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
