package com.example.yabeeprototypes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InitialBid extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_bid);

        // create onClick listener
        Button submitBid = (Button) findViewById(R.id.btnSubmit);
        final DatabaseHelper database = new DatabaseHelper();
        submitBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imageUrl = null; // null for now, will go back to take into account images
                String description = ((TextView) findViewById(R.id.etDescription)).getText().toString();
                double price = Double.parseDouble(((TextView) findViewById(R.id.etBidPrice)).getText().toString());
                Bid bid = new Bid(price, description, imageUrl, Long.toString(System.nanoTime()));

            }
        });
    }
}