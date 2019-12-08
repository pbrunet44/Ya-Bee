package com.example.yabeeprototypes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Bid
{
    private double price = 0.00;
    private String description;
    private String imageEncoding;
    private User seller;

    //public String id; //do bids need unique ids since we're only keeping track of the lowest bid?

    public Bid() {}
    public Bid(double price, String description, String imageEncoding, User newUser)
    {
        setPrice(price);
        setDescription(description);
        setImageEncoding(imageEncoding);
        setSeller(newUser);
        //this.id = id;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public double getPrice()
    {
        return this.price;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return this.description;
    }

    public void setImageEncoding(String imageEncoding) {
        this.imageEncoding = imageEncoding;
    }

    public String getImageEncoding() {
        return imageEncoding;
    }

    public void setSeller(User seller)
    {
        this.seller = seller;
    }

    public User getSeller() {
        return seller;
    }

    /**
     * Decodes the string representing the bid's image (stored on Firebase using Base64 encoding)
     * @return the Bitmap object for the image
     */
    public Bitmap decodeImage()
    {
        byte[] decoded = Base64.decode(getImageEncoding().getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
    }

}
