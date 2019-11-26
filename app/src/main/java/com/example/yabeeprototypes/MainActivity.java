package com.example.yabeeprototypes;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends FragmentActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    currentUser = mAuth.getCurrentUser();
                    switch(menuItem.getItemId()) {
                        case R.id.navHome:
                            selectedFragment = new MainPage();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit();
                            break;
                        case R.id.navSearch:
                            onSearchRequested();
                            break;
                        case R.id.navNotifications:
//                            selectedFragment = new Notifications();
//                            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit();
//                            break;
                            if (currentUser == null) // no one signed in
                            {
                                // show account options page
                                System.out.println("The user is not signed in.");
                                Intent intent = new Intent(getApplicationContext(), AccountOptions.class);
                                startActivity(intent);
                            }
                            else // signed in
                            {
                                System.out.println("The user is signed in. Here is their email: " + currentUser.getEmail());
                                selectedFragment = new Notifications();
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit();
                            }
                            break;
                        case R.id.navProfile:
                            if (currentUser == null) // no one signed in
                            {
                                // show account options page
                                System.out.println("The user is not signed in.");
                                Intent intent = new Intent(getApplicationContext(), AccountOptions.class);
                                startActivity(intent);
                            }
                            else // signed in
                            {
                                System.out.println("The user is signed in. Here is their email: " + currentUser.getEmail());
                                selectedFragment = new Profile();
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit();
                            }
                            break;
                        case R.id.navMakeListing:
                            Intent intent = new Intent(getApplicationContext(), MakeListing.class);
                            startActivity(intent);
                            break;
                    }
                    return true;
                }
            };



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        if (findViewById(R.id.fragmentContainer) != null) {

            if (savedInstanceState != null) {
                return;
            }

            MainPage mainPage = new MainPage();

            mainPage.setArguments(getIntent().getExtras());

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainer, mainPage).commit();
        }
    }
}
