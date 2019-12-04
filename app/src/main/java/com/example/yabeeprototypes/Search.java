package com.example.yabeeprototypes;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Search extends AppCompatActivity
{
    private DatabaseHelper database;
    private RecyclerView recyclerView;
    private String query = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items_browse_results);
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        database = new DatabaseHelper();

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            query = intent.getStringExtra(SearchManager.QUERY);
             //perform query by calling databasehelper class
            database.getPosts(new FirebaseCallback() {
                @Override
                public void onCallback(List<Post> posts) {
                    posts = database.getPostsByTitle(query, posts);
                    recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setHasFixedSize(true);
                    PostAdapter adapter = new PostAdapter(R.layout.list_item, R.id.brosweContainer, posts);
                    recyclerView.setAdapter(adapter);
                    findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Unsuccessful search. Please try again!", Toast.LENGTH_LONG).show();
        }
    }

}
