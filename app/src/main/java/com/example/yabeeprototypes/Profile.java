package com.example.yabeeprototypes;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class Profile extends Fragment {

    private Uri imageUri = null;
    private String imageEncoding = "";
    private Button makePost;
    private FirebaseUser currentUser;
    private ImageView profilePicture;
    private Button buying;
    private Button selling;
    private final static int RESULT_LOAD_IMAGE = 1;
    private List<Post> buy;
    private List<Post> sell;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        buying = view.findViewById(R.id.productsImBuying);
        selling = view.findViewById(R.id.productsImSelling);

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

        TextView profileEmail = (TextView)view.findViewById(R.id.profileEmail);
        profileEmail.setText(currentUser.getEmail());

        profilePicture = view.findViewById(R.id.postImageforProfile);
//        Picasso.get().setLoggingEnabled(true);
//        Picasso.get()
//                .load(currentUser.getPhotoUrl())
//                .resize(100, 100)
//                .centerCrop()
//                .into(profilePicture);
//
//        System.out.println("Here is the image url:" + currentUser.getPhotoUrl());
//        profilePicture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
//            }
//        });


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

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null)
//        {
//            Uri selectedImage = data.getData();
//            imageUri = selectedImage;
//            profilePicture.setImageURI(selectedImage);
//
//            // update user profile
//            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                    .setPhotoUri(selectedImage)
//                    .build();
//
//            currentUser.updateProfile(profileUpdates)
//                    .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful())
//                                System.out.println("Profile picture successfully uploaded!");
//                            else
//                                System.out.println("Profile picture not successfully uploaded!");
//                        }
//                    });
//        }
//    }

}
