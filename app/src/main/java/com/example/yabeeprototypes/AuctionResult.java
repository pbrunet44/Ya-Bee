package com.example.yabeeprototypes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.List;

public class AuctionResult extends AppCompatActivity {

    private String postID;
    private Post post;
    private static DatabaseHelper databaseHelper = new DatabaseHelper();
    private TextView purchasingFrom;
    private TextView auctionPrice;
    private TextView itemTitle;
    private ImageView auctionPicture;
    private Button payPalButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_result);

        // initialize
        purchasingFrom = (TextView) findViewById(R.id.purchasingFrom);
        auctionPicture = (ImageView) findViewById(R.id.auctionImage);
        itemTitle = (TextView) findViewById(R.id.auctionItemTitle);
        auctionPrice = (TextView) findViewById(R.id.auctionPrice);
        payPalButton = (Button) findViewById(R.id.paypal_button);

        // get intent and post id
        Intent intent = getIntent();
        postID = intent.getStringExtra("postID");

        // now get post from database helper
        databaseHelper.getPosts(new FirebaseCallback() {
            @Override
            public void onCallback(List<Post> posts) {
                post = databaseHelper.getPostByID(postID, posts);
                if (post != null)
                {
                    // set new values for textviews and imageview
                    String newPurchasingFrom = "Thank you for purchasing from " + post.getLowestBid().getSeller() + ".";
                    String newAuctionPrice = Double.toString(post.getLowestBid().getPrice());

                    purchasingFrom.setText(newPurchasingFrom);
                    auctionPrice.setText(newAuctionPrice);
                    auctionPicture.setImageBitmap(post.decodeImage());
                    itemTitle.setText(post.getTitle());

                }
            }
        });
        payPalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beginPayment(view);
            }
        });
    }

    public void beginPayment(View view)
    {
        Intent serviceConfig = new Intent(this, PayPalService.class);
        serviceConfig.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, PayPalConfig.CONFIG);
        startService(serviceConfig);

        PayPalPayment payment = new PayPalPayment(new BigDecimal(auctionPrice.getText().toString()), "USD", itemTitle.getText().toString(), PayPalPayment.PAYMENT_INTENT_SALE);

        Intent paymentConfig = new Intent(this, PaymentActivity.class);
        paymentConfig.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, PayPalConfig.CONFIG); //send the same configuration for restart resiliency
        paymentConfig.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(paymentConfig, 0);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data){
        if (resultCode == Activity.RESULT_OK){
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null){
                try {
                    Log.i("ListUp:", confirm.toJSONObject().toString(4));
                } catch (JSONException e) {
                    Log.e("ListUp:", "an extremely unlikely failure occurred: ", e);
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("ListUp:", "The user canceled.");
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("ListUp:", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }

    @Override
    public void onDestroy(){
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }


}
