package com.example.yabeeprototypes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MakeListing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_listing);
    }

    public void goBackToListOfPosts(View view)
    {
        Intent intent = new Intent(this, ItemsBuying.class);
        startActivity(intent);
    }
}
