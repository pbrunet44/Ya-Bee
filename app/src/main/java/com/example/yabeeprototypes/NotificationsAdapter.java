package com.example.yabeeprototypes;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder>
{
    private List<Notification> listData;
    private int containerId;
    private Post post;

    public NotificationsAdapter(List<Notification> listData, int containerId)
    {
        this.listData = listData;
        this.containerId = containerId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.notification_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Notification myListData = listData.get(position);

        if (myListData != null)
        {
            String message = listData.get(position).getNotificationMessage();
            System.out.println(message);
            holder.textView.setText(message);
        }
        else
        {
            String invalid = "null";
            holder.textView.setText(invalid);
        }

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myListData != null) {
                    final String postID = myListData.getPostID();

                    final DatabaseHelper databaseHelper = new DatabaseHelper();
                    databaseHelper.getPosts(new FirebaseCallback() {
                        @Override
                        public void onCallback(List<Post> posts) {
                            post = databaseHelper.getPostByID(postID, posts);
                            post.removeNotification(myListData);
                        }
                    });

                    FragmentActivity activity = (FragmentActivity) v.getContext();

                    // if auction expired, and they won
                    if (post.getAuctionTimer().equals("AUCTION EXPIRED"))
                    {
                        Intent intent = new Intent(v.getContext(), AuctionResult.class);
                        intent.putExtra("postID", postID);
                        v.getContext().startActivity(intent);
                        ((FragmentActivity) v.getContext()).finish();
                    }
                    else {
                        Bundle bundle = new Bundle();
                        bundle.putString("POST ID", postID);

                        ViewPost viewPost = new ViewPost();
                        viewPost.setArguments(bundle);
                        activity.getSupportFragmentManager().beginTransaction().replace(containerId, viewPost).commit();
                    }
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.notification_message);
            this.relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }



}
