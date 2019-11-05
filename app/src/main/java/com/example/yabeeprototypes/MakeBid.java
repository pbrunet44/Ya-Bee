package com.example.yabeeprototypes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.List;

public class MakeBid extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_bid);
        System.out.println("I'm MakeBid's databaseHelper!");
        final DatabaseHelper database = new DatabaseHelper();
        // getting post id so we can therefore get post information
        Intent intent = getIntent();
        final String postID = intent.getStringExtra("POST ID");

        System.out.println("MakeBid - Post id:" + postID);
        final Button submitBid = (Button) findViewById(R.id.btnSubmit);
        //final Post post = database.getPostByID(postID);
        database.getPosts(new FirebaseCallback() {
            @Override
            public void onCallback(List<Post> posts) {
                // find post with appropriate id
                final Post post = database.getPostByID(postID, posts);
                System.out.println(post.toString());
                submitBid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String imageUrl = "INITIAL BID PICTURE"; // null for now, will go back to take into account images
                        String description = ((TextView) findViewById(R.id.etDescription)).getText().toString();
                        double price = Double.parseDouble(((TextView) findViewById(R.id.etBidPrice)).getText().toString());
                        Bid bid = new Bid(price, description, imageUrl);
                        if (post.verifyBid(bid))
                        {
                            post.updateNewLowestBid(bid);
                            finish();// go back to view post after submitting bid
                        }
                        else
                        {
                            // prompt a toast telling user to enter a valid bid
                            DecimalFormat df = new DecimalFormat("#.##");
                            String currBidPrice = df.format(post.getLowestBid().price);
                            String toastText = "The current bid is $" + currBidPrice + ". Please bid lower.";
                            Toast invalidBid = Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_LONG);
                            invalidBid.setGravity(Gravity.TOP, 0, 0);
                            invalidBid.show();
                        }
                    }

            });
        }});

    }
}