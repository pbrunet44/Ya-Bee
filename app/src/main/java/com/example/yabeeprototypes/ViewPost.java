package com.example.yabeeprototypes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewPost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);
        // this is needed to send post information
        // view post should also display the post's id, to make it easier to retrieve post information
        Button createBid = (Button)findViewById(R.id.BidButton);
        createBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewPost.this, InitialBid.class);
                startActivity(intent);
            }

            // after sending post information, the lowest bid on the screen has to be changed
            // so we must set the lowest bid price on the screen to the post's lowestbid e.g. with setText, etc.
        });
    }


}
