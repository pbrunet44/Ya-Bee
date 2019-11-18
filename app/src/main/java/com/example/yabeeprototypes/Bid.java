package com.example.yabeeprototypes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Bid
{
    public double price = 0.00;
    public String description;
    public String imageEncoding;
    public User seller;

    //public String id; //do bids need unique ids since we're only keeping track of the lowest bid?

    public Bid() {}
    public Bid(double price, String description, String imageEncoding, User newUser)
    {
        this.price = price;
        this.description = description;
        this.imageEncoding = imageEncoding;
        this.seller = newUser;
        //this.id = id;
    }

    /**
     * Decodes the string representing the bid's image (stored on Firebase using Base64 encoding)
     * @return the Bitmap object for the image
     */
    public Bitmap decodeImage()
    {
        byte[] decoded = Base64.decode(this.imageEncoding.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
    }



}
