package com.example.yabeeprototypes;


import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MakeListing extends Activity implements AdapterView.OnItemSelectedListener {
    // initial values for bid price, imageurl, initial description respectively
    static final double INITIAL_BID_PRICE = Double.MAX_VALUE;
    private final static int RESULT_LOAD_IMAGE = 1;
    static final String INITIAL_IMAGE = "";
    private Uri imageUri = null;
    private String imageEncoding = "";
    static final String INITIAL_DESCRIPTION = "";

    private TextView title;
    private TextView description;
    private Spinner category;
    private Spinner condition;
    private Spinner durationOfAuction;
    private TextView maxPrice;



    ImageView postImage;

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() == null)
        {
            Intent intent = new Intent(MakeListing.this, AccountOptions.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_listing);
        Button makePost = findViewById(R.id.collectPostInfo);
        Button previewPost = findViewById(R.id.previewPost);
        final DatabaseHelper database = new DatabaseHelper();

        title = (TextView)findViewById(R.id.titleEntry);
        description = (TextView)findViewById(R.id.descEntry);
        category =(Spinner)findViewById(R.id.categoryDropdown);
        condition = (Spinner)findViewById(R.id.itemCondition);
        durationOfAuction = (Spinner)findViewById(R.id.auction_duration);
        maxPrice = (TextView)findViewById(R.id.yourPrice);


        postImage = findViewById(R.id.postImage);
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
                if (isValid())
                {
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
                    if(imageUri != null)
                    {
                        Bitmap imageBitmap = ((BitmapDrawable) postImage.getDrawable()).getBitmap();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        imageEncoding = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                    }
                    Post post = new Post(new ArrayList<Bid>(), new ArrayList<Notification>(), new ArrayList<User>(), new User(FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                            FirebaseAuth.getInstance().getCurrentUser().getUid()), title, maxPrice,
                            description, numOfDays, new Bid(INITIAL_BID_PRICE, INITIAL_DESCRIPTION, INITIAL_IMAGE, null),
                            imageEncoding, category, condition, Long.toString(System.nanoTime()),
                            date, false, 0);
                    database.writeNewPost(post);
                    Toast successToast = Toast.makeText(getApplicationContext(), "Successful post creation!", Toast.LENGTH_LONG);
                    successToast.setGravity(Gravity.TOP, 0, 0);
                    successToast.show();
                    finish();
                    Intent intent = new Intent(MakeListing.this, MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    printToastMessage();
                }
            }
        });

        postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

        previewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle postExtras = new Bundle();
                // adding key value pairs to this bundle
                //postExtras.putString("Image", post.getImageEncoding());
                postExtras.putString("Title", ((TextView)findViewById(R.id.titleEntry)).getText().toString());
                postExtras.putString("Description", ((TextView)findViewById(R.id.descEntry)).getText().toString());
                postExtras.putString("Category", ((Spinner)findViewById(R.id.categoryDropdown)).getSelectedItem().toString());
                postExtras.putString("Condition", ((Spinner)findViewById(R.id.itemCondition)).getSelectedItem().toString());
                postExtras.putString("Timer", ((Spinner)findViewById(R.id.auction_duration)).getSelectedItem().toString() + ", 0 hours");
                if(imageUri != null)
                {
                    Activity activity = (Activity) v.getContext();
                    Bitmap imageBitmap = ((BitmapDrawable) postImage.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    imageEncoding = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                    postExtras.putString("FileName", createImageFromBitmap(imageBitmap, activity));
                }
                //postExtras.putString("ImageEncoding", imageEncoding);
                if(((TextView)findViewById(R.id.yourPrice)).getText().toString().equals(""))
                {
                    postExtras.putDouble("MaxPrice", 0.0);
                }
                else
                {
                    postExtras.putDouble("MaxPrice", Double.parseDouble(((TextView)findViewById(R.id.yourPrice)).getText().toString()));
                }
                if (isValid())
                {
                    Intent intent = new Intent(getApplicationContext(), PreviewPost.class);
                    intent.putExtras(postExtras);
                    startActivity(intent);
                }
                else
                {
                    printToastMessage();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    // checks if the user has filled out all the necessary attributes of the form
    public boolean isValid()
    {
        if (postImage == null)
            return false;
        if (title == null || TextUtils.isEmpty(title.getText()))
            return false;
        if (category.getSelectedItem() == null || TextUtils.isEmpty(category.getSelectedItem().toString()))
            return false;
        if (condition.getSelectedItem() == null || TextUtils.isEmpty(condition.getSelectedItem().toString()))
            return false;
        if (durationOfAuction.getSelectedItem() ==  null ||TextUtils.isEmpty(durationOfAuction.getSelectedItem().toString()))
            return false;
        if (maxPrice == null || TextUtils.isEmpty(maxPrice.getText()))
            return false;
        return true;
    }

    public void printToastMessage()
    {
        if (postImage == null)
            Toast.makeText(getApplicationContext(), "Please select an image.", Toast.LENGTH_SHORT).show();
        if (title == null || TextUtils.isEmpty(title.getText()))
            Toast.makeText(getApplicationContext(), "Please input a title.", Toast.LENGTH_SHORT).show();
        if (category.getSelectedItem() == null || TextUtils.isEmpty(category.getSelectedItem().toString()))
            Toast.makeText(getApplicationContext(), "Please select a category.", Toast.LENGTH_SHORT).show();
        if (condition.getSelectedItem() == null || TextUtils.isEmpty(condition.getSelectedItem().toString()))
            Toast.makeText(getApplicationContext(), "Please select the item's condition.", Toast.LENGTH_SHORT).show();
        if (durationOfAuction.getSelectedItem() ==  null ||TextUtils.isEmpty(durationOfAuction.getSelectedItem().toString()))
            Toast.makeText(getApplicationContext(), "Please select the auction duration.", Toast.LENGTH_SHORT).show();
        if (maxPrice == null || TextUtils.isEmpty(maxPrice.getText()))
            Toast.makeText(getApplicationContext(), "Please input the maximum price you are willing to pay for the item.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null)
        {
            Uri selectedImage = data.getData();
            imageUri = selectedImage;
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
    //sending image to next activity
    private String createImageFromBitmap(Bitmap bitmap, Activity activity) {
        String fileName = "previewPostImage";
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            FileOutputStream fo = activity.openFileOutput(fileName, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
            fileName = null;
        }
        return fileName;
    }

}

