package com.example.yabeeprototypes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Date;

public class Browse extends AppCompatActivity implements PostAdapter.PostClicked{

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_search);

        /*recyclerView = findViewById(R.id.browselist);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager( this);
        recyclerView.setLayoutManager(layoutManager);
        Bid bid = new Bid(50.0, "description", "");
        posts = new ArrayList<Post>();
        posts.add(new Post("Clean Code", 28.50, "description", 2, bid, "","something", "Books", "1", new Date(), false));
        posts.add(new Post("Cracking the Coding Interview", 32, "description", 2, bid, "","something", "Books", "2", new Date(), false));
        posts.add(new Post("Java Illuminated", 124, "description", 2, bid,"","something", "Books", "3", new Date(), false));
        posts.add(new Post("Clean Code", 28.5, "description", 2, bid, "","something", "Books", "4", new Date(), false));
        posts.add(new Post("Cracking the Coding Interview", 32, "description", 2, bid, "","something", "Books", "5", new Date(), false));
        posts.add(new Post("Java Illuminated", 124, "description", 2, bid,"","something", "Books", "6", new Date(), false));
        posts.add(new Post("Clean Code", 28.50, "description", 2, bid,"","something", "Books", "7", new Date(), false));
        posts.add(new Post("Cracking the Coding Interview", 32, "description", 2, bid, "","something", "Books", "8", new Date(), false));
        posts.add(new Post("Java Illuminated", 124, "description", 2, bid, "","something", "Books", "9", new Date(), false));
        posts.add(new Post("Clean Code", 28.5, "description", 2, bid, "","something", "Books", "10", new Date(), false));
        posts.add(new Post("Cracking the Coding Interview", 32, "description", 2, bid ,"","something", "Books", "11", new Date(), false));
        posts.add(new Post("Java Illuminated", 124, "description", 2, bid, "","something", "Books", "12", new Date(), false));

        myAdapter = new PostAdapter(this, posts);

        recyclerView.setAdapter(myAdapter);*/
    }

    /*public void goToViewPost(View view)
    {
        Intent intent = new Intent(this, ViewPost.class);
        startActivity(intent);
    }*/

    @Override
    public void onPostClicked(int index)
    {
        //goToViewPost();
    }
}
