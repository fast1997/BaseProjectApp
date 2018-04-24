package com.sp18.ssu370.WasteYourTime.ui.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.sp18.ssu370.baseprojectapp.R;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;

public class EditImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_image_view);

        final Button drawButton = findViewById(R.id.draw_button);
        final Button eraseButton = findViewById(R.id.erase_button);
        Button textButton = findViewById(R.id.text_button);
        Button undoButton = findViewById(R.id.undo_button);


        PhotoEditorView photoEditorView = findViewById(R.id.image_id);
        photoEditorView.getSource().setImageResource(R.drawable.joyofmotion);

        Typeface font = ResourcesCompat.getFont(this, R.font.roboto_regular);
        //Typeface emoji = Typeface.createFromAsset(getAssets(), "emojione-android.ttf");

        final PhotoEditor photoEditor = new PhotoEditor.Builder(this, photoEditorView)
                .setPinchTextScalable(true)
                .setDefaultTextTypeface(font)
                //.setDefaultEmojiTypeface(emoji)
                .build();

        drawButton.setOnClickListener(new View.OnClickListener() {
            boolean drawCurrentState = false;

            @Override
            public void onClick(View view) {
                drawCurrentState = !drawCurrentState;
                if (drawCurrentState) {
                    turnOn(view);
                }
                else
                {
                    turnOff(view);
                }
            }

            public void turnOn(View view) {
                photoEditor.setBrushDrawingMode(true);
                view.setBackgroundColor(getResources().getColor(R.color.darkteal));
                drawCurrentState = true;
            }

            public void turnOff(View view) {
                photoEditor.setBrushDrawingMode(false);
                view.setBackgroundColor(getResources().getColor(R.color.teal));
                drawCurrentState = false;
            }
        });

        eraseButton.setOnClickListener(new View.OnClickListener() {
            boolean eraseCurrentState = false;

            @Override
            public void onClick(View view) {
                eraseCurrentState = !eraseCurrentState;
                if (eraseCurrentState) {
                    turnOn(view);
                }
                else {
                    turnOff(view);
                }
            }

            public void turnOn(View view) {
                photoEditor.brushEraser();
                view.setBackgroundColor(getResources().getColor(R.color.darkteal));
                eraseCurrentState = true;
            }

            public void turnOff(View view) {
                photoEditor.setBrushDrawingMode(false);
                view.setBackgroundColor(getResources().getColor(R.color.teal));
                eraseCurrentState = false;
            }
        });

        textButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoEditor.addText("Hi", R.color.white);
            }
        });


        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoEditor.undo();
            }
        });

    }
}
