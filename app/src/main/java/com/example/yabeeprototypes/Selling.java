package com.example.yabeeprototypes;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class Selling extends AppCompatActivity
{
    private DatabaseHelper database;
    private RecyclerView recyclerView;
    ArrayList<Post> selling;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items_selling);

        findViewById(R.id.loadingPanelforSelling).setVisibility(View.VISIBLE);

        database = new DatabaseHelper();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        selling = new ArrayList<>();

        database.getPosts(new FirebaseCallback() {
            @Override
            public void onCallback(List<Post> posts) {
                selling = database.getPostsBySeller(currentUser.getUid(), posts);
                System.out.println("Here is selling size of posts: " + selling.size());
                if (selling.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "You have not placed any bids!", Toast.LENGTH_SHORT).show();
                }
                else {
                    System.out.println("Here is the number of posts this user has posted: " + selling.size());
                    recyclerView = (RecyclerView) findViewById(R.id.recyclerViewforSelling);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setHasFixedSize(true);
                    PostAdapter adapter = new PostAdapter(R.id.SellingContainer, selling);
                    recyclerView.setAdapter(adapter);
                }

                findViewById(R.id.loadingPanelforSelling).setVisibility(View.GONE);
            }
        });
    }
}
