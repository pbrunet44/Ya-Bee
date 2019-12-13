package com.example.yabeeprototypes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;

import static android.graphics.BitmapFactory.decodeByteArray;

public class PreviewPost extends Activity {

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_post);
        ImageView previewImage = findViewById(R.id.postImagePreview);
        TextView previewTitle = findViewById(R.id.postTitlePreview);
        TextView previewPrice = findViewById(R.id.lowestBidPreview);
        TextView previewTimer = findViewById(R.id.timerPreview);
        Button continueEditing = findViewById(R.id.continueEditingButton);
        TextView previewCondition = findViewById(R.id.postConditionPreview);
        TextView previewCategory = findViewById(R.id.postCategoryPreview);
        TextView previewClicks = findViewById(R.id.postClicksPreview);
        TextView previewDescription = findViewById(R.id.postDescriptionPreview);
        LinearLayout previewConditionDisplay = findViewById(R.id.conditionDisplayPreview);

        Intent intent = getIntent();
        // get bundle from intent
        Bundle extras = intent.getExtras();

//        byte[] decoded = Base64.decode(extras.getString("ImageEncoding").getBytes(), Base64.DEFAULT);
//        Bitmap bitmap = BitmapFactory.decodeByteArray(decoded, 0, decoded.length);

        String fileName = extras.getString("FileName");
        if(fileName != null) {
            try {
                bitmap = BitmapFactory.decodeStream(getApplicationContext().openFileInput(fileName));
            } catch (FileNotFoundException e) {
                System.out.println("Zoinks");
            }
        }

        previewImage.setImageBitmap(bitmap);
        previewTitle.setText(extras.getString("Title"));
        DecimalFormat df = new DecimalFormat("#.##");
        previewPrice.setText("Max Price: $" + df.format(extras.getDouble("MaxPrice")) + " No bids yet!");
        previewTimer.setText(extras.getString("Timer"));
        if(extras.getString("Condition").equals("N/A"))
        {
            previewConditionDisplay.setVisibility(View.INVISIBLE);
        }
        else
        {
            previewCondition.setText(extras.getString("Condition"));
        }
        previewCategory.setText(extras.getString("Category"));
        previewClicks.setText("0");
        previewDescription.setText(extras.getString("Description"));

        continueEditing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
