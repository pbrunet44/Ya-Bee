package com.example.yabeeprototypes;


public class Prompt {

    private String typeofNotification;
    private User receivingUser;

    public Prompt() {}
    public Prompt(String type, User user) {
        setTypeofNotification(type);
        setReceivingUser(user);
    }

    public void setTypeofNotification(String typeofNotification) {
        this.typeofNotification = typeofNotification;
    }

    public String getNotificationMessage()
    {
        if(this.getTypeofNotification().equals("bid"))
        {
            return "The current bid has been outbid!";
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
