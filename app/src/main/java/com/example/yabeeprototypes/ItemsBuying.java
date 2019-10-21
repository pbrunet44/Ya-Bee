package com.example.yabeeprototypes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ItemsBuying extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_buying);
    }

    public void goToMakePost(View view)
    {
        Intent intent = new Intent(this, MakeListing.class);
        startActivity(intent);
    }
}
