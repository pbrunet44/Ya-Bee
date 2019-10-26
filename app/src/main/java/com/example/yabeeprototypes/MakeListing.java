package com.example.yabeeprototypes;


import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MakeListing extends AppCompatActivity {
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_listing);

        this.mDatabase = FirebaseDatabase.getInstance().getReference();
        this.mDatabase.setValue("Boo, it's Halloween!");
    }

}
