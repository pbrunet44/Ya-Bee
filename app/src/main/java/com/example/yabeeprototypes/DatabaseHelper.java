package com.example.yabeeprototypes;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseHelper {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    public DatabaseHelper()
    {
        this.database = FirebaseDatabase.getInstance();
    }

    public void writeNewPost(Post post)
    {
        this.databaseReference = this.database.getReference("Posts"); // need to take into account bids...
        this.databaseReference.setValue(post);
    }

    public Post readPostInformation()
    {
        Post post = new Post();
        // need to work on retrieving information from the database in appropriate structure
        // also i need to take into account as to whether i am looking for a specific post's information
        // so i need to change the signature of this method to accept a parameter
        return post;
    }

    public MakeBid getLowestBid()
    {
        // simply return lowest bid from database
        MakeBid bid = new MakeBid();
        return bid;
    }

    public void setLowestBid()
    {
        // update lowest bid in the database
    }



}
