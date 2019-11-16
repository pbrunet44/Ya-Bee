package com.example.yabeeprototypes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.List;

public class MakeBid extends AppCompatActivity {
    private final static int RESULT_LOAD_IMAGE = 1;
    private Uri imageUri = null;
    private String imageEncoding = "";
    ImageView bidImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_bid);
        System.out.println("I'm MakeBid's databaseHelper!");
        final DatabaseHelper database = new DatabaseHelper();
        // getting post id so we can therefore get post information
        Intent intent = getIntent();
        final String postID = intent.getStringExtra("POST ID");
        bidImage = (ImageView) findViewById(R.id.bidImage);

        System.out.println("MakeBid - Post id:" + postID);
        final Button submitBid = (Button) findViewById(R.id.btnSubmit);
        final Button uploadImage = (Button) findViewById(R.id.btnUploadImage);
        //final Post post = database.getPostByID(postID);
        database.getPosts(new FirebaseCallback() {
            @Override
            public void onCallback(List<Post> posts) {
                // find post with appropriate id
                final Post post = database.getPostByID(postID, posts);
                System.out.println(post.toString());
                uploadImage.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
                    }
                });
                submitBid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    String imageEncoding = ""; // null for now, will go back to take into account images
                    if(imageUri != null)
                    {
                        Bitmap imageBitmap = ((BitmapDrawable) bidImage.getDrawable()).getBitmap();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        imageEncoding = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                    }
                    String description = ((TextView) findViewById(R.id.etBidDescription)).getText().toString();
                    double price = Double.parseDouble(((TextView) findViewById(R.id.etBidPrice)).getText().toString());
                    Bid bid = new Bid(price, description, imageEncoding);
                    if (post.verifyBid(bid))
                    {
                        post.updateNewLowestBid(bid);

                        //post.getBuyer();
                        //send a notifcation to buyer that they're post has had a bid submitted


                        finish();// go back to view post after submitting bid
                    }
                    else
                    {
                        // prompt a toast telling user to enter a valid bid
                        DecimalFormat df = new DecimalFormat("#.##");
                        String currBidPrice = df.format(post.getLowestBid().price);
                        String toastText = "The current bid is $" + currBidPrice + ". Please bid lower.";
                        Toast invalidBid = Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_LONG);
                        invalidBid.setGravity(Gravity.TOP, 0, 0);
                        invalidBid.show();
                    }
                    }

                });
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null)
        {
            Uri selectedImage = data.getData();
            imageUri = selectedImage;
            bidImage.setImageURI(selectedImage);
        }
    }
}