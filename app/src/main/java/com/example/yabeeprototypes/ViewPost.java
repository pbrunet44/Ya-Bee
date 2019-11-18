
package com.example.yabeeprototypes;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;

import java.text.DecimalFormat;
import java.util.List;

public class ViewPost extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_view_post, container, false);
        // this is needed to send post information
        // view post should also display the post's id, to make it easier to retrieve post information
        Bundle b = getArguments();
        String i = "";
        if (b != null) {
            i = getArguments().getString("POST ID");
            System.out.println(i);
        }
        else
        {
            System.out.println("ID is null");
        }
        final String id = i;
        System.out.println("I'm in ViewPost, here's the post ID:" + id);
        final Button createBid = (Button) view.findViewById(R.id.BidButton);

        final DatabaseHelper database = new DatabaseHelper();
        view.findViewById(R.id.loadingPanelforViewPost).setVisibility(View.VISIBLE);
        final TextView timer = (TextView) view.findViewById(R.id.timer); // retrieving timer off the post's page
        final TextView postTitle = (TextView) view.findViewById(R.id.postTitle);
        final TextView postDescription = (TextView) view.findViewById(R.id.postDescription);
        final ImageView postImage = (ImageView) view.findViewById(R.id.postImage);
        final TextView postClicks = view.findViewById(R.id.postClicks);

        final Handler timerHandler = new Handler();
        Runnable timerRunnable = new Runnable(){
            @Override
            public void run() {
                database.getPosts(new FirebaseCallback() {
                    @Override
                    public void onCallback(List<Post> posts) {
                        // find post with appropriate id
                        //System.out.println("Do you even get here?");
                        Post post = database.getPostByID(id, posts);
                        System.out.println(post.toString());
                        view.findViewById(R.id.loadingPanelforViewPost).setVisibility(View.GONE);
                        postTitle.setText(post.getTitle());
                        postDescription.setText(post.getDescription());
                        postImage.setImageBitmap(post.decodeImage());
                        postClicks.setText("Clicks: " + post.getClicks());
                        //System.out.println(post.toString());
                        //boolean isAuctionOver = false;
                        if (post != null)
                        {
                            post.updateAuctionTimer();
                            timer.setText(post.getAuctionTimer()); // setting timer to time remaining
                        }
                    }
                });
                timerHandler.postDelayed(this, 500);
            }
        };

        timerRunnable.run();

        createBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MakeBid.class);
                intent.putExtra("POST ID", id);
                startActivity(intent);
            }
        });

        // now get new bid price
        database.getPosts(new FirebaseCallback() {
            @Override
            public void onCallback(List<Post> posts) {
                Post post = database.getPostByID(id, posts);
                System.out.println(post.toString());
                //Increment clicks while we're here
                post.incrementClicksOnFirebase();
                TextView currBid = view.findViewById(R.id.lowestBid);
                DecimalFormat df = new DecimalFormat("#.##");
                String newLowestBid = null;
                if (post.getLowestBid().price == post.INITIAL_BID_PRICE)
                    newLowestBid = "Max price: " + "$" + df.format(post.getMaxPrice()) + "\nNo bids yet!";
                else
                    newLowestBid = "Current bid: $" + df.format(post.getLowestBid().price);
                //System.out.println("Got new bid price in viewpost: " + newLowestBid);
                currBid.setText(newLowestBid);
            }
        });

        return view;

    }

}