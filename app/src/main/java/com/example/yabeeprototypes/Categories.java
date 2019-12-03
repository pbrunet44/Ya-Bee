package com.example.yabeeprototypes;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Categories extends AppCompatActivity
{
    private String category;
    private List<Post> postsByCategory;
    private DatabaseHelper database;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_items);

        findViewById(R.id.loadingPanelforCategories).setVisibility(View.VISIBLE);

        database = new DatabaseHelper();
        category = getIntent().getStringExtra("Category");

        database.getPosts(new FirebaseCallback() {
            @Override
            public void onCallback(List<Post> posts) {
                postsByCategory = database.getPostsByCategory(category, posts);

                if (postsByCategory.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "No posts have been made in this category!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    recyclerView = findViewById(R.id.recyclerViewforCategories);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setHasFixedSize(true);
                    PostAdapter postAdapter = new PostAdapter(R.layout.list_item, R.id.CategoriesContainer, postsByCategory);
                    recyclerView.setAdapter(postAdapter);
                }

                findViewById(R.id.loadingPanelforCategories).setVisibility(View.GONE);
            }
        });

    }
}
