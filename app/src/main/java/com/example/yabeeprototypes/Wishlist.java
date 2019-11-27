package com.example.yabeeprototypes;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class Wishlist extends AppCompatActivity
{
    private RecyclerView recyclerView;
    ArrayList<Post> wishlist;
    private DatabaseHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wishlist_items);
        findViewById(R.id.loadingPanelforWishlist).setVisibility(View.VISIBLE);
        database = new DatabaseHelper();
        wishlist = new ArrayList<>();
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


        database.getUsers(new UserCallback() {
            @Override
            public void onCallback(List<User> users) {
                final User user = database.getUserById(currentUser.getUid(), users);
                    database.getPosts(new FirebaseCallback() {
                        @Override
                        public void onCallback(List<Post> posts) {
                            for (String postId: user.getWishlist())
                            {
                                wishlist.add(database.getPostByID(postId, posts));
                            }
                            System.out.println("Here is the size of the user's wishlist: " + wishlist.size());
                            recyclerView = (RecyclerView) findViewById(R.id.recyclerViewforWishlist);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setHasFixedSize(true);
                            PostAdapter adapter = new PostAdapter(R.id.WishlistContainer, wishlist);
                            recyclerView.setAdapter(adapter);

                            findViewById(R.id.loadingPanelforWishlist).setVisibility(View.GONE);
                        }
                    });
                }

        });




 /*       database.getUsers(new UserCallback() {
            @Override
            public void onCallback(List<User> users) {
                User user = database.getUserById(currentUser.getUid(), users);
                wishlist = user.getWishlist();

                System.out.println("Here is the size of the user's wishlist: " + wishlist.size());
                recyclerView = (RecyclerView) findViewById(R.id.recyclerViewforWishlist);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setHasFixedSize(true);
                PostAdapter adapter = new PostAdapter(R.id.WishlistContainer, wishlist);
                recyclerView.setAdapter(adapter);

                findViewById(R.id.loadingPanelforWishlist).setVisibility(View.GONE);
            }
        });*/


    }

}
