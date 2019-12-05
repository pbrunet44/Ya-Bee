package com.example.yabeeprototypes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class ViewUserReviews extends AppCompatActivity {

    private DatabaseHelper database;
    private FirebaseUser currentUser;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_reviews);

        findViewById(R.id.loadingPanelforReviews).setVisibility(View.VISIBLE);

        database = new DatabaseHelper();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        database.getReviews(new ReviewCallback() {
            @Override
            public void onCallback(List<Review> reviews) {
                System.out.println("Size of reviews: " + reviews.size());
                if (reviews.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "You have no reviews!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    recyclerView = (RecyclerView) findViewById(R.id.recyclerViewforReviews);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setHasFixedSize(false);
                    ReviewAdapter adapter = new ReviewAdapter(reviews, R.layout.review_list_item);
                    recyclerView.setAdapter(adapter);
                }

                findViewById(R.id.loadingPanelforReviews).setVisibility(View.GONE);
            }
        });



    }
}
