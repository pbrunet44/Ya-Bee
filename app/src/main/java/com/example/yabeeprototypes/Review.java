package com.example.yabeeprototypes;

import com.google.firebase.database.DataSnapshot;

public class Review
{
    private String title;
    private String description;
    private float rating;
    private String reviewedId;
    private String posterId;

    public Review(String rTitle, String rDescription, float rRating, String rId, String pId)
    {
        setTitle(rTitle);
        setDescription(rDescription);
        setRating(rRating);
        setReviewedId(rId);
        setPosterId(pId);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReviewedId() {
        return reviewedId;
    }

    public void setReviewedId(String reviewedId) {
        this.reviewedId = reviewedId;
    }

    public String getPosterId() {
        return posterId;
    }

    public void setPosterId(String posterId) {
        this.posterId = posterId;
    }

    public String toString()
    {
        return "Here's the title of the review" + this.title
                + "\nDescription: " + this.description
                + "\nRating" + this.rating
                + "\nReviewed: " + this.reviewedId
                + "\nPoster: " + this.posterId;

    }

}

