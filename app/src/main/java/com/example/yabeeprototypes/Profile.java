package com.example.yabeeprototypes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    public void goToProductsSelling(View view)
    {
        Intent intent = new Intent(this, ItemsSelling.class);
        startActivity(intent);
    }

    public void goToProductsBuying(View view)
    {
        Intent intent = new Intent(this, ItemsBuying.class);
        startActivity(intent);
    }

    public void goToMakeAPost(View view)
    {
        Intent intent = new Intent(this, MakeListing.class);
        startActivity(intent);
    }
}
