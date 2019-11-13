package com.example.yabeeprototypes;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;


public class Browse extends Fragment implements PostAdapter.PostClicked{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;


    /*final DatabaseHelper database = new DatabaseHelper();
    final ArrayList<Post> query = new ArrayList<>();
    final String title = "Bike";*/


    //private ArrayList<Post> posts;

    private final ArrayList<Post> posts = new ArrayList<>();
    private final String title = "Bike";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_browse_search, container, false);
        final DatabaseHelper database = new DatabaseHelper();



        database.getPosts(new FirebaseCallback() {
            @Override
            public void onCallback(List<Post> postsList) {
                posts.addAll(database.getPostsByTitle(title, postsList));
            }
        });
        for (Post post : posts) {
            System.out.println(post.toString());
        }


        /*for(Post post:query)
        {
            System.out.println(post.getTitle() + ", " + post.getDescription());
        }*/
        recyclerView = (RecyclerView) view.findViewById(R.id.browselist); //view.findViewById

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        /*Bid bid = new Bid(50.0, "description", "");
        posts = new ArrayList<Post>();
        posts.add(new Post("Clean Code", 28.50, "description", 2, bid, "","something", "Books", "1", new Date(), false));
        posts.add(new Post("Cracking the Coding Interview", 32, "description", 2, bid, "","something", "Books", "2", new Date(), false));
        posts.add(new Post("Java Illuminated", 124, "description", 2, bid,"","something", "Books", "3", new Date(), false));
        posts.add(new Post("Clean Code", 28.50, "description", 2, bid, "","something", "Books", "4", new Date(), false));
        posts.add(new Post("Cracking the Coding Interview", 32, "description", 2, bid, "","something", "Books", "5", new Date(), false));
        posts.add(new Post("Java Illuminated", 124, "description", 2, bid,"","something", "Books", "6", new Date(), false));
        posts.add(new Post("Clean Code", 28.50, "description", 2, bid,"","something", "Books", "7", new Date(), false));
        posts.add(new Post("Cracking the Coding Interview", 32, "description", 2, bid, "","something", "Books", "8", new Date(), false));
        posts.add(new Post("Java Illuminated", 124, "description", 2, bid, "","something", "Books", "9", new Date(), false));
        posts.add(new Post("Clean Code", 28.50, "description", 2, bid, "","something", "Books", "10", new Date(), false));
        posts.add(new Post("Cracking the Coding Interview", 32, "description", 2, bid ,"","something", "Books", "11", new Date(), false));
        posts.add(new Post("Java Illuminated", 124, "description", 2, bid, "","something", "Books", "12", new Date(), false));*/
        myAdapter = new PostAdapter(this.getContext(), posts);
        recyclerView.setAdapter(myAdapter);
        return view;
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
