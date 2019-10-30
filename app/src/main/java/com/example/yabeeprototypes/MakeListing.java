package com.example.yabeeprototypes;


import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class MakeListing extends AppCompatActivity {

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
                String category = ((TextView)findViewById(R.id.categoryDropdown)).getText().toString();
                double maxPrice = Double.parseDouble(((TextView)findViewById(R.id.yourPrice)).getText().toString());
                int auctionLength = Integer.parseInt(((TextView)findViewById(R.id.durationOfAuction)).getText().toString());

                // will take care of image url later that's why it's null
                Post post = new Post(title, maxPrice, description, auctionLength, null, category, Long.toString(System.nanoTime()));
                database.writeNewPost(post);

            }
        });

    }

}
