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
}
