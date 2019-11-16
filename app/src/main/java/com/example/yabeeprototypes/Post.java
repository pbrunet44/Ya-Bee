package com.example.yabeeprototypes;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.util.Base64;

import com.google.firebase.database.Exclude;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;

public class Post {

    static final double INITIAL_BID_PRICE = Double.MAX_VALUE;
    static final String INITIAL_IMAGE = "";
    static final String INITIAL_DESCRIPTION = "";
    private final static long MILLISECONDS_PER_DAY = 1000L * 60 * 60 * 24;
    private final static long MILLISECONDS_PER_MINUTE = 1000L * 60;
    private String title;
    private double maxPrice;
    private User buyer;
    private String description;
    private int auctionLength;
    private Bid lowestBid;
    private String imageEncoding;
    private ArrayList<Bid> allBids;
    //private Bitmap imageBitmap;
    //private Uri imageUri; // will deal with this later
    private String category;
    private String condition;
    private String id;
    private Date postDate;
    private long auctionTimeLeft;
    private boolean isExpired;

    public Post()
    {
        super();
    }

    public Post(User buyer, String title, double maxPrice, String description, int auctionLength, Bid lowestBid, String imageEncoding, String category, String condition, String id, Date postDate, boolean isExpired)
    {
        this.allBids = new ArrayList<>();
        this.setTitle(title);
        this.setMaxPrice(maxPrice);
        this.setDescription(description);
        this.setAuctionLength(auctionLength);
        this.lowestBid = lowestBid;
        this.setImageEncoding(imageEncoding);
        this.setCondition(condition);
        this.setCategory(category);
        this.setId(id);
        this.setPostDate(postDate);
        this.setExpired(isExpired);
        this.setBuyer(buyer);
        updateAuctionTimer(); // setting auction timer to start
    }

    private void addBidtoList(Bid bid)
    {
        this.allBids.add(bid);
    }
    private void setBuyer(User buyer)
    {
        this.buyer = buyer;
    }

    public User getBuyer()
    {
        return buyer;
    }
    /**
     * Decodes the string representing the post's image (stored on Firebase using Base64 encoding)
     * @return the Bitmap object for the image
     */
    public Bitmap decodeImage()
    {
        byte[] decoded = Base64.decode(this.imageEncoding.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
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
        if(this.auctionTimeLeft > 24 * 60 * 60)
        {
            return ("Days: " + Long.toString(this.auctionTimeLeft / 24 / 60 / 60)
                    + " Hours: " + Long.toString((this.auctionTimeLeft / 60 / 60) % 24));
        }
        else if(this.auctionTimeLeft > 60 * 60)
        {
            return ("Hours: " + Long.toString(this.auctionTimeLeft / 60 / 60)
                    + " Minutes: " + Long.toString((this.auctionTimeLeft / 60) % 60));
        }
        else if(this.auctionTimeLeft > 60)
        {
            return ("Minutes: " + Long.toString(this.auctionTimeLeft / 60)
                    + " Seconds: " + Long.toString((this.auctionTimeLeft) % 60));
        }
        else
        {
            return "Seconds: " + Long.toString(this.auctionTimeLeft);
        }
        /*return (String.format("%02d", (this.auctionTimeLeft / 60 / 60))
                + ":" + String.format("%02d", (this.auctionTimeLeft / 60) % 60)
                + ":" + String.format("%02d", this.auctionTimeLeft % 60));*/
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

    /*public Uri getImageUri() {
        return imageUri;
    }

    private void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    private void setImageBitmap(Bitmap imageBitmap){this.imageBitmap = imageBitmap;}

    public Bitmap getImageBitmap(){return this.imageBitmap;}
*/
    private void setImageEncoding(String imageEncoding){this.imageEncoding  = imageEncoding;}
    public String getImageEncoding(){return this.imageEncoding;}

    public String getCategory() {
        return category;
    }

    private void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    private void setCondition(String condition)
    {
        this.condition = condition;
    }
    public String getCondition()
    {
        return this.condition;
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
