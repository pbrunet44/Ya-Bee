package com.example.yabeeprototypes;

public class Post {

    public String title;
    public double maxPrice;
    public String description;
    public int auctionLength;
    public String imageUrl; // will deal with this later
    public String category;

    public Post()
    {
        super(); // filler, will change this later
    }

    public Post(String title, double maxPrice, String description, int auctionLength, String imageUrl, String category)
    {
        this.title = title;
        this.maxPrice = maxPrice;
        this.description = description;
        this.auctionLength = auctionLength;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public String toString()
    {
        return String.format("%s\n %f\n %s\n %d\n %s\n %s\n", this.title, this.maxPrice, this.description,
                this.auctionLength, this.imageUrl, this.category);
    }

}
