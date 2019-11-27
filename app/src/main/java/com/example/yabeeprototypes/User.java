package com.example.yabeeprototypes;


import java.util.ArrayList;

public class User {

    private String email = null;
    private String uid = null;
    private ArrayList<Post> wishlist = new ArrayList<>();

    public User() {}

    public User(String email, String userID)
    {
        setEmail(email);
        setUid(userID);
    }

    public User(String email, String userID, ArrayList<Post> wishlist)
    {
        setEmail(email);
        setUid(userID);
        setWishlist(wishlist);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ArrayList<Post> getWishlist()
    {
        return this.wishlist;
    }

    public void setWishlist(ArrayList<Post> postIds)
    {
        this.wishlist = postIds;
    }

    public void addAllToWishlist(ArrayList<Post> post)
    {
        this.wishlist.addAll(post);
    }

    public boolean addToWishlist(Post post)
    {
        for (Post p: this.wishlist)
        {
            if (p.getId().equals(post.getId()))
            {
                return false;
            }
        }
        this.wishlist.add(post);
        return true;
    }

    public void clearWishlist(){
        this.wishlist.clear();
    }

    public void removeFromWishlist(Post post)
    {
        this.wishlist.remove(post);
    }




}
