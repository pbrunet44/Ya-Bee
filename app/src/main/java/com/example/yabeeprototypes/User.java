package com.example.yabeeprototypes;


import java.util.ArrayList;

public class User {

    private String email = null;
    private String uid = null;
    private ArrayList<String> wishlist = new ArrayList<>();

    public User() {}

    public User(String email, String userID)
    {
        setEmail(email);
        setUid(userID);
    }

    public User(String email, String userID, ArrayList<String> wishlist)
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

    public ArrayList<String> getWishlist()
    {
        return this.wishlist;
    }

    public void setWishlist(ArrayList<String> postIds)
    {
        this.wishlist = postIds;
    }

    public void addAllToWishlist(ArrayList<String> post)
    {
        this.wishlist.addAll(post);
    }

    public boolean addToWishlist(String postId)
    {
        for (String pid: this.wishlist)
        {
            if (pid.equals(postId))
            {
                return false;
            }
        }
        this.wishlist.add(postId);
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
