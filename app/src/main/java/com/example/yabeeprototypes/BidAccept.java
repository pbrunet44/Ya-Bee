package com.example.yabeeprototypes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

public class BidAccept extends AppCompatActivity {

    private GestureDetectorCompat gestureObject;

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
                Intent intense = new Intent(BidAccept.this, ItemsBuying.class);
                Toast.makeText(getApplicationContext(), "Bid Accepted", Toast.LENGTH_LONG).show();
                startActivity(intense);
            }

            else if(e2.getX() < e1.getX()) {
                //right to left
                Intent intents = new Intent(BidAccept.this, ItemsBuying.class);
                Toast.makeText(getApplicationContext(), "Bid Declined", Toast.LENGTH_LONG).show();
                startActivity(intents);
            }
            //buttons don't do anything lol
            return true;
        }
    }



}
