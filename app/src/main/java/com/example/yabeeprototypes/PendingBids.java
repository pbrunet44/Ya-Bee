package com.example.yabeeprototypes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class PendingBids extends AppCompatActivity {

    private static int MAX_NUMBER_OF_PENDING_BIDS = 1000000;
    private TextView test;
    private String postID;
    private DatabaseHelper databaseHelper;
    private ArrayList<Bid> bidsPendingAcceptance;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items_pendingbids);

        test = findViewById(R.id.testez);

        Intent intent = getIntent();
        postID = intent.getStringExtra("POST ID");

        System.out.println("Here is the post id in pending bids: " + postID);

        databaseHelper = new DatabaseHelper();

        bidsPendingAcceptance = new ArrayList<>();

        databaseHelper.getPosts(new FirebaseCallback() {
            @Override
            public void onCallback(List<Post> posts) {
                bidsPendingAcceptance = databaseHelper.getPostByID(postID, posts).getBidsPendingAcceptance();
                if(bidsPendingAcceptance == null || bidsPendingAcceptance.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "No bids to accept.", Toast.LENGTH_SHORT).show();
                }
                else {
                    recyclerView = (RecyclerView) findViewById(R.id.recyclerViewForPendingBids);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setHasFixedSize(true);
                    BidAdapter adapter = new BidAdapter(bidsPendingAcceptance, R.id.pendingBidsContainer, postID);
                    recyclerView.setAdapter(adapter);
//                    test.setText(bidsPendingAcceptance.get(0).getDescription());
//                    test.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intense = new Intent(getApplicationContext(), BidAccept.class);
//                            Bundle b = new Bundle();
//                            b.putDouble("BID PRICE", bidsPendingAcceptance.get(0).getPrice());
//                            b.putString("BID DESC", bidsPendingAcceptance.get(0).getDescription());
//                            b.putString("IMAGE STRING", bidsPendingAcceptance.get(0).getImageEncoding());
//                            b.putString("USER ID", bidsPendingAcceptance.get(0).getSeller().getUid());
//                            b.putString("USER EMAIL", bidsPendingAcceptance.get(0).getSeller().getEmail());
//                            b.putString("POST ID", postID);
//                            intense.putExtras(b);
//                            startActivity(intense);
//                            finish();
//                        }
//                    });
                }
                findViewById(R.id.loadingPanelforPendingBids).setVisibility(View.GONE);
            }
        });

        //recycler view

    }
}
