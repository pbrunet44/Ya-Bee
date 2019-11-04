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

public class Profile extends Fragment {

    private Button makePost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        makePost = (Button) view.findViewById(R.id.makePost);
        /*ImageView deadBee = (ImageView) view.findViewById(R.id.deadBee);
        deadBee.getDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.MULTIPLY);*/
        makePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MakeListing.class);
                startActivity(intent);
            }
        });
        return view;
    }

//    public void goToProductsSelling(View view)
//    {
//        Intent intent = new Intent(this, ItemsSelling.class);
//        startActivity(intent);
//    }
//
//    public void goToProductsBuying(View view)
//    {
//        Intent intent = new Intent(this, ItemsBuying.class);
//        startActivity(intent);
//    }
//
//    public void goToMakeAPost(View view)
//    {
//        Intent intent = new Intent(this, MakeListing.class);
//        startActivity(intent);
//    }
}
