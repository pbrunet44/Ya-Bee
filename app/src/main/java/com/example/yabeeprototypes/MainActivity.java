package com.example.yabeeprototypes;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
                            selectedFragment = new Browse();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit();
                            break;
                        case R.id.navNotifications:
                            selectedFragment = new Fragment();
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit();
                            break;
                        case R.id.navProfile:
                            if (currentUser == null) // no one signed in
                            {
                                // show account options page
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
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
}

//import androidx.appcompat.app.AppCompatActivity;
//import androidx.viewpager.widget.ViewPager;
//
//import android.os.Bundle;
//
//import java.util.concurrent.locks.Lock;
//
//import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;
//
//
////This is the Main Activity that adds the Main Page fragment
//
//public class MainActivity extends AppCompatActivity {
//
//    private SectionsStatePagerAdapter mSectionsStatePagerAdapter;
//    private LockableViewPager mViewPager;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate((savedInstanceState));
//        setContentView(R.layout.fragment_viewpager);
//        mSectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//        mViewPager = (LockableViewPager) findViewById(R.id.container);
//        setupViewPager(mViewPager);
//
//        mViewPager.setSwipeable(LockableViewPager.SwipeDirection.left);
//    }
//
//    /**
//     * Sets up layout that allows user to swipe left and right through "pages
//     * @param viewPager the ID string of the target post
//     * @return the target Post object
//     */
//    private void setupViewPager(ViewPager viewPager) {
//        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager(), BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//
//
//
//        //adapter.addFragment((new ClassName(), "FragmentTitle"));
//        adapter.addFragment(new MainPage(), "MainPage");
//        adapter.addFragment(new ViewPost(), "ViewPost");
//
//        viewPager.setAdapter(adapter);
//    }
//
//    public void setViewPager(int fragmentNumber) {
//        mViewPager.setCurrentItem(fragmentNumber);
//    }
//
//}


//package com.example.yabeeprototypes;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//
////I know it still says activity, but the main page is now a fragment
//
//public class MainActivity extends Fragment {
//    //private ImageButton button;
//
//    private ImageView buzz1;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.activity_main, container, false);
//        buzz1 = (ImageView) view.findViewById(R.id.imgBuzz1);
//        buzz1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Hydroflasksksks clicked.", Toast.LENGTH_SHORT).show();
//
//                ((MainPage)getActivity()).setViewPager(1);
//            }
//        });
//        return view;
//    }
//
//
//
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_main);
////    }
////
////    public void goToBrowse(View view)
////    {
////        Intent intent = new Intent(this, Browse.class);
////        startActivity(intent);
////    }
////
////    public void goToLogin(View view)
////    {
////        Intent intent = new Intent(this, Login.class);
////        startActivity(intent);
////    }
////
////    public void goToSignUp(View view)
////    {
////        Intent intent = new Intent(this, MakeAccount.class);
////        startActivity(intent);
////    }
//}
