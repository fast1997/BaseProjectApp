package com.sp18.ssu370.WasteYourTime.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.sp18.ssu370.baseprojectapp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class SingleItemActivity extends AppCompatActivity{

    private ImageView singlePic;
    private BottomNavigationView bottomNavigationView;
    private Context thisContext = this;
    private String urlLink;
    private boolean isAnimated;
    private PhotoView photoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_item);
        //singlePic = findViewById(R.id.single_item_pic);
        photoView = findViewById(R.id.single_item_pic);

        Intent intent = getIntent();
        urlLink = intent.getStringExtra("ImageURL");
        isAnimated = intent.getBooleanExtra("Animated",false);

        if(isAnimated){
            GlideDrawableImageViewTarget gifLoad = new GlideDrawableImageViewTarget(photoView);

            Glide.with(thisContext)
                    .load(urlLink)
                    .into(gifLoad);

        }
        else{
            Glide.with(thisContext)
                    .load(urlLink)
                    .into(photoView);
        }


        initBottomNavigation();
        
    }
    public void initBottomNavigation(){
        bottomNavigationView = findViewById(R.id.single_item_menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.save_fav:
                        Toast.makeText(thisContext,"Saved",Toast.LENGTH_SHORT).show();
                        save(photoView);

                        break;
                }
                return true;
            }
        });
    }

    private void save(ImageView imageView){
        imageView.setDrawingCacheEnabled(true);
        Bitmap b = imageView.getDrawingCache();
        MediaStore.Images.Media.insertImage(thisContext.getContentResolver(), b,"Title", "description");
    }


}
