package com.sp18.ssu370.WasteYourTime.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sp18.ssu370.WasteYourTime.model.Album;
import com.sp18.ssu370.WasteYourTime.model.ImgurImage;
import com.sp18.ssu370.WasteYourTime.network.AlbumAsyncTask;
import com.sp18.ssu370.WasteYourTime.ui.view.ImageRecyclerViewAdapter;
import com.sp18.ssu370.baseprojectapp.R;

import java.util.ArrayList;

import static android.widget.LinearLayout.VERTICAL;

public class ImageActivity extends AppCompatActivity {

    private Context thisContext = this;

    private TextView title;
    private RecyclerView picRecyclerView;
    private ImageRecyclerViewAdapter myAdapter;

    private ArrayList<ImgurImage> currentAlbumPics;

    private AlbumAsyncTask task;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        picRecyclerView = findViewById(R.id.big_image_recycler_view_id);
        picRecyclerView.setHasFixedSize(true);
        picRecyclerView.setItemViewCacheSize(20);
        picRecyclerView.setDrawingCacheEnabled(true);
        picRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        title = findViewById(R.id.image_view_galley_id);

        //Receive data
        Intent intent = getIntent();
        String galleryTitle = intent.getStringExtra("GalleryTitle");
        Bundle args = intent.getBundleExtra("AllImg");
        //allPics = (ArrayList<ImgurImage>) args.getSerializable("ImageArrayList");
        String galID = intent.getStringExtra("GalleryID");

        title.setText(galleryTitle);

        task = new AlbumAsyncTask();
        task.setListener(new AlbumAsyncTask.OnAlbumFetchResponse() {
            @Override
            public void onCallback(Album album) {

                currentAlbumPics = album.getSingleData().getImages();
                setUpRecyclerView();
            }
        });

        String type = "getGalleryAlbum";
        task.execute(type,galID);
    }

    public void setUpRecyclerView(){
        myAdapter = new ImageRecyclerViewAdapter(thisContext, currentAlbumPics);
        LinearLayoutManager layoutManager = new LinearLayoutManager(thisContext, VERTICAL, false);
        picRecyclerView.setLayoutManager(layoutManager);
        picRecyclerView.setAdapter(myAdapter);
    }
}
