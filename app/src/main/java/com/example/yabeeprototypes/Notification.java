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
            message = "New current bid!";
        }
        else if(this.getTypeofNotification().equals("EXPIRED"))
        {
            message = "Auction has expired!";
        }
        else if(this.getTypeofNotification().equals("TINDER"))
        {
            message = "New bid on your post!";
        }
        else if(this.getTypeofNotification().equals("BID DECLINED"))
        {
            message = "Bid declined by buyer.";
        }
        else if(this.getTypeofNotification().equals("BID ACCEPTED"))
        {
            message = "Bid accepted by buyer. You are the current bid!" ;
        }
        else if(this.getTypeofNotification().equals("WINNER"))
        {
            message = "You've won the auction!";
        }
        else if(this.getTypeofNotification().equals("HAHA LOSER"))
        {
            message = "Auction lost.";
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
