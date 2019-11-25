package com.example.yabeeprototypes;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;


//RecyclerView List of prompts for user
public class Notifications extends Fragment {

    private static int MAX_NUMBER_OF_NOTIFICATIONS = 1000000;
    private DatabaseHelper database;
    private FirebaseUser currentUser;
    private RecyclerView recyclerView;
    ArrayList<Prompt> notifcations;
    ArrayList<Post> selling;

    private TextView textView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        final DatabaseHelper database = new DatabaseHelper();

        notifcations = new ArrayList<>();

        database.getPosts(new FirebaseCallback() {
            @Override
            public void onCallback(List<Post> posts) {
                notifcations = database.getPromptsByUser(currentUser.getUid(), posts);
                if(notifcations.isEmpty())
                {
                    Toast.makeText(getContext(), "my name is jon ramos", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "I'm jo ramos!", Toast.LENGTH_SHORT).show();
                    textView = (TextView)view.findViewById(R.id.testez);
                    textView.setText(notifcations.get(0).getNotificationMessage());
                }
            }
        });

        return view;
    }
}
