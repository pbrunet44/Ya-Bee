package com.example.yabeeprototypes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


public class MakeListing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_listing);

        final Button collectInfoButton = (Button)findViewById(R.id.collectPostInfo);
        collectInfoButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                // collect stuff from the ids
                String title = ((TextView)findViewById(R.id.titleEntry)).getText().toString();
                String description = ((TextView)findViewById(R.id.descEntry)).getText().toString();
                String category = ((TextView)findViewById(R.id.categoryDropdown)).getText().toString();
                String maxPrice = ((TextView)findViewById(R.id.yourPrice)).getText().toString();
                String auctionDuration = ((TextView)findViewById(R.id.durationOfAuction)).getText().toString();
                // make a new post object from Post class
                //Post newPost = new Post(title, Double.parseDouble(maxPrice), description, Integer.parseInt(auctionDuration), null, category);
                // first get instance and reference of database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();
                // now store post info into the database
                myRef = database.getReference().child("title");
                myRef.setValue("title");

                // now testing to see if we can read from the database
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String postInfo = dataSnapshot.getValue(String.class);
                        Log.d("MESSAGE:", "Please print something: " + postInfo.toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.w("MESSAGE:", databaseError.toException());
                    }
                });
            }
        });
    }

}
