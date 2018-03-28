package com.sp18.ssu370.WasteYourTime.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.sp18.ssu370.baseprojectapp.R;
import com.squareup.picasso.Picasso;

public class ImageActivity extends AppCompatActivity {

    private TextView titleView;
    private TextView descriptionView;
    private ImageView thumbnailView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        titleView = findViewById(R.id.image_view_title_id);
        descriptionView = findViewById(R.id.image_view_description_id);
        thumbnailView = findViewById(R.id.image_view_thumbnail_id);

        //Receive data

        Intent intent = getIntent();
        String title = intent.getExtras().getString("Title");
        String thumbnail = intent.getExtras().getString("Thumbnail");
        String description = intent.getExtras().getString("Description");
        boolean animated = intent.getExtras().getBoolean("Animated");


        Picasso.get().load(thumbnail).into(thumbnailView);
        titleView.setText(title);
        descriptionView.setText(description);
    }
}
