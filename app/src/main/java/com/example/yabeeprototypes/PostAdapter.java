package com.example.yabeeprototypes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>
{
    private ArrayList<Post> posts;
    private PostClicked activity; //made private previously undeclared

    public interface PostClicked
    {
        void onPostClicked(int index);
    }

    public PostAdapter (Context context, ArrayList<Post> list)
    {
        posts = list;
        activity = (PostClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView ivPostImage;
        TextView tvPostTitle, tvPostCurrentBid, tvPostTimeLeft, tvPostLeader;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvPostTitle = itemView.findViewById(R.id.tvPostTitle);
            tvPostCurrentBid = itemView.findViewById(R.id.tvDescriptionCurrentBid);
            tvPostTimeLeft = itemView.findViewById(R.id.tvPostTimeLeft);
            tvPostLeader = itemView.findViewById(R.id.tvPostLeader);
            ivPostImage = itemView.findViewById(R.id.ivPostImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    activity.onPostClicked(posts.indexOf((Post) v.getTag()));
                }
            });
        }
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_posts, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position)
    {
        holder.itemView.setTag(posts.get(position));

        holder.tvPostTitle.setText(posts.get(position).getTitle());
        //holder.tvPostCurrentBid.setText(Double.toString(posts.get(position).getLowestBid().price));
        holder.tvPostCurrentBid.setText(String.format(Locale.US,"%.2f",posts.get(position).getLowestBid().price));
        holder.tvPostTimeLeft.setText(posts.get(position).getAuctionTimer());
        // holder.tvPostLeader @TODO
        // holder.ivPostImage.setText(posts.get(position).getImageUrl()); @ TODO


    }

    @Override
    public int getItemCount()
    {
        return posts.size();
    }
}
