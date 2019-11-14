package com.example.yabeeprototypes;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class User {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    public User()
    {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    public FirebaseUser getCurrentUser()
    {
        return this.currentUser;
    }

}
