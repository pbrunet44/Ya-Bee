package com.example.yabeeprototypes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EditPost extends Activity implements AdapterView.OnItemSelectedListener {

    public String postId;
    public TextView editTitle;
    public TextView editDescription;
    public Spinner editCategory;
    public Spinner editCondition;
    //public ImageView editImage;
    public Button finish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        final DatabaseHelper database = new DatabaseHelper();
        //editImage = (ImageView) findViewById(R.id.editpostImage);
        editTitle = (TextView)findViewById(R.id.edittitleEntry);
        editDescription = (TextView)findViewById(R.id.editdescEntry);
        finish = (Button)findViewById(R.id.finishEditingButton);


        Intent intent = getIntent();
        // get bundle from intent
        Bundle extras = intent.getExtras();

        // now get data from bundle
        postId = extras.getString("Post ID");
        //final String image = extras.getString("Image");
        final String title = extras.getString("Title");
        final String description = extras.getString("Description");
        final String category = extras.getString("Category");
        final String condition = extras.getString("Condition");

        editCategory = (Spinner) findViewById(R.id.editcategoryDropdown);
        editCategory.setOnItemSelectedListener(this);
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
        editCategory.setAdapter(categoryAdapter);

        // Spinner for conditions
        editCondition = (Spinner) findViewById(R.id.edititemCondition);
        // on click listener
        editCondition.setOnItemSelectedListener(this);
        // drop down elements
        List<String> conditions = new ArrayList<>();
        conditions.add("New");
        conditions.add("New with defects");
        conditions.add("New without tags");
        conditions.add("Pre-owned");
        conditions.add("Certified refurbished");
        conditions.add("N/A");
        // Create adapter for spinner
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, conditions);
        // drop down style
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editCondition.setAdapter(dataAdapter);


        // now set texts
        //editImage.setImageBitmap(decodeImage(image));
        editTitle.setText(title);
        editDescription.setText(description);
        editCategory.setSelection(findPositionInSpinner(categories, category));
        editCondition.setSelection(findPositionInSpinner(conditions, condition));
        //editAuctionLength.setSelection(findPositionInSpinner(duration, auctionLength));

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // update Firebase with new information
                if (!editTitle.getText().toString().equals(title))
                {
                    database.updateTitle(postId, editTitle.getText().toString());
                }
                if (!editDescription.getText().toString().equals(description))
                {
                    database.updateDescription(postId, editDescription.getText().toString());
                }
                if (!editCategory.getSelectedItem().toString().equals(category))
                {
                    database.updateCategory(postId, editCategory.getSelectedItem().toString());
                }
                if (!editCondition.getSelectedItem().toString().equals(condition))
                {
                    database.updateCondtion(postId, editCondition.getSelectedItem().toString());
                }
                Toast.makeText(getApplicationContext(), "Successfully updated!", Toast.LENGTH_LONG).show();
                //database.updateImage(postId, image);
                finish();
            }
        });



    }
    public Bitmap decodeImage(String imageEncoding)
    {
        byte[] decoded = Base64.decode(imageEncoding.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
    }

    public int findPositionInSpinner(List<String> spinner, String value)
    {
        int spinnerPosition = 0;
        if (value != null || spinner != null)
            spinnerPosition = spinner.indexOf(value);
        return spinnerPosition;
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

