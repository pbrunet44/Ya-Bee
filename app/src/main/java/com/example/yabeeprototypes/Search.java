package com.example.yabeeprototypes;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Search extends AppCompatActivity
{

    private DatabaseHelper database;
    private Post[] results;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_results);
        database = new DatabaseHelper();
        // Get the intent, verify the action and get the query
        results = new Post[1000000];
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            final String query = intent.getStringExtra(SearchManager.QUERY);
            List<Post> posts;
            // perform query by calling databasehelper class
            database.getPosts(new FirebaseCallback() {
                @Override
                public void onCallback(List<Post> posts) {
                    posts = database.getPostsByTitle(query, posts);
                    int i = 0;
                    for (Post p: posts)
                    {
                        results[i] = p;
                        i++;
                    }
                }
            });
        }
        else
        {
            Toast searchToast = Toast.makeText(getApplicationContext(), "Error in search", Toast.LENGTH_LONG);
            searchToast.setGravity(Gravity.TOP, 0, 0);
            searchToast.show();
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        PostAdapter adapter = new PostAdapter(results);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
