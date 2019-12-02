package com.example.yabeeprototypes;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>
{
    private List<Post> listData;
    private int containerId;
    private int layout;

    public PostAdapter(int layout, int containerId, List<Post> listData)
    {
        this.layout = layout;
        this.listData = listData;
        this.containerId = containerId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(this.layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Post myListData = listData.get(position);
        if (listData.get(position) != null) {
            String bid = "";
            DecimalFormat df = new DecimalFormat("#.##");
            holder.textView.setText(listData.get(position).getTitle());
            holder.imageView.setImageBitmap(listData.get(position).decodeImage());
            listData.get(position).updateAuctionTimer();
            holder.timeLeft.setText(listData.get(position).getAuctionTimer());
            holder.condition.setText(listData.get(position).getCondition());


            if (listData.get(position).getLowestBid().price == listData.get(position).INITIAL_BID_PRICE)
                bid = "No bids placed yet!";
            else
                bid = "$" + df.format(listData.get(position).getLowestBid().price);

            holder.bid.setText(bid);
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(view.getContext(), "click on item: " + myListData.getTitle(), Toast.LENGTH_LONG).show();
                    // start view post activity, send post information
                    Bundle bundle = new Bundle();
                    bundle.putString("POST ID", myListData.getId());

                    FragmentActivity activity = (FragmentActivity) view.getContext();


                    ViewPost vp = new ViewPost();
                    vp.setArguments(bundle);
                    // need loading screen while firebase is loading data
                    activity.getSupportFragmentManager().beginTransaction().replace(containerId, vp).commit();
                }
            });
            if (holder.menu != null) {
                holder.menu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                        MenuInflater inflater = popupMenu.getMenuInflater();
                        inflater.inflate(R.menu.item, popupMenu.getMenu());
                        popupMenu.show();
                    }
                });
            }
            if (holder.clear != null) {
                holder.clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // remove from arraylist
                        final Post post = listData.get(position);
                        listData.remove(post);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, listData.size());
                        // get current user
                        // find user
                        // remove element from the wishlist
                        // update wishlist on firebase
                        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        final DatabaseHelper databaseHelper = new DatabaseHelper();
                        databaseHelper.getUsers(new UserCallback() {
                            @Override
                            public void onCallback(List<User> users) {
                                User user = databaseHelper.getUserById(currentUser.getUid(), users);
                                user.getWishlist().remove(post.getId());
                                databaseHelper.updateUserOnFirebase(user);
                            }
                        });
                    }
                });
            }
        }
        else
        {
            String invalid = "null";
            holder.textView.setText(invalid);
            holder.timeLeft.setText(invalid);
            holder.condition.setText(invalid);
        }
    }
    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public TextView condition;
        public TextView bid;
        public TextView timeLeft;
        public ImageButton menu;
        public ImageButton clear;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
            this.textView = (TextView) itemView.findViewById(R.id.textView);
            this.condition = (TextView) itemView.findViewById(R.id.condition);
            this.bid = (TextView) itemView.findViewById(R.id.bidPrice);
            this.timeLeft = (TextView) itemView.findViewById(R.id.timeLeft);
            this.menu = (ImageButton) itemView.findViewById(R.id.item_menu);
            this.clear = (ImageButton) itemView.findViewById(R.id.item_clear);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }



}
