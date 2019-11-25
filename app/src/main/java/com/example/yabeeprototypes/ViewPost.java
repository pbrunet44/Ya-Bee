
package com.example.yabeeprototypes;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DecimalFormat;
import java.util.List;

public class ViewPost extends Fragment {

    private boolean updatedClicks = false;
    private Post post;


    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        final Button interested = (Button) view.findViewById(R.id.interestedButton);

        final DatabaseHelper database = new DatabaseHelper();
        //view.findViewById(R.id.loadingPanelforViewPost).setVisibility(View.VISIBLE);
        final TextView timer = (TextView) view.findViewById(R.id.timer); // retrieving timer off the post's page
        final TextView postTitle = (TextView) view.findViewById(R.id.postTitle);
        final TextView postCondition = (TextView) view.findViewById(R.id.postCondition);
        final TextView postDescription = (TextView) view.findViewById(R.id.postDescription);
        final TextView postCategory = (TextView) view.findViewById(R.id.postCategory);
        final ImageView postImage = (ImageView) view.findViewById(R.id.postImage);
        final TextView postClicks = (TextView) view.findViewById(R.id.postClicks);
        final Button editPostButton = (Button) view.findViewById(R.id.editPostButton);
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        final Handler timerHandler = new Handler();
        Runnable timerRunnable = new Runnable(){
            @Override
            public void run() {
                database.getPosts(new FirebaseCallback() {
                    @Override
                    public void onCallback(List<Post> posts) {
                        // find post with appropriate id
                        //System.out.println("Do you even get here?");
                        post = database.getPostByID(id, posts);
                        System.out.println(post.toString());
                        //view.findViewById(R.id.loadingPanelforViewPost).setVisibility(View.GONE);
                        if(currentUser == null)
                        {
                            interested.setVisibility(View.INVISIBLE);
                        }
                        if (currentUser != null && !(post.getBuyer().getUid().equals(currentUser.getUid())))
                        {
                            editPostButton.setVisibility(View.INVISIBLE);
                        }
                        if (currentUser != null && (post.getBuyer().getUid().equals(currentUser.getUid())))
                        {
                            createBid.setVisibility(View.INVISIBLE);
                        }

                        postTitle.setText(post.getTitle());
                        postCondition.setText(post.getCondition());
                        postDescription.setText(post.getDescription());
                        postImage.setImageBitmap(post.decodeImage());
                        postCategory.setText(post.getCategory());
                        postClicks.setText(String.valueOf(post.getClicks()));
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

        editPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle postExtras = new Bundle();
                // adding key value pairs to this bundle
                postExtras.putString("Post ID", id);
                //postExtras.putString("Image", post.getImageEncoding());
                postExtras.putString("Title", post.getTitle());
                postExtras.putString("Description", post.getDescription());
                postExtras.putString("Category", post.getCategory());
                postExtras.putString("Condition", post.getCondition());

                Intent intent = new Intent(getActivity(), EditPost.class);
                intent.putExtras(postExtras);
                startActivity(intent);

            }
        });

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
                if(!updatedClicks)
                {
                    post.incrementClicksOnFirebase();
                    updatedClicks = true;
                }
                TextView currBid = view.findViewById(R.id.lowestBid);
                DecimalFormat df = new DecimalFormat("#.##");
                String newLowestBid = null;
                if (post.getLowestBid().price == post.INITIAL_BID_PRICE)
                    newLowestBid = "Max price: " + "$" + df.format(post.getMaxPrice()) + " No bids yet!";
                else
                    newLowestBid = "Current bid: $" + df.format(post.getLowestBid().price);
                //System.out.println("Got new bid price in viewpost: " + newLowestBid);
                currBid.setText(newLowestBid);
            }
        });

        interested.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.getUsers(new UserCallback() {
                    @Override
                    public void onCallback(List<User> users) {
                        User user = database.getUserById(currentUser.getUid(), users);
                        if (user != null) {
                            user.addToWishlist(id);
                            user.updateUserOnFirebase();
                            //Toast successToast = Toast.makeText(getContext(), "Added to wishlist!", Toast.LENGTH_LONG);
                            //successToast.setGravity(Gravity.BOTTOM, 0, 0);
                            //successToast.show();
                        } else {
                            //Toast userNotFoundToast = Toast.makeText(getContext(), "Error finding user in database", Toast.LENGTH_LONG);
                            //userNotFoundToast.setGravity(Gravity.BOTTOM, 0, 0);
                            //userNotFoundToast.show();
                        }
                    }
                });
            }
        });

        return view;

    }

}