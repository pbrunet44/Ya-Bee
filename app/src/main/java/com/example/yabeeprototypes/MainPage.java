package com.example.yabeeprototypes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


//I know it still says activity, but the main page is now a fragment

public class MainPage extends Fragment {
    //private ImageButton button;

    private ImageView btnBuzz1;
    private ImageView btnBuzz2;
    private ImageView btnBuzz3;
    private TextView tvBuzz1;
    private TextView tvBuzz2;
    private TextView tvBuzz3;
    private Button signIn;

    private ImageView services;
    private ImageView textbooks;
    private ImageView sportingGoods;
    private ImageView videoGames;
    private ImageView books;
    private ImageView movies;
    private ImageView music;
    private ImageView furniture;
    private ImageView kitchen;
    private ImageView home;
    private ImageView shoes;
    private ImageView electronics;
    private ImageView toys;
    private ImageView travel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);
        btnBuzz1 = (ImageView) view.findViewById(R.id.imgBuzz1);
        btnBuzz2 = (ImageView) view.findViewById(R.id.imgBuzz2);
        btnBuzz3 = (ImageView) view.findViewById(R.id.imgBuzz3);
        tvBuzz1 = view.findViewById(R.id.tvBuzz1);
        tvBuzz2 = view.findViewById(R.id.tvBuzz2);
        tvBuzz3 = view.findViewById(R.id.tvBuzz3);
        //signIn = (Button) view.findViewById(R.id.tvLogin);

        services = (ImageView) view.findViewById(R.id.servicesImage);
        textbooks = (ImageView) view.findViewById(R.id.textbookImage);
        sportingGoods = (ImageView) view.findViewById(R.id.sportingGoodsImage);
        videoGames = (ImageView) view.findViewById(R.id.videoGamesImage);
        books = (ImageView) view.findViewById(R.id.booksImage);
        movies = (ImageView) view.findViewById(R.id.moviesImage);
        music = (ImageView) view.findViewById(R.id.musicImage);
        furniture = (ImageView) view.findViewById(R.id.furnitureImage);
        kitchen = (ImageView) view.findViewById(R.id.kitchenImage);
        home = (ImageView) view.findViewById(R.id.homeImage);
        shoes = (ImageView) view.findViewById(R.id.shoesImage);
        electronics = (ImageView) view.findViewById(R.id.electronicsImage);
        toys = (ImageView) view.findViewById(R.id.toysImage);
        travel = (ImageView) view.findViewById(R.id.travelImage);

        final DatabaseHelper databaseHelper = new DatabaseHelper();
        databaseHelper.getPosts(new FirebaseCallback() {
            @Override
            public void onCallback(List<Post> posts) {
                ArrayList<Post> dailyBuzz = databaseHelper.getDailyBuzz(posts);
                btnBuzz1.setImageBitmap(dailyBuzz.get(0).decodeImage());
                tvBuzz1.setText(dailyBuzz.get(0).getTitle());
                btnBuzz2.setImageBitmap(dailyBuzz.get(1).decodeImage());
                tvBuzz2.setText(dailyBuzz.get(1).getTitle());
                btnBuzz3.setImageBitmap(dailyBuzz.get(2).decodeImage());
                tvBuzz3.setText(dailyBuzz.get(2).getTitle());
            }
        });
        btnBuzz1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Hydroflasksksks clicked.", Toast.LENGTH_SHORT).show();

                ViewPost nextFrag = new ViewPost();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, nextFrag)
                        .addToBackStack(null)
                        .commit();
            }
        });
        btnBuzz2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "idk", Toast.LENGTH_SHORT).show();
            }
        });




        /*signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });*/
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
