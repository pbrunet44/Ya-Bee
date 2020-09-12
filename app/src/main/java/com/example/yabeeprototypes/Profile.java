package com.example.yabeeprototypes;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
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
    private Snackbar verifiedSnackbar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        wishlist = view.findViewById(R.id.productsInWishlist);
        buying = view.findViewById(R.id.productsImBuying);
        selling = view.findViewById(R.id.productsImSelling);
        makePost = (Button) view.findViewById(R.id.makePost);
        Button signOff = (Button) view.findViewById(R.id.signOutButton);
        Button reviews = view.findViewById(R.id.productReviews);
        Button giveAReview = view.findViewById(R.id.review_user);
        // pop-up message displayed to user to verify their email
        verifiedSnackbar = Snackbar.make(view.findViewById(R.id.myCoordinatorLayout), R.string.profile_account_verification_text, Snackbar.LENGTH_SHORT);


        Bundle bundle = getArguments();
        String email = "";
        String uid = "";
        if(currentUser != null)
        {
            email = currentUser.getEmail();
            uid = currentUser.getUid();
            boolean verified = currentUser.isEmailVerified();
            // check if user's account is verified
            if (!verified)
            {
                View snackbarView = verifiedSnackbar.getView();
                CoordinatorLayout.LayoutParams params=(CoordinatorLayout.LayoutParams)snackbarView.getLayoutParams();
                params.gravity = Gravity.TOP;
                view.setLayoutParams(params);
                verifiedSnackbar.setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE);
                verifiedSnackbar.show();
            }
        }

        if (bundle != null)
        {
            email = this.getArguments().getString("Email");
            uid = this.getArguments().getString("UserID");
            uId = uid;
        }

        if (currentUser == null || currentUser.getUid().equals(uid))
        {
            giveAReview.setVisibility(View.GONE);
        }

        if(currentUser == null || !currentUser.getUid().equals(uid))
        {
            buying.setVisibility(View.GONE);
            selling.setVisibility(View.GONE);
            wishlist.setVisibility(View.GONE);
            makePost.setVisibility(View.GONE);
            signOff.setVisibility(View.GONE);
            //editIcon.setVisibility(View.GONE);
            reviews.setVisibility(View.GONE);
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

