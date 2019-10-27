package com.example.yabeeprototypes;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseHelper {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    public DatabaseHelper()
    {
        this.database = FirebaseDatabase.getInstance();
        this.databaseReference = this.database.getReference("Posts"); // need to take into account bids...
    }

    public void writeNewPost(Post post)
    {
        this.databaseReference.setValue(post);
    }

}
