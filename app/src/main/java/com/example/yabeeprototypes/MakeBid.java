package com.example.yabeeprototypes;

import androidx.appcompat.app.AppCompatActivity;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MakeBid extends AppCompatActivity {
    private final static int RESULT_LOAD_IMAGE = 1;
    private Uri imageUri = null;
    private String imageEncoding = "";
    ImageView bidImage;
    FirebaseUser currentUser;
    ArrayList<User> bidders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_bid);


        System.out.println("I'm MakeBid's databaseHelper!");
        final DatabaseHelper database = new DatabaseHelper();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        // getting post id so we can therefore get post information
        Intent intent = getIntent();
        final String postID = intent.getStringExtra("POST ID");
        bidImage = (ImageView) findViewById(R.id.bidImage);

        System.out.println("MakeBid - Post id:" + postID);
        final Button submitBid = (Button) findViewById(R.id.btnSubmit);
        //final Button uploadImage = (Button) findViewById(R.id.btnUploadImage);
        //final Post post = database.getPostByID(postID);
        database.getPosts(new FirebaseCallback() {
            @Override
            public void onCallback(List<Post> posts) {
                // find post with appropriate id
                final Post post = database.getPostByID(postID, posts);
                System.out.println(post.toString());
                bidImage.setOnClickListener(new View.OnClickListener(){
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
                    Bid bid = new Bid(price, description, imageEncoding, new User(currentUser.getEmail(), currentUser.getUid()));
                    if (post.verifyBid(bid))
                    {
                        //post.updateNewLowestBid(bid);

                        Notification tinder = new Notification("TINDER", post.getBuyer(), post.getId());
                        post.addNotification(tinder);

                        post.addToBidPendingAcceptance(bid);

                        /*
                        Bid acceptance and how it will work:
                        1.  buyer gets tinder notification ("New bid on your post! Tap to view.")
                        2.  bid gets added to a "bid pending acceptance arraylist" that will probably be tied to the post.
                        3.  tinder notification for buyer takes them to the post. On the post there will be a button that
                            takes the user to a recycler view of all the bids pending acceptance from the post.
                        4.  buyer will scroll through recyclerview of bids pending acceptance and click on each one to accept or decline them.
                        4a. declining bid will take user back to recyclerview of bids pending acceptance
                        4b. accepting bid will take user back to view post
                        5.  Declined bid simply removed from arraylist of pending acceptance. Notification
                            sends to seller ("Bid declined by buyer! Tap to view post.")
                        6.  Accepted bid updated as new lowest bid, then removed from arraylist of pending acceptance.
                            Notifcation sends to seller ("Bid accepted by buyer! You are the current bid.")
                        */


                        if (!post.alreadyBid(currentUser.getUid()))
                        {
                            post.addBiddertoList(new User(currentUser.getEmail(), currentUser.getUid()));
                        }

                        bidders = new ArrayList<>();
                        bidders = post.getAllBidders();

                        for(User u: bidders)
                        {
                            if(!u.getUid().equals(currentUser.getUid()))
                            {
                                Notification notification = new Notification("BID", u, post.getId());
                                post.addNotification(notification);
                            }
                        }

                        System.out.println("I'm in MakeBid, i'm allbids, here's my size" + post.getAllBidders().size());
                        //post.getBuyer();
                        //send a notifcation to buyer that they're post has had a bid submitted
                        finish();// go back to view post after submitting bid
                    }
                    else
                    {
                        // prompt a toast telling user to enter a valid bid
                        DecimalFormat df = new DecimalFormat("#.##");
                        String currBidPrice = df.format(post.getLowestBid().getPrice());
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
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
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