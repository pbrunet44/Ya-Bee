package com.example.yabeeprototypes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


//RecyclerView List of prompts for user
public class Notifications extends Fragment {

    private static int MAX_NUMBER_OF_NOTIFICATIONS = 1000000;
    private DatabaseHelper database;
    private FirebaseUser currentUser;
    private RecyclerView recyclerView;
    ArrayList<Notification> notifications;
    ArrayList<Post> selling;

    private TextView textView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        view.findViewById(R.id.loadingPanelforNotifications).setVisibility(View.VISIBLE);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        database = new DatabaseHelper();

        notifications = new ArrayList<>();

        database.getPosts(new FirebaseCallback() {
            @Override
            public void onCallback(List<Post> posts) {
                notifications = database.getNotificationsByUser(currentUser.getUid(), posts);
                if(notifications == null || notifications.isEmpty())
                {
                    Toast.makeText(getContext(), "No notifications to show.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Collections.reverse(notifications);
                    //Toast.makeText(getContext(), "I'm jo ramos!", Toast.LENGTH_SHORT).show();
                    recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewforNotifications);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setHasFixedSize(true);
                    NotificationsAdapter adapter = new NotificationsAdapter(notifications, R.id.NotificationsContainer);
                    recyclerView.setAdapter(adapter);
                }

                view.findViewById(R.id.loadingPanelforNotifications).setVisibility(View.GONE);
            }
        });

        return view;
    }
}
