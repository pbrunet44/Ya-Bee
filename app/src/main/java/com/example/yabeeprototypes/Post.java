package com.example.yabeeprototypes;

import android.provider.ContactsContract;
import java.util.Date;

public class Post {

    private final static long MILLISECONDS_PER_DAY = 1000L * 60 * 60 * 24;
    public String title;
    public double maxPrice;
    public String description;
    public int auctionLength;
    public Bid lowestBid;
    public String imageUrl; // will deal with this later
    public String category;
    public String id;
    // this has to be a long since Firebase does not support Date class objects
    public long postDate;
    public long auctionTimeLeft;
    public boolean isExpired;

    public Post()
    {
        super();
    }

    public Post(String title, double maxPrice, String description, int auctionLength, Bid lowestBid, String imageUrl, String category, String id, long postDate, boolean isExpired)
    {
        this.title = title;
        this.maxPrice = maxPrice;
        this.description = description;
        this.auctionLength = auctionLength;
        this.lowestBid = lowestBid;
        this.imageUrl = imageUrl;
        this.category = category;
        this.id = id;
        this.postDate = postDate;
        this.isExpired = isExpired;
        startAuctionTimer(); // setting auction timer to start
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

    public Bid getLowestBid() {
        return lowestBid;
    }

    /**
     * Initializes the auction timer when posts are loaded
     */
    public void startAuctionTimer()
    {
        Date d = new Date(this.postDate);
        Date expireDate = new Date(d.getTime());
        long expireTime = expireDate.getTime();
        expireTime += (MILLISECONDS_PER_DAY * this.auctionLength);
        expireDate.setTime(expireTime);
        Date currentDate = new Date();
        this.auctionTimeLeft = (expireDate.getTime() - currentDate.getTime()) / 1000; //Convert time left from milliseconds to seconds
    }

    /**
     * Gets the current auction timer as a string
     * @return The timer in format Days: d, Hrs: h, Mins: m or AUCTION EXPIRED if it's over
     */
    public String getAuctionTimer()
    {
        if (this.auctionTimeLeft <= 0)
        {
            this.isExpired = true;
            return "AUCTION EXPIRED";
        }
        return ("Days: " + Long.toString(this.auctionTimeLeft/60/60/24)
                + ", Hrs: " + Long.toString((this.auctionTimeLeft / 60 / 60) % 24)
                + ", Mins: " + Long.toString((this.auctionTimeLeft / 60) % 60));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAuctionLength() {
        return auctionLength;
    }

    public void setAuctionLength(int auctionLength) {
        this.auctionLength = auctionLength;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /*public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }*/

    public long getAuctionTimeLeft() {
        return auctionTimeLeft;
    }

    public void setAuctionTimeLeft(long auctionTimeLeft) {
        this.auctionTimeLeft = auctionTimeLeft;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }
}
