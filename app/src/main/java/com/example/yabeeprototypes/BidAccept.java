package com.example.yabeeprototypes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class BidAccept extends AppCompatActivity {

    private GestureDetectorCompat gestureObject;
    private Button accept;
    private Button decline;

    private Bid bid;
    private ImageView bidImage;
    private TextView askingBid;
    private TextView submissionDesc;
    private String imageEncoded;

    private String uID;
    private String uEmail;
    private User seller;

    private Post post;
    private String postID;

    private DatabaseHelper databaseHelper;

    private double dummyBid = 42.69;
    private String description = "timmy turner commits tax fraud and wishes he gets away with it and does " +
            "timmy turner commits tax fraud and wishes he gets away with it and does " +
            "timmy turner commits tax fraud and wishes he gets away with it and does " +
            "timmy turner commits tax fraud and wishes he gets away with it and does " +
            "timmy turner commits tax fraud and wishes he gets away with it and does " +
            "timmy turner commits tax fraud and wishes he gets away with it and does " +
            "timmy turner commits tax fraud and wishes he gets away with it and does " +
            "";
    // dummy value lol

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_accept);
        System.out.println(description);
        databaseHelper = new DatabaseHelper();

        Intent jonRamos = getIntent();
        Bundle b = jonRamos.getExtras();

        bidImage = (ImageView) findViewById(R.id.bidPhoto);
        askingBid = (TextView) findViewById(R.id.askingBid);
        submissionDesc = (TextView) findViewById(R.id.sellerDescription);
        accept = (Button) findViewById(R.id.yeaSure);
        decline = (Button) findViewById(R.id.noDice);

        dummyBid = b.getDouble("BID PRICE");
        description = b.getString("BID DESC");
        imageEncoded = b.getString("IMAGE STRING");
        uID = b.getString("USER ID");
        uEmail = b.getString("USER EMAIL");
        postID = b.getString("POST ID");
        seller = new User(uEmail, uID);

        bid = new Bid(dummyBid, description, imageEncoded, seller);

        bidImage.setImageBitmap(bid.decodeImage());
        askingBid.setText("Bid: $" + dummyBid);
        submissionDesc.setText(description);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptBid();
            }
        });

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                declineBid();
            }
        });

        gestureObject = new GestureDetectorCompat(this, new LearnGesture());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class LearnGesture extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e2.getX() > e1.getX()) {
                //left to right swipe
                //Intent intense = new Intent(BidAccept.this, ItemsBuying.class);
                //startActivity(intense);

                acceptBid();
            }

            else if(e2.getX() < e1.getX()) {
                //right to left
                //Intent intents = new Intent(BidAccept.this, ItemsBuying.class);
                //startActivity(intents);

                declineBid();
            }
            return true;
        }
    }

    private void acceptBid()
    {
        Toast.makeText(getApplicationContext(), "Bid Accepted", Toast.LENGTH_LONG).show();
        databaseHelper.getPosts(new FirebaseCallback() {
            @Override
            public void onCallback(List<Post> posts) {
                post = databaseHelper.getPostByID(postID, posts);
                post.removeFromBidPendingAcceptance(bid);
                post.updateNewLowestBid(bid);
//                Notification notification = new Notification("BID ACCEPTED", seller, postID);
//                post.addNotification(notification);
                finish();
            }
        });
    }

    private void declineBid()
    {
        Toast.makeText(getApplicationContext(), "Bid Declined", Toast.LENGTH_LONG).show();
        databaseHelper.getPosts(new FirebaseCallback() {
            @Override
            public void onCallback(List<Post> posts) {
                post = databaseHelper.getPostByID(postID, posts);
                post.removeFromBidPendingAcceptance(bid);
//                Notification notification = new Notification("BID DECLINED", seller, postID);
//                post.addNotification(notification);
                finish();
            }
        });
    }

    private void backToViewPost()
    {
        Bundle bundle = new Bundle();
        bundle.putString("POST ID", postID);

        FragmentActivity activity = (FragmentActivity) getApplicationContext();


        ViewPost vp = new ViewPost();
        vp.setArguments(bundle);
        // need loading screen while firebase is loading data
        //activity.getSupportFragmentManager().beginTransaction().replace(containerId, vp).addToBackStack(null).commit();
    }

}
