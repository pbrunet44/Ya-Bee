package com.example.yabeeprototypes;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

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
    private ArrayList<User> allBidders;
    private ArrayList<Notification> notifications;
    //private Bitmap imageBitmap;
    //private Uri imageUri; // will deal with this later
    private String category;
    private String condition;
    private String id;
    private Date postDate;
    private long auctionTimeLeft;
    private boolean isExpired;
    private int clicks;

    public Post()
    {
        super();
    }

    public Post(ArrayList<Notification> notifications, ArrayList<User> allBidders, User buyer, String title, double maxPrice, String description, int auctionLength, Bid lowestBid, String imageEncoding, String category, String condition, String id, Date postDate, boolean isExpired, int clicks)
    {
        this.setNotifications(notifications);
        this.setAllBidders(allBidders);
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
        this.setClicks(clicks);
        updateAuctionTimer(); // setting auction timer to start

    }

    private void setNotifications(ArrayList<Notification> notifications)
    {
        this.notifications = notifications;
    }

    private void setAllBidders(ArrayList<User> allBidders)
    {
        this.allBidders = allBidders;
    }

    public void addNotification(Notification notification)
    {
        if (this.notifications == null)
            this.notifications = new ArrayList<>();
        this.notifications.add(notification);
        DatabaseHelper databaseHelper = new DatabaseHelper();
        databaseHelper.updateNotifications(this.id, notifications);
    }

    public void addBiddertoList(User bidder)
    {
        if (this.allBidders == null) {
            this.allBidders = new ArrayList<>();
        }
        this.allBidders.add(bidder);
        DatabaseHelper databaseHelper = new DatabaseHelper();
        databaseHelper.updateBidders(this.id, this.allBidders);

    }

    public ArrayList<User> getAllBidders()
    {
        return this.allBidders;
    }

    public ArrayList<Notification> getNotifications()
    {
        return this.notifications;
    }

    public boolean alreadyBid(String uid)
    {
        if (this.allBidders != null && this.allBidders.size() != 0) {
            for (User user : this.allBidders) {
                if (user.getUid().equals(uid)) {
                    return true;
                }
            }
        }
        return false;
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
            if(this.isExpired = false)
            {
                this.isExpired = true;
                DatabaseHelper databaseHelper = new DatabaseHelper();
                databaseHelper.databaseReference.child("Posts").child(id).child("isExpired").setValue(this.isExpired);
                if(this.allBidders != null)
                {
                    for(User u: this.allBidders)
                    {
                        Notification notification = new Notification("EXPIRED", u);
                        this.addNotification(notification);
                    }
                }
            }
            return "AUCTION EXPIRED";
        }
        if(this.auctionTimeLeft > 24 * 60 * 60)
        {
            return (Long.toString(this.auctionTimeLeft / 24 / 60 / 60) + " days, " + Long.toString((this.auctionTimeLeft / 60 / 60) % 24) + " hours");
        }
        else if(this.auctionTimeLeft > 60 * 60)
        {
            return (Long.toString(this.auctionTimeLeft / 60 / 60)  + " hours, "  + Long.toString((this.auctionTimeLeft / 60) % 60) + " minutes" );
        }
        else if(this.auctionTimeLeft > 60)
        {
            return (Long.toString(this.auctionTimeLeft / 60) + " minutes, "
                    + Long.toString((this.auctionTimeLeft) % 60) + " seconds");
        }
        else
        {
            return (Long.toString(this.auctionTimeLeft) + " seconds");
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

    public int getClicks()
    {
        return this.clicks;
    }

    private void setClicks(int clicks)
    {
        this.clicks = clicks;
    }

    public void incrementClicksOnFirebase()
    {
        this.clicks++;
        DatabaseHelper databaseHelper = new DatabaseHelper();
        databaseHelper.databaseReference.child("Posts").child(id).child("clicks").setValue(this.clicks);
    }


    public String toString()
    {
        return String.format("Title: %s\nMax price: %f\n Description: %s\n", this.title, this.maxPrice, this.description);
    }
}
