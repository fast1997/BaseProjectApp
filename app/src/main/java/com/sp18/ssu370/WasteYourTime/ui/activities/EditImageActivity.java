package com.sp18.ssu370.WasteYourTime.ui.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sp18.ssu370.baseprojectapp.R;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;

public class EditImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_view);

        PhotoEditorView photoEditorView = findViewById(R.id.meme_image_id);
        photoEditorView.getSource().setImageResource(R.drawable.joyofmotion);

        //Typeface font = Typeface.createFromAsset(getAssets(), "blahblah.meme");
        //Typeface emoji = Typeface.createFromAsset(getAssets(), "emojione-android.ttf");

        PhotoEditor photoEditor = new PhotoEditor.Builder(this, photoEditorView)
                .setPinchTextScalable(true)
                //.setDefaultTextTypeface(font)
                //.setDefaultEmojiTypeface(emoji)
                .build();
    }
}
