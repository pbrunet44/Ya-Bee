package com.example.yabeeprototypes;


import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseHelper {

    private final int NUM_DAILY_BUZZ_POSTS = 3;
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
                    System.out.println(post.getTitle() +  ", " + post.getDescription()); //Commented out since it gets in the way of debugging querying posts
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
     * @param keyword string to search titles for
     * @param posts List of posts
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
     * Orders posts based on clicks
     * @param posts List of posts
     * @return ArrayList of posts, sorted from most to least clicks
     */
    public ArrayList<Post> sortPostsByClicks(List<Post> posts)
    {
        ArrayList<Post> results = new ArrayList<>();
        results.addAll(posts);
        Collections.sort(results, new PostClickComparator());
        System.out.println("Inside sortPostsByClicks:");
//        for (Post post:results) {
//            System.out.println(post.toString());
//        }
        return results;
    }

    public ArrayList<Post> getDailyBuzz(List<Post> posts)
    {
        ArrayList<Post> sortedPosts = sortPostsByClicks(posts);
//        System.out.println("Inside getDailyBuzz:");
//        for (Post post:sortedPosts) {
//            System.out.println(post.toString());
//        }
        ArrayList<Post> results = new ArrayList<>();
        for (int i = 0; i < NUM_DAILY_BUZZ_POSTS; i++) {
            results.add(sortedPosts.get(i));
        }
        return results;
    }

    public void updateTitle(String postId, String title)
    {
        this.databaseReference.child("Posts").child(postId).child("title").setValue(title);
    }

    public void updateDescription(String postId, String description)
    {
        this.databaseReference.child("Posts").child(postId).child("description").setValue(description);
    }

    public void updateImage(String postId, String imageEncoding)
    {
        this.databaseReference.child("Posts").child(postId).child("imageEncoding").setValue(imageEncoding);
    }

    public void updateCondtion(String postId, String condition)
    {
        this.databaseReference.child("Posts").child(postId).child("condition").setValue(condition);
    }

    public void updateCategory(String postId, String category)
    {
        this.databaseReference.child("Posts").child(postId).child("category").setValue(category);
    }


    /**
     * Updates post's lowest bid in the database
     * @param id
     * @param bid
     */
    public void updateLowestBid(String id, Bid bid) {

        this.databaseReference.child("Posts").child(id).child("lowestBid").setValue(bid);
    }

    /**
     * Updates all bidders participating in auction in the database
     * @param id
     * @param bidders
     */
    public void updateBidders(String id, ArrayList<User> bidders)
    {
        this.databaseReference.child("Posts").child(id).child("allBids").setValue(bidders);
    }

    public void updatePrompts(String id, ArrayList<Prompt> prompts)
    {
        this.databaseReference.child("Posts").child(id).child("prompts").setValue(prompts);
    }


    /**
     * Returns a given user's posts that they've posted (used only for 'Buying' under Profile)
     * @param  uid
     * @param posts
     */
    public ArrayList<Post> getPostsByBuyer(String uid, List<Post> posts)
    {
        ArrayList<Post> results = new ArrayList<>();
        for (Post post: posts)
        {
            if (post.getBuyer().getUid().equals(uid))
            {
                results.add(post);
            }
        }
        return results;
    }

    public ArrayList<Prompt> getPromptsByUser(String uid, List<Post> posts)
    {
        ArrayList<Prompt> results = new ArrayList<>();
        for (Post post: posts)
        {
            if(post.getPrompts() != null)
            for(Prompt prompt: post.getPrompts())
            {
                if (prompt.getReceivingUser().getUid().equals(uid))
                {
                    results.add(prompt);
                }
            }
        }
        return results;
    }

    /**
     * Returns a given user's posts that they have placed bids in(used only for 'Selling' under Profile)
     * @param  uid
     * @param posts
     */
    public ArrayList<Post> getPostsBySeller(String uid, List<Post> posts)
    {
        ArrayList<Post> results = new ArrayList<>();
        for (Post post: posts)
        {
            if (post != null) {
                ArrayList<User> bidders = post.getAllBidders();
                if (bidders != null) {
                    for (User u : bidders) {
                        if (u.getUid().equals(uid)) {
                            results.add(post);
                        }
                    }
                }
            }
        }
        return results;
    }

}
