package com.example.yabeeprototypes;


public class Notification {

    private String typeofNotification;
    private User receivingUser;
    private String postID;

    public Notification() {}

    public Notification(String type, User user, String postID) {
        setTypeofNotification(type);
        setReceivingUser(user);
        setPostID(postID);
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getPostID() {
        return this.postID;
    }

    public void setTypeofNotification(String typeofNotification) {
        this.typeofNotification = typeofNotification;
    }

    public String getNotificationMessage()
    {
        String message = "";
        if(this.getTypeofNotification().equals("BID"))
        {
            message = "New current bid! Tap to view.";
        }
        else if(this.getTypeofNotification().equals("EXPIRED"))
        {
            message = "Auction has expired!";
        }
        else if(this.getTypeofNotification().equals("TINDER"))
        {
            message = "New bid on your post! Tap to view.";
        }
        else if(this.getTypeofNotification().equals("BID DECLINED"))
        {
            message = "Bid declined by buyer! Tap to view post.";
        }
        else if(this.getTypeofNotification().equals("BID ACCEPTED"))
        {
            message = "Bid accepted by buyer. You are the current bid! Tap to view post." ;
        }
        else if(this.getTypeofNotification().equals("WINNER"))
        {
            message = "You've won the auction!";
        }
        return message;
    }

    public String getTypeofNotification() {
        return this.typeofNotification;
    }

    public void setReceivingUser(User user) {
        this.receivingUser = user;
    }

    public User getReceivingUser() {
        return this.receivingUser;
    }
}
