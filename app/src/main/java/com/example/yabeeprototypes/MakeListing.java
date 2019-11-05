package com.example.yabeeprototypes;


import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Date;

public class MakeListing extends AppCompatActivity {
    // initial values for bid price, imageurl, initial description respectively
    static final double INITIAL_BID_PRICE = Double.MAX_VALUE;
    static final String INITIAL_IMAGE = "";
    static final String INITIAL_DESCRIPTION = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_listing);
        Button makePost = findViewById(R.id.collectPostInfo);
        final DatabaseHelper database = new DatabaseHelper();

        makePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // collect post info from button click
                // probably must check for validation and require them to fill out all of these

                String title = ((TextView)findViewById(R.id.titleEntry)).getText().toString();
                String description = ((TextView)findViewById(R.id.descEntry)).getText().toString();
                String category = ((Spinner)findViewById(R.id.categoryDropdown)).getSelectedItem().toString();
                double maxPrice = Double.parseDouble(((TextView)findViewById(R.id.yourPrice)).getText().toString());
                int auctionLength = Integer.parseInt(((TextView)findViewById(R.id.durationOfAuction)).getText().toString());

                // will take care of image url later that's why it's null
                Date date = new Date();
                Post post = new Post(title, maxPrice, description, auctionLength, new Bid(INITIAL_BID_PRICE, INITIAL_DESCRIPTION, INITIAL_IMAGE), INITIAL_IMAGE, category, Long.toString(System.nanoTime()), date, false);
                database.writeNewPost(post);

                Toast successToast = Toast.makeText(getApplicationContext(), "Successful post creation!", Toast.LENGTH_LONG);
                successToast.setGravity(Gravity.TOP, 0, 0);
                successToast.show();
            }
        });

    }

}
