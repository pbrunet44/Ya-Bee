package com.example.yabeeprototypes;


import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MakeListing extends Activity implements AdapterView.OnItemSelectedListener {
    // initial values for bid price, imageurl, initial description respectively
    static final double INITIAL_BID_PRICE = Double.MAX_VALUE;
    static final String INITIAL_IMAGE = "";
    static final String INITIAL_DESCRIPTION = "";
    String conditionSelected = "";
    String auctionLength = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_listing);
        Button makePost = findViewById(R.id.collectPostInfo);
        final DatabaseHelper database = new DatabaseHelper();

        // Spinner for conditions
        Spinner conditionSpinner = (Spinner) findViewById(R.id.itemCondition);
        // on click listener
        conditionSpinner.setOnItemSelectedListener(this);
        // drop down elements
        List<String> conditions = new ArrayList<>();
        conditions.add("New with tags");
        conditions.add("Pre-owned");
        conditions.add("New with defects");
        conditions.add("New without tags");
        // Create adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, conditions);
        // drop down style
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        conditionSpinner.setAdapter(dataAdapter);

        // Spinner for auction duration
         Spinner auctionLengthSpinner = (Spinner) findViewById(R.id.auction_duration);
         auctionLengthSpinner.setOnItemSelectedListener(this);
         // drop down elements
        List<String> duration = new ArrayList<>();
        duration.add("3 days");
        duration.add("5 days");
        duration.add("7 days");
        duration.add("10 days");
        ArrayAdapter<String> lengthAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, duration);
        auctionLengthSpinner.setAdapter(lengthAdapter);

        makePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = ((TextView)findViewById(R.id.titleEntry)).getText().toString();
                String description = ((TextView)findViewById(R.id.descEntry)).getText().toString();
                String category = ((Spinner)findViewById(R.id.categoryDropdown)).getSelectedItem().toString();
                double maxPrice = Double.parseDouble(((TextView)findViewById(R.id.yourPrice)).getText().toString());
                //int auctionLength = Integer.parseInt(((TextView)findViewById(R.id.durationOfAuction)).getText().toString());

                // will take care of image url later that's why it's null
                // split auction length string into number + " days"
                String durationArray[] = auctionLength.split(" ");
                int numOfDays = Integer.parseInt(durationArray[0]);
                System.out.println("Here's the number of days the auciton is going to last:" + numOfDays);
                Date date = new Date();
                Post post = new Post(title, maxPrice, description, numOfDays, new Bid(INITIAL_BID_PRICE, INITIAL_DESCRIPTION, INITIAL_IMAGE), INITIAL_IMAGE, category, Long.toString(System.nanoTime()), date, false);
                database.writeNewPost(post);

                Toast successToast = Toast.makeText(getApplicationContext(), "Successful post creation!", Toast.LENGTH_LONG);
                successToast.setGravity(Gravity.TOP, 0, 0);
                successToast.show();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        conditionSelected = parent.getItemAtPosition(position).toString();
        // For debugging purposes only
        Toast.makeText(parent.getContext(), "Selected: " + conditionSelected, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}

