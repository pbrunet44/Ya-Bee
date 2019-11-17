package com.example.yabeeprototypes;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchResults extends AppCompatActivity
{
    private static int MAX_NUMBER_OF_POSTS = 1000000;
    private Post[] listData;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_results);
        listData = new Post[MAX_NUMBER_OF_POSTS];
        DatabaseHelper database = new DatabaseHelper();
        database.getPosts(new FirebaseCallback() {
            @Override
            public void onCallback(List<Post> posts) {
                int i = 0;
                for (Post p: posts)
                {
                    listData[i] = p;
                    i++;
                }
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        PostAdapter adapter = new PostAdapter(listData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}
