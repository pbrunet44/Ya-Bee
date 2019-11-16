package com.example.yabeeprototypes;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class Profile extends Fragment {

    private Button makePost;
    private FirebaseUser currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

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
