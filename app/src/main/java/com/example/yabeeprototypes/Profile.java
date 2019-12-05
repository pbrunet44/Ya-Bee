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
import android.util.Log;
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

import android.content.Intent;

import static android.app.Activity.RESULT_OK;

public class Profile extends Fragment {

    public static int GET_FROM_GALLERY = 3;
    private Uri imageUri = null;
    private String imageEncoding = "";
    private Button makePost;
    private FirebaseUser currentUser;
    private Button wishlist;
    private Button buying;
    private Button selling;
    private final static int RESULT_LOAD_IMAGE = 1;
    private String uId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        wishlist = view.findViewById(R.id.productsInWishlist);
        buying = view.findViewById(R.id.productsImBuying);
        selling = view.findViewById(R.id.productsImSelling);
        makePost = (Button) view.findViewById(R.id.makePost);
        Button signOff = (Button) view.findViewById(R.id.signOutButton);
        ImageView editIcon = view.findViewById(R.id.editIcon);
        ImageView emailIcon = view.findViewById(R.id.emailIcon);
        Button reviews = view.findViewById(R.id.productReviews);
        Button giveAReview = view.findViewById(R.id.review_user);

        Bundle bundle = getArguments();
        String email = currentUser.getEmail();
        String uid = currentUser.getUid();
        if (bundle != null)
        {
            email = this.getArguments().getString("Email");
            uid = this.getArguments().getString("UserID");
            uId = uid;
        }

        if(!currentUser.getUid().equals(uid))
        {
            buying.setVisibility(View.INVISIBLE);
            selling.setVisibility(View.INVISIBLE);
            wishlist.setVisibility(View.INVISIBLE);
            makePost.setVisibility(View.INVISIBLE);
            signOff.setVisibility(View.INVISIBLE);
            editIcon.setVisibility(View.INVISIBLE);
            reviews.setVisibility(View.INVISIBLE);
        }
        if (currentUser.getUid().equals(uid))
        {
            giveAReview.setVisibility(View.INVISIBLE);
        }


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

        reviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewUserReviews.class);
                startActivity(intent);
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
        profileEmail.setText(email);


        signOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Fragment home = new MainPage();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, home).commit();
            }
        });

        makePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), MakeListing.class);
                startActivity(i);
            }
        });

        giveAReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReviewUser.class);
                intent.putExtra("uid", uId);
                startActivity(intent);
            }
        });

        return view;
    }


}

