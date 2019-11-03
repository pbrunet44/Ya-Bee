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
    public DatabaseReference databaseReference;
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
        this.databaseReference.child("Posts").child(post.getId()).setValue(post);
    }

    //id the ID string of the target post
    /**
     * Queries the database for a post with the provided ID
     * @param
     * @return the target Post object
     */
    /*public Post getPostByID(String id)
    {
        Post result = null;
        for (Post post: this.posts) {
            if(post.id.equals(id))
            {
                result = post;
            }
        }

        return result;
    }*/
    public void getPosts(final FirebaseCallback firebaseCallback)
    {
        this.databaseReference.child("Posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                posts.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren())
                {
                    Post post = snapshot.getValue(Post.class);
                    posts.add(post);
                    System.out.println(post.getTitle() +  ", " + post.getDescription());
                }
                firebaseCallback.onCallback(posts);
                //System.out.println("Size of list of posts: " + posts.size());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                System.out.println("ERROR: data not read!");
            }
        });
    }

    public Post getPostByID(String id, List<Post> posts)
    {
        Post result = null;
        for (Post p: posts)
        {
            if (id.equals(p.getId()))
            {
                result = p;
                break;
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
            if(post.getCategory().equals(category))
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
            if(post.getTitle().contains(keyword))
            {
                results.add(post);
            }
        }

        return results;
    }

    /**
     * Updates a post's lowest bid
     */
    public void updateLowestBid(String path, Bid bid) {

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
            for(DataSnapshot snapshot: dataSnapshot.getChildren())
            {
                Post post = snapshot.getValue(Post.class);
                posts.add(post);
                System.out.println(post.getTitle() +  ", " + post.getDescription());
            }
            //System.out.println("Size of list of posts: " + posts.size());
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError)
        {
            System.out.println("ERROR: data not read!");
        }
    };

}
