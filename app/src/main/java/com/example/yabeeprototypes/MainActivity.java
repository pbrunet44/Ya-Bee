package com.example.yabeeprototypes;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends FragmentActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch(menuItem.getItemId()) {
                        case R.id.navHome:
                            selectedFragment = new MainPage();
                            break;
                        case R.id.navSearch:
                            selectedFragment = new Fragment();
                            break;
                        case R.id.navProfile:
                            selectedFragment = new Profile();
                            break;

                    }
                    //getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit();
                    return true;
                }
            };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragmentContainer) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create a new Fragment to be placed in the activity layout
            MainPage firstFragment = new MainPage();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainer, firstFragment).commit();
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
