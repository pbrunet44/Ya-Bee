package com.example.yabeeprototypes;

public class Bid
{
    public double price = 0.00;
    public String description;
    public String imageURL;
    //public String id; //do bids need unique ids since we're only keeping track of the lowest bid?

    public Bid() {}
    public Bid(double price, String description, String imageURL)
    {
        this.price = price;
        this.description = description;
        this.imageURL = imageURL;
        //this.id = id;
    }

}
