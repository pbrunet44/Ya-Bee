package com.example.yabeeprototypes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.Date;

public class ItemsSelling extends AppCompatActivity implements PostAdapter.PostClicked{

    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Post> posts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_selling);

        recyclerView = findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager( this);
        recyclerView.setLayoutManager(layoutManager);

        posts = new ArrayList<Post>();
//        posts.add(new Post("Clean Code", 28.50, "description", 2, null,null, "Books","New", "1", new Date(), false));
//        posts.add(new Post("Cracking the Coding Interview", 32, "description", 2, null,null, "Books","New", "2", new Date(), false));
//        posts.add(new Post("Java Illuminated", 124, "description", 2, null,null, "Books","New", "3", new Date(), false));
//        posts.add(new Post("Clean Code", 28.5, "description", 2, null,null, "Books","New", "4", new Date(), false));
//        posts.add(new Post("Cracking the Coding Interview", 32, "description", 2, null,null, "Books","New", "5", new Date(), false));
//        posts.add(new Post("Java Illuminated", 124, "description", 2, null,null, "Books","New", "6", new Date(), false));
//        posts.add(new Post("Clean Code", 28.50, "description", 2, null,null, "Books","New", "7", new Date(), false));
//        posts.add(new Post("Cracking the Coding Interview", 32, "description", 2, null,null, "Books","New", "8", new Date(), false));
//        posts.add(new Post("Java Illuminated", 124, "description", 2, null,null, "Books","New", "9", new Date(), false));
//        posts.add(new Post("Clean Code", 28.5, "description", 2, null,null, "Books","New", "10", new Date(), false));
//        posts.add(new Post("Cracking the Coding Interview", 32, "description", 2, null,null, "Books","New", "11", new Date(), false));
//        posts.add(new Post("Java Illuminated", 124, "description", 2, null,null, "Books","New", "12", new Date(), false));

        myAdapter = new PostAdapter(this,posts);

        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public void onPostClicked(int index) {

        //implement action for clicked post
    }
}
