package com.example.yabeeprototypes;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


//RecyclerView List of prompts for user
public class Notifications extends Fragment {

    private static int MAX_NUMBER_OF_NOTIFICATIONS = 1000000;
    private DatabaseHelper database;
    private FirebaseUser currentUser;
    private RecyclerView recyclerView;
    ArrayList<Prompt> notifcations;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notifications, container, false);



        return view;
    }
}
