package com.example.yabeeprototypes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


//I know it still says activity, but the main page is now a fragment

public class MainPage extends Fragment {
    //private ImageButton button;


    private ImageView buzz1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        buzz1 = (ImageView) view.findViewById(R.id.imgBuzz1);
        buzz1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Hydroflasksksks clicked.", Toast.LENGTH_SHORT).show();

                ((MainActivity) getActivity()).setViewPager(1); //viewPost is our second fragment
            }
        });
        return view;
    }

/*    @Override
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
    }*/
}


/*package com.example.yabeeprototypes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;


//This is the Main Activity that adds the Main Page fragment

public class MainPage extends AppCompatActivity {

    private SectionsStatePagerAdapter mSectionsStatePagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate((savedInstanceState));
        setContentView(R.layout.fragment_viewpager);

        mSectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);

        setupViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new MainActivity(), "MainActivity");
        adapter.addFragment(new ViewPost(), "ViewPost");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber) {
        mViewPager.setCurrentItem(fragmentNumber);
    }

}*/
