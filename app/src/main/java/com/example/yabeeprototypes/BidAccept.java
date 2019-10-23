package com.example.yabeeprototypes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class BidAccept extends AppCompatActivity {

    //@Override
    double dummyBid = 42.69;
    String description = "timmy turner commits tax fraud and wishes he gets away with it and does " +
            "timmy turner commits tax fraud and wishes he gets away with it and does " +
            "timmy turner commits tax fraud and wishes he gets away with it and does " +
            "timmy turner commits tax fraud and wishes he gets away with it and does " +
            "timmy turner commits tax fraud and wishes he gets away with it and does " +
            "timmy turner commits tax fraud and wishes he gets away with it and does " +
            "timmy turner commits tax fraud and wishes he gets away with it and does " +
            "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_accept);

        TextView askingBid = (TextView) findViewById(R.id.askingBid);
        TextView submissionDesc = (TextView) findViewById(R.id.sellerDescription);

        askingBid.setText("Bid: $" + dummyBid);
        submissionDesc.setText(description);


    }





}
