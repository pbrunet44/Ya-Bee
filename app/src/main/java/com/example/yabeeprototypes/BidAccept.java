package com.example.yabeeprototypes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class BidAccept extends AppCompatActivity {

    private GestureDetectorCompat gestureObject;
    private Button accept;
    private Button decline;

    private Bid bid;
    private ImageView bidImage;
    private TextView askingBid;
    private TextView submissionDesc;
    private Bitmap bitmap;

    private String uID;
    private String uEmail;
    private User seller;

    private Post post;
    private String postID;
    private ArrayList<User> allBidders;
    private FirebaseUser currentUser;

    private int count = 0;

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
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        bidImage = (ImageView) findViewById(R.id.bidPhoto);
        askingBid = (TextView) findViewById(R.id.askingBid);
        submissionDesc = (TextView) findViewById(R.id.sellerDescription);
        accept = (Button) findViewById(R.id.yeaSure);
        decline = (Button) findViewById(R.id.noDice);

        dummyBid = b.getDouble("BID PRICE");
        description = b.getString("BID DESC");
        uID = b.getString("USER ID");
        uEmail = b.getString("USER EMAIL");
        postID = b.getString("POST ID");
        seller = new User(uEmail, uID);
        String fileName = b.getString("FILE NAME");



        try {
            bitmap = BitmapFactory.decodeStream(getApplicationContext().openFileInput(fileName));
        } catch (FileNotFoundException e) {
            System.out.println("Zoinks");
        } catch (NullPointerException npe) {
            System.out.println("No image was input");
        }

        if (bitmap != null)
            bid = new Bid(dummyBid, description, encodeImage(bitmap), seller);
        else
            bid = new Bid(dummyBid, description, null, seller);

        //bidImage.setImageBitmap(bid.decodeImage());
        databaseHelper.getPosts(new FirebaseCallback() {
            @Override
            public void onCallback(List<Post> posts) {
                post = databaseHelper.getPostByID(postID, posts);
                bidImage.setImageBitmap(bitmap);
            }
        });

        DecimalFormat df = new DecimalFormat("#.##");
        askingBid.setText("Bid: $" + df.format(dummyBid));
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
                allBidders = post.getAllBidders();

                if(count < 1)
                {
                    if (!post.alreadyBid(seller.getUid()))
                    {
                        post.addBidderToList(seller);
                    }
                    if (allBidders != null && !allBidders.isEmpty()) {
                        for (User u : allBidders) {
                            if (!u.getUid().equals(seller.getUid())) {
                                Notification notification = new Notification("BID", u, post.getId());
                                post.addNotification(notification);
                            }
                        }
                    }

                    post.addNotification(new Notification("BID ACCEPTED", seller, postID));
                    count++;
                }
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
                if(count < 1)
                {
                    post.addNotification(new Notification("BID DECLINED", seller, postID));
                    count++;
                }
//                Notification notification = new Notification("BID DECLINED", seller, postID);
//                post.addNotification(notification);
                finish();
            }
        });
    }

    private String encodeImage(Bitmap bitmap)
    {
        System.out.println("Do I even get here?");
        String imageEncoding = "";
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        imageEncoding = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        return imageEncoding;
    }

}
