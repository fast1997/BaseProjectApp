package com.sp18.ssu370.WasteYourTime.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sp18.ssu370.baseprojectapp.R;

public class UploadActivity extends AppCompatActivity {

    private ImageView preview;
    private EditText title;
    private EditText description;
    private Button cancel;
    private Button post;
    private Button select;
    private Context thisContext = this;

    //opening gallery
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        preview = findViewById(R.id.preview_img);
        title = findViewById(R.id.upload_title_id);
        description = findViewById(R.id.upload_description_id);
        cancel = findViewById(R.id.cancel_upload_id);
        post = findViewById(R.id.post_upload_id);
        select = findViewById(R.id.select_upload_id);

        setUpCancelButton();
        setUpPostButton();
        setUpSelectButton();

    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

            imageUri = data.getData();
            preview.setImageURI(imageUri);
            Toast.makeText(thisContext,"Selected",Toast.LENGTH_SHORT).show();
    }

    private void setUpCancelButton() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(thisContext, MainActivity.class);
                thisContext.startActivity(home);
            }
        });

    }

    private void setUpPostButton() {
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(thisContext,"Posted",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setUpSelectButton() {
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(thisContext,"Please Select an Image",Toast.LENGTH_SHORT).show();
                openGallery();
            }
        });

    }
}
