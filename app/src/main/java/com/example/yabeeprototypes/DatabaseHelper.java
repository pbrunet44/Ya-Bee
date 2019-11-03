package com.example.yabeeprototypes;


import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private List<Post> posts;

    public DatabaseHelper()
    {
        this.database = FirebaseDatabase.getInstance();
        this.databaseReference = this.database.getReference();
        this.posts = new ArrayList<>(); //Holds current state of database
        this.databaseReference.child("Posts").addValueEventListener(postListener); //Updates current state of database
    }

    public void writeNewPost(Post post)
    {
        this.databaseReference.child("Posts").child(post.id).setValue(post);
    }

    /**
     * Queries the database for a post with the provided ID
     * @param id the ID string of the target post
     * @return the target Post object
     */
    public Post getPostByID(String id)
    {
        Post result = null;
        for (Post post:posts) {
            if(post.id.equals(id))
            {
                result = post;
            }
        }

        return result;
    }

    /**
     * Queries the database for posts with a certain category
     * @param category the desired category (Electronics, Shoes, etc.) as a string
     * @return an ArrayList of posts with the given category
     */
    public ArrayList<Post> getPostsByCategory(String category)
    {
        ArrayList<Post> results = new ArrayList<>();
        for (Post post:posts) {
            if(post.category.equals(category))
            {
                results.add(post);
            }
        }

        return results;
    }

    /**
     * Queries the database for posts containing a certain keyword in the title
     * @param keyword the string to be checked against post titles
     * @return an ArrayList of posts containing the keyword in their titles
     */
    public ArrayList<Post> getPostsByKeyword(String keyword)
    {
        ArrayList<Post> results = new ArrayList<>();
        for (Post post:posts) {
            if(post.title.contains(keyword))
            {
                results.add(post);
            }
        }

        return results;
    }

//    public void readPostInformation()
//    {
//        Post post = new Post();
//        this.databaseReference = this.database.getReference().child("Posts").child("10107998789100");
//        System.out.println("Before listener!!");
//        ValueEventListener val = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    Post post = dataSnapshot.getValue(Post.class);
//                    System.out.println("Inside listener!");
//                }
//                else
//                {
//                    System.out.println("DATABASE: The value is null!");
//                }
//
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println(databaseError.toString());
//            }
//        };
//        System.out.println("Outside of listener!!");
//        this.databaseReference.addValueEventListener(val);
//    }

    public void updateLowestBid(String path, Bid bid) {
        // update lowest bid in the database
        this.databaseReference = this.database.getReference(path);
        this.databaseReference.child(path).setValue(bid);
    }

    /**
     * Scans for database updates and updates the local ArrayList, called in constructor
     */
    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            posts.clear();
            if(dataSnapshot.exists())
            {
                //for(DataSnapshot snapshot: dataSnapshot.getChildren())
                //{
                    Post post = dataSnapshot.getValue(Post.class);
                    posts.add(post);
                    System.out.println(post.title +  ", " + post.description);
                //}
                System.out.println("LOCAL ARRAYLIST UPDATED!");
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError)
        {
            System.out.println("ERROR: data not read!");
        }
    };

}
