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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;


public class PendingBids extends AppCompatActivity {

    private static int MAX_NUMBER_OF_PENDING_BIDS = 1000000;
    private String postID;
    private Post post;
    private DatabaseHelper databaseHelper;
    private ArrayList<Bid> bidsPendingAcceptance;
    private RecyclerView recyclerView;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items_pendingbids);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = getIntent();
        postID = intent.getStringExtra("POST ID");

        System.out.println("Here is the post id in pending bids: " + postID);

        databaseHelper = new DatabaseHelper();

        bidsPendingAcceptance = new ArrayList<>();

        databaseHelper.getPosts(new FirebaseCallback() {
            @Override
            public void onCallback(List<Post> posts) {
                post = databaseHelper.getPostByID(postID, posts);
                bidsPendingAcceptance = post.getBidsPendingAcceptance();
                if(bidsPendingAcceptance == null || bidsPendingAcceptance.isEmpty())
                {
                    if(currentUser.getUid().equals(post.getBuyer().getUid()))
                        Toast.makeText(getApplicationContext(), "No bids to accept.", Toast.LENGTH_SHORT).show();
                }
                else {
                    recyclerView = (RecyclerView) findViewById(R.id.recyclerViewForPendingBids);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setHasFixedSize(true);
                    BidAdapter adapter = new BidAdapter(bidsPendingAcceptance, R.id.pendingBidsContainer, postID);
                    recyclerView.setAdapter(adapter);
                }
                findViewById(R.id.loadingPanelforPendingBids).setVisibility(View.GONE);
            }
        });

        //recycler view

    }
}
