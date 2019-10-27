package com.example.yabeeprototypes;

public class MakeBid
{
    public double price = 0.00;
    public String description;
    public String imageURL;

    public void getBid(double price, String description, String imageURL)
    {
        this.price = price;
        this.description = description;
        this.imageURL = imageURL;
    }

    public boolean verifyBid()
    {
        // compare current bid to the lowest bid in the auction
        // if it is lower, allow bid i.e. let lowest bid become current price and return true
        // else refuse, and prompt user to enter an appropriate bid and return false;
        return false;
    }

    public double getLowestBid()
    {
        return 0.0;
    }

    public void setLowestBid()
    {
        // sets lowest bid
        // updates lowest bid price associated with post in database through database helper
    }

}
