
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

public class ViewPost extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_view_post, container, false);
        // this is needed to send post information
        // view post should also display the post's id, to make it easier to retrieve post information
        Button createBid = (Button) view.findViewById(R.id.BidButton);
        createBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InitialBid.class);
                startActivity(intent);
            }

            // after sending post information, the lowest bid on the screen has to be changed
            // so we must set the lowest bid price on the screen to the post's lowestbid e.g. with setText, etc.
        });
        return view;

    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);
        // this is needed to send post information
        // view post should also display the post's id, to make it easier to retrieve post information
        Button createBid = (Button)findViewById(R.id.BidButton);
        createBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewPost.this, InitialBid.class);
                startActivity(intent);
            }

            // after sending post information, the lowest bid on the screen has to be changed
            // so we must set the lowest bid price on the screen to the post's lowestbid e.g. with setText, etc.
        });
    }*/


}