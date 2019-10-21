package com.example.yabeeprototypes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    private ImageButton button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToBrowse(View view)
    {
        Intent intent = new Intent(this, Browse.class);
        startActivity(intent);
    }

    public void goToLogin(View view)
    {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void goToSignUp(View view)
    {
        Intent intent = new Intent(this, MakeAccount.class);
        startActivity(intent);
    }
}
