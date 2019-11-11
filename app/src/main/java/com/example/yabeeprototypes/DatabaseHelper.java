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
    }

    public void writeNewPost(Post post)
    {
        this.databaseReference.child("Posts").child(post.getId()).setValue(post);
    }

    /**
     * Retrieves all posts from the Firebase realtime database and passes it to firebaseCallback
     * @param firebaseCallback
     */
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
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                System.out.println("ERROR: data not read!");
            }
        });
    }

    /**
     * Retrieves a specific post based on id by searching through posts
     * @param posts
     * @param id
     * @return post with specific id
     */
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
     * Retrieves posts from specified category
     * @param category
     * @param posts
     * @return list of posts consisting of requested category
     */
    public ArrayList<Post> getPostsByCategory(String category, List<Post> posts)
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
     * Retrieves posts containing specified keyword in their titles
     * @param keyword
     * @param posts
     * @return list of posts containing specified keyword in their titles
     */
    public ArrayList<Post> getPostsByTitle(String keyword, List<Post> posts)
    {
        ArrayList<Post> results = new ArrayList<>();
        for (Post post:posts) {
            if(post.getTitle().toLowerCase().contains(keyword.toLowerCase()))
            {
                results.add(post);
            }
        }

        return results;
    }

    /**
     * Updates post's lowest bid in the database
     * @param id
     * @param bid
     */
    public void updateLowestBid(String id, Bid bid) {

        this.databaseReference.child("Posts").child(id).child("lowestBid").setValue(bid);
    }

}
