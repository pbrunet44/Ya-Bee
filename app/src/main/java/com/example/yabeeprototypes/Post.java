package com.example.yabeeprototypes;

import android.provider.ContactsContract;

public class Post {

    public String title;
    public double maxPrice;
    public String description;
    public int auctionLength;
    public Bid lowestBid;
    public String imageUrl; // will deal with this later
    public String category;
    public String id;

    public Post(String title, double maxPrice, String description, int auctionLength, String imageUrl, String category, String id)
    {
        this.title = title;
        this.maxPrice = maxPrice;
        this.description = description;
        this.auctionLength = auctionLength;
        this.imageUrl = imageUrl;
        this.category = category;
        this.id = id;
    }

    public boolean verifyBid(Bid newBid)
    {
        // compare current bid to the lowest bid in the auction
        // if it is lower, allow bid i.e. let lowest bid become current price and return true
        // else refuse, and prompt user to enter an appropriate bid and return false;
        boolean accept = false;
        if (Double.compare(newBid.price, this.lowestBid.price) < 0)
            accept = true; // if lowest bid > bid, accept
        // anything else will not be accepted
        return accept;
    }

    public void setLowestBid(Bid lowestBid)
    {
        this.lowestBid = lowestBid;
        // now update in database
        DatabaseHelper database = new DatabaseHelper();
        String path = "Posts/this.title/lowestBid".replace("this.title", this.title);
        path = "Posts/id/lowestBid".replace("id", this.id);
        database.updateLowestBid(path, this.lowestBid);
    }



}
