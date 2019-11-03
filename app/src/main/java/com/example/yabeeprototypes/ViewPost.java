
package com.example.yabeeprototypes;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ViewPost extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_view_post, container, false);
        // this is needed to send post information
        // view post should also display the post's id, to make it easier to retrieve post information
        final String id = ((TextView)view.findViewById(R.id.postID)).getText().toString();
        Button createBid = (Button) view.findViewById(R.id.BidButton);

        DatabaseHelper database = new DatabaseHelper();
        Post post = database.getPostByID(id);

        TextView timer = (TextView)view.findViewById(R.id.timer); // retrieving timer off the post's page
        boolean isAuctionOver = false;
        if (post != null)
        {
            while (!isAuctionOver)
            {
                String result = post.getAuctionTimer();
                if (!result.equals("AUCTION EXPIRED"))
                    timer.setText(post.getAuctionTimer()); // setting timer to time remaining
                else
                {
                    isAuctionOver = true;
                    // go to another screen displaying that you won the auction
                }
            }
        }

        createBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MakeBid.class);
                intent.putExtra("POST ID", id);
                startActivity(intent);
            }

            // after sending post information, the lowest bid on the screen has to be changed
            // so we must set the lowest bid price on the screen to the post's lowestbid e.g. with setText, etc.
        });
        return view;

    }

}