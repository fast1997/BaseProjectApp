package com.sp18.ssu370.WasteYourTime.ui.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.felipecsl.gifimageview.library.GifImageView;
import com.sp18.ssu370.baseprojectapp.R;

public class GifActivity extends AppCompatActivity{

    private GifImageView gifImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gif_view);

        gifImageView = findViewById(R.id.gif_image_id);
        //gifImageView.setBytes();

        gifImageView.setOnFrameAvailable(new GifImageView.OnFrameAvailable() {
            @Override
            public Bitmap onFrameAvailable(Bitmap bitmap) {
                return bitmap;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        gifImageView.startAnimation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        gifImageView.stopAnimation();
    }
}
