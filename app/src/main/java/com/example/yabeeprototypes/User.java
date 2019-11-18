package com.example.yabeeprototypes;


public class User {

    private String email = null;
    private String uid = null;

    public User() {}

    public User(String email, String userID)
    {
        setEmail(email);
        setUid(userID);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
