package com.example.yabeeprototypes;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
//import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class Profile extends Fragment {

    public static int GET_FROM_GALLERY = 3;
    private Uri imageUri = null;
    private String imageEncoding = "";
    private Button makePost;
    private FirebaseUser currentUser;
    private ImageButton profilePicture;
    private Button wishlist;
    private Button buying;
    private Button selling;
    private final static int RESULT_LOAD_IMAGE = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        wishlist = view.findViewById(R.id.productsInWishlist);
        buying = view.findViewById(R.id.productsImBuying);
        selling = view.findViewById(R.id.productsImSelling);
        profilePicture = view.findViewById(R.id.user_profile_photo);

        buying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start new intent
                Intent intent = new Intent(getActivity(), Buying.class);
                startActivity(intent);

            }
        });
        selling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start new intent
                Intent intent = new Intent(getActivity(), Selling.class);
                startActivity(intent);

            }
        });

        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(
                        new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI
                        ),
                        GET_FROM_GALLERY
                );
            }
        });

        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start intent
                Intent intent = new Intent(getActivity(), Wishlist.class);
                startActivity(intent);
            }
        });

        TextView profileEmail = (TextView)view.findViewById(R.id.profileEmail);
        profileEmail.setText(currentUser.getEmail());


        Button signOff = (Button) view.findViewById(R.id.signOutButton);
        signOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Fragment home = new MainPage();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, home).commit();
            }
        });

        makePost = (Button) view.findViewById(R.id.makePost);
        makePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MakeListing.class);
                startActivity(i);
            }
        });

        return view;
    }



}

