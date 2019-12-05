package com.example.yabeeprototypes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ReviewUser extends AppCompatActivity {

    private String title;
    private String description;
    private float rating;

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() == null)
        {
            Intent intent = new Intent(ReviewUser.this, AccountOptions.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_user);
        final DatabaseHelper database = new DatabaseHelper();

        Intent intent = getIntent();
        final String reviewedId = intent.getStringExtra("uid");

        final TextView titleReview = findViewById(R.id.review_title);
        final TextView descriptionReview = findViewById(R.id.description_review);
        final RatingBar starReview = findViewById(R.id.ratingBar);
        Button submitReview = findViewById(R.id.submitReview);

        submitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = titleReview.getText().toString();
                description = descriptionReview.getText().toString();
                rating = starReview.getRating();

                Review review = new Review(title, description, rating, reviewedId, FirebaseAuth.getInstance().getUid());
                database.addReview(review);
                Toast.makeText(getApplicationContext(), "Review submitted!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }




}
