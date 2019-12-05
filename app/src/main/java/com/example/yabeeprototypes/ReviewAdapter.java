package com.example.yabeeprototypes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DecimalFormat;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>
{
    private List<Review> listData;
    private int containerId;

    public ReviewAdapter(List<Review> data, int containerId)
    {
        this.listData = data;
        this.containerId = containerId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(containerId, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Review review = listData.get(position);
        if (review != null) {
            String bid = "";
            holder.title.setText(review.getTitle());
            holder.description.setText(review.getDescription());
            holder.rating.setRating(review.getRating());
        }
        else
        {
            String invalid = "null";
            holder.title.setText(invalid);
            holder.description.setText(invalid);
            holder.rating.setRating(0);
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView description;
        public RatingBar rating;
        public ViewHolder(View itemView) {
            super(itemView);
            this.title = ((TextView) itemView.findViewById(R.id.display_review_title));
            this.description = ((TextView) itemView.findViewById(R.id.display_review_desc));
            this.rating = ((RatingBar) itemView.findViewById(R.id.display_review_rating));
        }
    }


}




