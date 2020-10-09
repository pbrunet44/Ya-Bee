package com.example.yabeeprototypes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


//I know it still says activity, but the main page is now a fragment

public class MainPage extends Fragment implements View.OnClickListener {
    //private ImageButton button;
    private static final int MAX_NUM_OF_POSTS_FOR_DAILY_BUZZ = 10;
    private ImageView btnBuzz1;
    private ImageView btnBuzz2;
    private ImageView btnBuzz3;
    private ImageView btnBuzz4;
    private ImageView btnBuzz5;
    private ImageView btnBuzz6;
    private ImageView btnBuzz7;
    private ImageView btnBuzz8;
    private ImageView btnBuzz9;
    private ImageView btnBuzz10;
    private TextView tvBuzz1;
    private TextView tvBuzz2;
    private TextView tvBuzz3;
    private TextView tvBuzz4;
    private TextView tvBuzz5;
    private TextView tvBuzz6;
    private TextView tvBuzz7;
    private TextView tvBuzz8;
    private TextView tvBuzz9;
    private TextView tvBuzz10;
    private Button signIn;

    private LinearLayout services;
    private LinearLayout textbooks;
    private LinearLayout sportingGoods;
    private LinearLayout videoGames;
    private LinearLayout books;
    private LinearLayout movies;
    private LinearLayout music;
    private LinearLayout furniture;
    private LinearLayout kitchen;
    private LinearLayout home;
    private LinearLayout shoes;
    private LinearLayout electronics;
    private LinearLayout toys;
    private LinearLayout travel;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    private List<String> postIDs = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main_page, container, false);
        view.findViewById(R.id.loadingPanelforMainPage).setVisibility(View.VISIBLE);

        btnBuzz1 = (ImageView) view.findViewById(R.id.imgBuzz1);
        btnBuzz2 = (ImageView) view.findViewById(R.id.imgBuzz2);
        btnBuzz3 = (ImageView) view.findViewById(R.id.imgBuzz3);
        btnBuzz4 = (ImageView) view.findViewById(R.id.imgBuzz4);
        btnBuzz5 = (ImageView) view.findViewById(R.id.imgBuzz5);
        btnBuzz6 = (ImageView) view.findViewById(R.id.imgBuzz6);
        btnBuzz7 = (ImageView) view.findViewById(R.id.imgBuzz7);
        btnBuzz8 = (ImageView) view.findViewById(R.id.imgBuzz8);
        btnBuzz9 = (ImageView) view.findViewById(R.id.imgBuzz9);
        btnBuzz10 = (ImageView) view.findViewById(R.id.imgBuzz10);

        tvBuzz1 = view.findViewById(R.id.tvBuzz1);
        tvBuzz2 = view.findViewById(R.id.tvBuzz2);
        tvBuzz3 = view.findViewById(R.id.tvBuzz3);
        tvBuzz4 = view.findViewById(R.id.tvBuzz4);
        tvBuzz5 = view.findViewById(R.id.tvBuzz5);
        tvBuzz6 = view.findViewById(R.id.tvBuzz6);
        tvBuzz7 = view.findViewById(R.id.tvBuzz7);
        tvBuzz8 = view.findViewById(R.id.tvBuzz8);
        tvBuzz9 = view.findViewById(R.id.tvBuzz9);
        tvBuzz10 = view.findViewById(R.id.tvBuzz10);



        services = (LinearLayout) view.findViewById(R.id.servicesImage);
        textbooks = (LinearLayout) view.findViewById(R.id.textbookImage);
        sportingGoods = (LinearLayout) view.findViewById(R.id.sportingGoodsImage);
        videoGames = (LinearLayout) view.findViewById(R.id.videoGamesImage);
        books = (LinearLayout) view.findViewById(R.id.booksImage);
        movies = (LinearLayout) view.findViewById(R.id.moviesImage);
        music = (LinearLayout) view.findViewById(R.id.musicImage);
        furniture = (LinearLayout) view.findViewById(R.id.furnitureImage);
        kitchen = (LinearLayout) view.findViewById(R.id.kitchenImage);
        home = (LinearLayout) view.findViewById(R.id.homeImage);
        shoes = (LinearLayout) view.findViewById(R.id.shoesImage);
        electronics = (LinearLayout) view.findViewById(R.id.electronicsImage);
        toys = (LinearLayout) view.findViewById(R.id.toysImage);
        travel = (LinearLayout) view.findViewById(R.id.travelImage);

        services.setOnClickListener(this);
        textbooks.setOnClickListener(this);
        sportingGoods.setOnClickListener(this);
        videoGames.setOnClickListener(this);
        books.setOnClickListener(this);
        movies.setOnClickListener(this);
        music.setOnClickListener(this);
        furniture.setOnClickListener(this);
        kitchen.setOnClickListener(this);
        home.setOnClickListener(this);
        shoes.setOnClickListener(this);
        electronics.setOnClickListener(this);
        toys.setOnClickListener(this);
        travel.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        final DatabaseHelper databaseHelper = new DatabaseHelper();
        databaseHelper.getPosts(new FirebaseCallback() {
            @Override
            public void onCallback(List<Post> posts) {
                ArrayList<Post> dailyBuzz = databaseHelper.getDailyBuzz(posts);
                // set appropriate attributes (image bitmap and title) and save postIds
                setAttributes(dailyBuzz.get(0), btnBuzz1, tvBuzz1);
                postIDs.add(0, dailyBuzz.get(0).getId());

                setAttributes(dailyBuzz.get(1), btnBuzz2, tvBuzz2);
                postIDs.add(1, dailyBuzz.get(1).getId());

                setAttributes(dailyBuzz.get(2), btnBuzz3, tvBuzz3);
                postIDs.add(2, dailyBuzz.get(2).getId());

                setAttributes(dailyBuzz.get(3), btnBuzz4, tvBuzz4);
                postIDs.add(3, dailyBuzz.get(3).getId());

                setAttributes(dailyBuzz.get(4), btnBuzz5, tvBuzz5);
                postIDs.add(4, dailyBuzz.get(4).getId());

                setAttributes(dailyBuzz.get(5), btnBuzz6, tvBuzz6);
                postIDs.add(5, dailyBuzz.get(5).getId());

                setAttributes(dailyBuzz.get(6), btnBuzz7, tvBuzz7);
                postIDs.add(6, dailyBuzz.get(6).getId());

                setAttributes(dailyBuzz.get(7), btnBuzz8, tvBuzz8);
                postIDs.add(7, dailyBuzz.get(7).getId());

                setAttributes(dailyBuzz.get(8), btnBuzz9, tvBuzz9);
                postIDs.add(8, dailyBuzz.get(8).getId());

                setAttributes(dailyBuzz.get(9), btnBuzz10, tvBuzz10);
                postIDs.add(9, dailyBuzz.get(9).getId());
                view.findViewById(R.id.loadingPanelforMainPage).setVisibility(View.GONE);

            }
        });
        btnBuzz1.setOnClickListener(this);
        btnBuzz2.setOnClickListener(this);
        btnBuzz3.setOnClickListener(this);
        btnBuzz4.setOnClickListener(this);
        btnBuzz5.setOnClickListener(this);
        btnBuzz6.setOnClickListener(this);
        btnBuzz7.setOnClickListener(this);
        btnBuzz8.setOnClickListener(this);
        btnBuzz9.setOnClickListener(this);
        btnBuzz10.setOnClickListener(this);

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

        return view;
    }

    @Override
    public void onClick(View v)
    {
        Bundle bundle = new Bundle();
        String category = "";
        Intent intent = null;
        ViewPost viewPost = new ViewPost();
        switch(v.getId())
        {
            case R.id.servicesImage:
                category = "Services";
                bundle.putString("Category", category);
                intent = new Intent(getContext(), Categories.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.textbookImage:
                category = "Textbooks";
                bundle.putString("Category", category);
                intent = new Intent(getContext(), Categories.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.sportingGoodsImage:
                category = "Sporting Goods";
                bundle.putString("Category", category);
                intent = new Intent(getContext(), Categories.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.videoGamesImage:
                category = "Video Games";
                bundle.putString("Category", category);
                intent = new Intent(getContext(), Categories.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.booksImage:
                category = "Books";
                bundle.putString("Category", category);
                intent = new Intent(getContext(), Categories.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.moviesImage:
                category = "Movies";
                bundle.putString("Category", category);
                intent = new Intent(getContext(), Categories.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.musicImage:
                category = "Music";
                bundle.putString("Category", category);
                intent = new Intent(getContext(), Categories.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.furnitureImage:
                category = "Furniture";
                bundle.putString("Category", category);
                intent = new Intent(getContext(), Categories.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.kitchenImage:
                category = "Kitchen";
                bundle.putString("Category", category);
                intent = new Intent(getContext(), Categories.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.homeImage:
                category = "Home";
                bundle.putString("Category", category);
                intent = new Intent(getContext(), Categories.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.shoesImage:
                category = "Shoes";
                bundle.putString("Category", category);
                intent = new Intent(getContext(), Categories.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.electronicsImage:
                category = "Electronics";
                bundle.putString("Category", category);
                intent = new Intent(getContext(), Categories.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.toysImage:
                category = "Toys";
                bundle.putString("Category", category);
                intent = new Intent(getContext(), Categories.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.travelImage:
                category = "Travel";
                bundle.putString("Category", category);
                intent = new Intent(getContext(), Categories.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.imgBuzz1:
                bundle.putString("POST ID", postIDs.get(0));
                bundle.putInt("container", R.id.fragmentContainer);
                viewPost.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, viewPost)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.imgBuzz2:
                bundle.putString("POST ID", postIDs.get(1));
                bundle.putInt("container", R.id.fragmentContainer);
                viewPost.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, viewPost)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.imgBuzz3:
                bundle.putString("POST ID", postIDs.get(2));
                bundle.putInt("container", R.id.fragmentContainer);
                viewPost.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, viewPost)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.imgBuzz4:
                bundle.putString("POST ID", postIDs.get(3));
                bundle.putInt("container", R.id.fragmentContainer);
                viewPost.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, viewPost)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.imgBuzz5:
                bundle.putString("POST ID", postIDs.get(4));
                bundle.putInt("container", R.id.fragmentContainer);
                viewPost.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, viewPost)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.imgBuzz6:
                bundle.putString("POST ID", postIDs.get(5));
                bundle.putInt("container", R.id.fragmentContainer);
                viewPost.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, viewPost)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.imgBuzz7:
                bundle.putString("POST ID", postIDs.get(6));
                bundle.putInt("container", R.id.fragmentContainer);
                viewPost.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, viewPost)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.imgBuzz8:
                bundle.putString("POST ID", postIDs.get(7));
                bundle.putInt("container", R.id.fragmentContainer);
                viewPost.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, viewPost)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.imgBuzz9:
                bundle.putString("POST ID", postIDs.get(8));
                bundle.putInt("container", R.id.fragmentContainer);
                viewPost.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, viewPost)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.imgBuzz10:
                bundle.putString("POST ID", postIDs.get(9));
                bundle.putInt("container", R.id.fragmentContainer);
                viewPost.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, viewPost)
                        .addToBackStack(null)
                        .commit();
                break;
            default:
                Toast.makeText(getContext(), "Something went wrong. Please refresh page.", Toast.LENGTH_SHORT).show();
        }

    }

    public void setAttributes(Post post, ImageView picture, TextView title)
    {
        if (post != null)
        {
            picture.setImageBitmap(compressBitmap(post.decodeImage()));
            title.setText(post.getTitle());
        }
    }

    private Bitmap compressBitmap(Bitmap bitmap)
    {
        final int maxSize = 200;
        int outWidth;
        int outHeight;
        int inWidth = bitmap.getWidth();
        int inHeight = bitmap.getHeight();
        if(inWidth > inHeight){
            outWidth = maxSize;
            outHeight = (inHeight * maxSize) / inWidth;
        } else {
            outHeight = maxSize;
            outWidth = (inWidth * maxSize) / inHeight;
        }
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, outWidth, outHeight, true);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 40, stream);
        byte[] byteArray = stream.toByteArray();
        return BitmapFactory.decodeByteArray(byteArray,0,byteArray.length);
    }

}