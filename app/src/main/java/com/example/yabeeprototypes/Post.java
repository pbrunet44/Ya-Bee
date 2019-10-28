package com.example.yabeeprototypes;

import android.provider.ContactsContract;

public class Post {

    public String title;
    public double maxPrice;
    public String description;
    public int auctionLength;
    private Bid lowestBid;
    public String imageUrl; // will deal with this later
    public String category;
    //public String id;

    public Post()
    {
        super(); // filler, will change this later
    }

    public Post(String title, double maxPrice, String description, int auctionLength, String imageUrl, String category)
    {
        this.title = title;
        this.maxPrice = maxPrice;
        this.description = description;
        this.auctionLength = auctionLength;
        this.imageUrl = imageUrl;
        this.category = category;
        //this.id =
    }

    public void setLowestBid(Bid lowestBid)
    {
        this.lowestBid = lowestBid;
        // now update in database
        DatabaseHelper database = new DatabaseHelper();
        String path = "Posts/this.title/lowestBid".replace("this.title", this.title);
        database.updateLowestBid(path, this.lowestBid);
    }

}
