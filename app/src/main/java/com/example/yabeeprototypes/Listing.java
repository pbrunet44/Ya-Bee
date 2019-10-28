package com.example.yabeeprototypes;

public class Listing {

    private String name;
    private String description;
    private double currentBid;
    private String imageURL;

    public void setListing (String name, String description, double currentBid, String imageURL )
    {
        this.name = name;
        this.description = description;
        this.currentBid = currentBid;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getCurrentBid() {
        return currentBid;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCurrentBid(double currentBid) {
        this.currentBid = currentBid;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
