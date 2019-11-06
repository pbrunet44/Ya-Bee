package com.example.yabeeprototypes;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MakeListing extends Activity implements AdapterView.OnItemSelectedListener {
    // initial values for bid price, imageurl, initial description respectively
    static final double INITIAL_BID_PRICE = Double.MAX_VALUE;
    private final static int RESULT_LOAD_IMAGE = 1;
    static final String INITIAL_IMAGE = "";
    static final String INITIAL_DESCRIPTION = "";
    String conditionSelected = "";
    String auctionLength = "";
    ImageView postImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_listing);
        Button makePost = findViewById(R.id.collectPostInfo);
        final DatabaseHelper database = new DatabaseHelper();
        postImage = findViewById(R.id.postImage);
        Button uploadPostImage = findViewById(R.id.uploadPostImage);

        // Spinner for category
        Spinner categorySpinner = (Spinner) findViewById(R.id.categoryDropdown);
        categorySpinner.setOnItemSelectedListener(this);
        // drop down elements
        List<String> categories = new ArrayList<>();
        categories.add("Services");
        categories.add("Textbooks");
        categories.add("Clothing");
        categories.add("Shoes");
        categories.add("Home");
        categories.add("Furniture");
        categories.add("Kitchen");
        categories.add("Electronics");
        categories.add("Music");
        categories.add("Movies");
        categories.add("Books");
        categories.add("Video Games");
        categories.add("Toys");
        categories.add("Sporting Goods");
        categories.add("School/Office");
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        categorySpinner.setAdapter(categoryAdapter);

        // Spinner for conditions
        Spinner conditionSpinner = (Spinner) findViewById(R.id.itemCondition);
        // on click listener
        conditionSpinner.setOnItemSelectedListener(this);
        // drop down elements
        List<String> conditions = new ArrayList<>();
        conditions.add("New");
        conditions.add("New with defects");
        conditions.add("New without tags");
        conditions.add("Pre-owned");
        conditions.add("Certified refurbished");
        conditions.add("N/A");
        // Create adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, conditions);
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
        ArrayAdapter<String> lengthAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, duration);
        auctionLengthSpinner.setAdapter(lengthAdapter);


        makePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = ((TextView)findViewById(R.id.titleEntry)).getText().toString();
                String description = ((TextView)findViewById(R.id.descEntry)).getText().toString();
                String category = ((Spinner)findViewById(R.id.categoryDropdown)).getSelectedItem().toString();
                String condition = ((Spinner)findViewById(R.id.itemCondition)).getSelectedItem().toString();
                String durationOfAuction = ((Spinner)findViewById(R.id.auction_duration)).getSelectedItem().toString();
                double maxPrice = Double.parseDouble(((TextView)findViewById(R.id.yourPrice)).getText().toString());
                //int auctionLength = Integer.parseInt(((TextView)findViewById(R.id.durationOfAuction)).getText().toString());

                // will take care of image url later that's why it's null
                // split auction length string into number + " days"
                String[] durationArray = durationOfAuction.split(" ");
                int numOfDays = Integer.parseInt(durationArray[0]);
                System.out.println("Here's the number of days the auction is going to last:" + numOfDays);
                Date date = new Date();
                Post post = new Post(title, maxPrice, description, numOfDays, new Bid(INITIAL_BID_PRICE, INITIAL_DESCRIPTION, INITIAL_IMAGE), INITIAL_IMAGE, category, condition, Long.toString(System.nanoTime()), date, false);
                database.writeNewPost(post);

                Toast successToast = Toast.makeText(getApplicationContext(), "Successful post creation!", Toast.LENGTH_LONG);
                successToast.setGravity(Gravity.TOP, 0, 0);
                successToast.show();
            }
        });

        uploadPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null)
        {
            Uri selectedImage = data.getData();
            postImage.setImageURI(selectedImage);
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        //conditionSelected = parent.getItemAtPosition(position).toString();
        // For debugging purposes only
        //Toast.makeText(parent.getContext(), "Selected: " + conditionSelected, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}

