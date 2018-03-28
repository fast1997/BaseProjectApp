package com.sp18.ssu370.WasteYourTime.ui.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sp18.ssu370.WasteYourTime.model.ImgurImageList;
import com.sp18.ssu370.WasteYourTime.network.ImgurImageAsyncTask;
import com.sp18.ssu370.WasteYourTime.ui.view.RecyclerViewAdapter;
import com.sp18.ssu370.baseprojectapp.R;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private TextView imgLink;
    private ImageView imgThumbnail;
    private EditText searchEditText;
    private Button searchButton;
    private ImgurImageAsyncTask task;
    private RecyclerView gallery;
    private RecyclerViewAdapter myAdapter;

    private Context thisContext = this;

    private ImgurImageList imgurImageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //imgLink = findViewById(R.id.img_link);
        //imgThumbnail = findViewById(R.id.img_thumbnail);
        searchEditText = findViewById(R.id.search_edit_text);
        searchButton = findViewById(R.id.my_search_button);
        gallery = findViewById(R.id.recycler_view_id);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task = new ImgurImageAsyncTask();
                task.setListener(new ImgurImageAsyncTask.OnImgurImageFetchResponse() {
                    @Override
                    public void onCallback(ImgurImageList imgurImageList) {

                        myAdapter = new RecyclerViewAdapter(thisContext,imgurImageList);

                        gallery.setLayoutManager(new GridLayoutManager(thisContext, 2));
                        gallery.setAdapter(myAdapter);

                        //imgLink.setText(imgurImageList.getImgurImage().getMemes().get(0).getUrl());
                        //loadImageFromUrl(imgurImageList.getImgurImage().getMemes().get(0).getUrl());

                    }

                });


                String searchTerms = searchEditText.getText().toString();
                task.execute(searchTerms);
            }
        });
    }

    private void loadImageFromUrl( String url){
        Picasso.get().load(url).into(imgThumbnail);

    }
}
