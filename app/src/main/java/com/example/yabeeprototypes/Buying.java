package com.example.yabeeprototypes;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class Buying extends AppCompatActivity
{
    private DatabaseHelper database;
    private RecyclerView recyclerView;
    ArrayList<Post> buying;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items_buying);

        findViewById(R.id.loadingPanelforBuying).setVisibility(View.VISIBLE);

        database = new DatabaseHelper();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        buying = new ArrayList<>();

        database.getPosts(new FirebaseCallback() {
            @Override
            public void onCallback(List<Post> posts) {
                buying = database.getPostsByBuyer(currentUser.getUid(), posts);
                System.out.println("Here is the number of posts this user has posted: " + buying.size());
                if (buying.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "You have not made any posts!", Toast.LENGTH_SHORT).show();
                }
                else {
                    recyclerView = (RecyclerView) findViewById(R.id.recyclerViewforBuying);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setHasFixedSize(true);
                    PostAdapter adapter = new PostAdapter(R.id.BuyingContainer, buying);
                    recyclerView.setAdapter(adapter);
                }

                findViewById(R.id.loadingPanelforBuying).setVisibility(View.GONE);
            }
        });
    }
}
