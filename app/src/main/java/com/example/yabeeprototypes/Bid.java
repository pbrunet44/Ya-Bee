package com.example.yabeeprototypes;

public class Bid
{
    public double price = 0.00;
    public String description;
    public String imageURL;
    public String id; //this is to ensure all bids have a unique id

    public void setBid(double price, String description, String imageURL, String id)
    {
        this.price = price;
        this.description = description;
        this.imageURL = imageURL;
        this.id = id;
    }

    public boolean verifyBid(Bid lowestbid, int bid)
    {
        // compare current bid to the lowest bid in the auction
        // if it is lower, allow bid i.e. let lowest bid become current price and return true
        // else refuse, and prompt user to enter an appropriate bid and return false;
        boolean accept = false;
        if (Double.compare(lowestbid.price, bid) > 0)
            accept = true; // if lowest bid > bid, accept
        // anything else will not be accepted
        return accept;
    }

}
