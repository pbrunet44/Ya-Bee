package com.example.yabeeprototypes;

import android.provider.ContactsContract;
import java.util.Date;

public class Post {

    static final double INITIAL_BID_PRICE = Double.MAX_VALUE;
    static final String INITIAL_IMAGE = "";
    static final String INITIAL_DESCRIPTION = "";
    private final static long MILLISECONDS_PER_DAY = 1000L * 60 * 60 * 24;
    private final static long MILLISECONDS_PER_MINUTE = 1000L * 60;
    private String title;
    private double maxPrice;
    private String description;
    private int auctionLength;
    private Bid lowestBid;
    private String imageUrl; // will deal with this later
    private String category;
    private String id;
    private Date postDate;
    private long auctionTimeLeft;
    private boolean isExpired;

    public Post()
    {
        super();
    }

    public Post(String title, double maxPrice, String description, int auctionLength, Bid lowestBid, String imageUrl, String category, String id, Date postDate, boolean isExpired)
    {
        this.setTitle(title);
        this.setMaxPrice(maxPrice);
        this.setDescription(description);
        this.setAuctionLength(auctionLength);
        this.lowestBid = lowestBid;
        this.setImageUrl(imageUrl);
        this.setCategory(category);
        this.setId(id);
        this.setPostDate(postDate);
        this.setExpired(isExpired);
        updateAuctionTimer(); // setting auction timer to start
    }

    public boolean verifyBid(Bid newBid)
    {
        // compare current bid to the lowest bid in the auction
        // if it is lower, allow bid i.e. let lowest bid become current price and return true
        // else refuse, and prompt user to enter an appropriate bid and return false;
        boolean accept = false;
        if (this.lowestBid == null || Double.compare(newBid.price, this.lowestBid.price) < 0)
            accept = true; // if lowest bid > bid, accept
        // anything else will not be accepted
        return accept;
    }

    public void updateNewLowestBid(Bid lowestBid)
    {
        this.lowestBid = lowestBid;
        // now update in database
        DatabaseHelper database = new DatabaseHelper();
        database.updateLowestBid(this.id, this.lowestBid);
    }

    public Bid getLowestBid() {
        return lowestBid;
    }

    /**
     * Initializes the auction timer when posts are loaded
     */
    public void updateAuctionTimer()
    {
        Date expireDate = new Date(this.postDate.getTime());
        long expireTime = expireDate.getTime();
        expireTime += (MILLISECONDS_PER_MINUTE * this.auctionLength);
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
        return (String.format("%02d", (this.auctionTimeLeft / 60 / 60))
                + ":" + String.format("%02d", (this.auctionTimeLeft / 60) % 60)
                + ":" + String.format("%02d", this.auctionTimeLeft % 60));
    }

    public String getTitle() {
        return title;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    private void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    private void setPostDate(Date postDate) {this.postDate = postDate;}

    public Date getPostDate() {return this.postDate;}

    public int getAuctionLength() {
        return auctionLength;
    }

    private void setAuctionLength(int auctionLength) {
        this.auctionLength = auctionLength;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    private void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    private void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    /*public Date getPostDate() {
        return postDate;
    }

    private void setPostDate(Date postDate) {
        this.postDate = postDate;
    }*/

    public long getAuctionTimeLeft() {
        return auctionTimeLeft;
    }

    private void setAuctionTimeLeft(long auctionTimeLeft) {
        this.auctionTimeLeft = auctionTimeLeft;
    }

    public boolean isExpired() {
        return isExpired;
    }

    private void setExpired(boolean expired) {
        isExpired = expired;
    }

    public String toString()
    {
        return String.format("Title: %s\nMax price: %f\n Description: %s\n", this.title, this.maxPrice, this.description);
    }
}
