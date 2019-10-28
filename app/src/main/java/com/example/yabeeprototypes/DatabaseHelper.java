package com.example.yabeeprototypes;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DatabaseHelper {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    //private ArrayList<Post> posts;

    public DatabaseHelper()
    {
        this.database = FirebaseDatabase.getInstance();
        this.databaseReference = this.database.getReference(); // need to take into account bids...
        //this.posts = new ArrayList<Post>();
        databaseReference.child("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Post checkPost = dataSnapshot.child("Bike").getValue(Post.class);
                //System.out.println(checkPost.title);
                for (DataSnapshot child:dataSnapshot.getChildren()) {
                    Post readPost = child.getValue(Post.class);
                    System.out.println(readPost.title + ", " + readPost.description);
                    //posts.add(readPost);
                }
                System.out.println("WE GOT HERE BOIS!");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void writeNewPost(Post post)
    {
        this.databaseReference.child("Posts").child(post.title).setValue(post);
    }

    public Post readPostInformation()
    {
        Post post = new Post();
        // need to work on retrieving information from the database in appropriate structure
        // also i need to take into account as to whether i am looking for a specific post's information
        // so i need to change the signature of this method to accept a parameter
        return post;
    }

    public void updateLowestBid(String path, Bid bid) {
        // update lowest bid in the database
        this.databaseReference = this.database.getReference(path);
        this.databaseReference.child(path).setValue(bid);
    }

//    public void queryPost(String title)
//    {
//        com.google.firebase.database.Query query = this.databaseReference.child("Posts").orderByChild("title").equalTo(title);
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot data:dataSnapshot.getChildren()){
//                    Post post = data.getValue(Post.class);
//                    System.out.println(post.title + ", " + post.description);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Log.e("DatabaseHelper", "onCancelled", databaseError.toException());
//            }
//        });
//    }

    public void getPost(Post post)
    {
        this.databaseReference.child("Posts").child(post.title).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Post readPost = dataSnapshot.getValue(Post.class);
                System.out.println(readPost.title);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DatabaseHelper", "onCancelled", databaseError.toException());
            }
        });

    }



}
