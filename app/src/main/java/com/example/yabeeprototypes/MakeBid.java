package com.example.yabeeprototypes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MakeBid extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_bid);
        final DatabaseHelper database = new DatabaseHelper();

        // getting post id so we can therefore get post information
        Intent intent = getIntent();
        String postID = intent.getStringExtra("POST ID");
        final Post post = database.getPostByID(postID);

        // create onClick listener
        Button submitBid = (Button) findViewById(R.id.btnSubmit);
        submitBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imageUrl = null; // null for now, will go back to take into account images
                String description = ((TextView) findViewById(R.id.etDescription)).getText().toString();
                double price = Double.parseDouble(((TextView) findViewById(R.id.etBidPrice)).getText().toString());
                Bid bid = new Bid(price, description, imageUrl);
                if (post.verifyBid(bid))
                {
                    post.setLowestBid(bid);
                    // now set the lowest bid on the post's page
                    String postBid = "$" + bid.price;
                    TextView lowestBid = ((TextView)findViewById(R.id.lowestBid));
                    lowestBid.setText(postBid);
                }
                else
                {
                    // prompt a toast telling user to enter a valid bid
                    Toast.makeText(MakeBid.this, "Your bid is too high! Please bid lower.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}