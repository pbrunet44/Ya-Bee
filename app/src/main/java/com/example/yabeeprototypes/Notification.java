package com.example.yabeeprototypes;


public class Notification {

    private String typeofNotification;
    private User receivingUser;

    public Notification() {}

    public Notification(String type, User user) {
        setTypeofNotification(type);
        setReceivingUser(user);
    }

    public void setTypeofNotification(String typeofNotification) {
        this.typeofNotification = typeofNotification;
    }

    public String getNotificationMessage()
    {
        if(this.getTypeofNotification().equals("BID"))
        {
            return "New current bid!";
        }
        else if(this.getTypeofNotification().equals("EXPIRED"))
        {
            return "Auction has expired!";
        }
        return "";
    }

    public String getTypeofNotification() {
        return typeofNotification;
    }

    public void setReceivingUser(User user) {
        this.receivingUser = user;
    }

    public User getReceivingUser() {
        return receivingUser;
    }
}
