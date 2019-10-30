package com.example.yabeeprototypes;

public class Bid
{
    public double price = 0.00;
    public String description;
    public String imageURL;
    public String id; //this is to ensure all bids have a unique id

    public Bid(double price, String description, String imageURL, String id)
    {
        this.price = price;
        this.description = description;
        this.imageURL = imageURL;
        this.id = id;
    }

}
