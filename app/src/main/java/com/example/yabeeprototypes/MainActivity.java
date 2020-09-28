package com.example.yabeeprototypes;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseHelper databaseHelper;

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    currentUser = mAuth.getCurrentUser();

                    switch(menuItem.getItemId()) {
                        case R.id.navHome:
                            selectedFragment = new MainPage();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).addToBackStack(null).commit();
                            break;
                        case R.id.navSearch:
                            onSearchRequested();
                            break;
                        case R.id.navNotifications:
//                            selectedFragment = new Notifications();
//                            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).addToBackStack(null).commit();
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
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).addToBackStack(null).commit();
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
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).addToBackStack(null).commit();
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
        currentUser = mAuth.getCurrentUser();

        databaseHelper = new DatabaseHelper();

        databaseHelper.getUsers(new UserCallback() {
            @Override
            public void onCallback(List<User> users) {
                ArrayList<User> allUsers = databaseHelper.getAllUsers(users);
                for (User user: allUsers) {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(user.getUid())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    String msg = "Unsubscribed from all Notifications";
                                    if (!task.isSuccessful()) {
                                        msg = "Unsucessfull";
                                    }
                                    System.out.println(msg + " to notifications");
                                    //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                if (currentUser != null) {
                    FirebaseMessaging.getInstance().subscribeToTopic(currentUser.getUid())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    String msg = "Subscribed to Notifications for user: " + currentUser.getEmail();
                                    if (!task.isSuccessful()) {
                                        msg = "Unsucessfull";
                                    }
                                    System.out.println(msg);
                                    //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

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
