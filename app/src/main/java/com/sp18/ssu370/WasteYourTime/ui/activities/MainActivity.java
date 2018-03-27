package com.sp18.ssu370.WasteYourTime.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sp18.ssu370.WasteYourTime.model.ImgurImageList;
import com.sp18.ssu370.WasteYourTime.network.ImgurImageAsyncTask;
import com.sp18.ssu370.baseprojectapp.R;

public class MainActivity extends AppCompatActivity {

    private TextView imgLink;
    private ImageView imgThumbnail;
    private EditText searchEditText;
    private Button searchButton;
    private ImgurImageAsyncTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgLink = (TextView)findViewById(R.id.img_link);
        imgThumbnail = (ImageView) findViewById(R.id.img_thumbnail);
        searchEditText = (EditText) findViewById(R.id.search_edit_text);
        searchButton = (Button) findViewById(R.id.my_search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task = new ImgurImageAsyncTask();
                task.setListener(new ImgurImageAsyncTask.OnImgurImageFetchResponse() {
                    @Override
                    public void onCallback(ImgurImageList imgurImageList) {
                        imgLink.setText(imgurImageList.getImgurImage().get(0).getLink());
                    }
                });

                String searchTerms = searchEditText.getText().toString();
                task.execute(searchTerms);
            }
        });
    }
}
