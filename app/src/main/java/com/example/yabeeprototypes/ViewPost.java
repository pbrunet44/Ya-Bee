package com.example.yabeeprototypes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewPost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        Button createBid = (Button)findViewById(R.id.BidButton);
        // now retrieve post info using post's unique id
        final String id = ((TextView)findViewById(R.id.postID)).getText().toString();
        createBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewPost.this, InitialBid.class);
                intent.putExtra("POST ID", id);
                startActivity(intent);
            }
            // after sending post information, the lowest bid on the screen has to be changed
            // so we must set the lowest bid price on the screen to the post's lowestbid e.g. with setText, etc.
        });
    }


}
