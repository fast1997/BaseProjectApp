package com.sp18.ssu370.WasteYourTime.ui.activities;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.sp18.ssu370.WasteYourTime.ui.util.TextEditorDialogFragment;
import com.sp18.ssu370.baseprojectapp.R;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;

public class EditImageActivity extends AppCompatActivity {

    boolean drawCurrentState = false;
    boolean eraseCurrentState = false;

    Button drawButton;
    Button eraseButton;
    Button textButton;

    PhotoEditor photoEditor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_image_view);

        drawButton = findViewById(R.id.draw_button);
        eraseButton = findViewById(R.id.erase_button);
        textButton = findViewById(R.id.text_button);
        Button undoButton = findViewById(R.id.undo_button);

        PhotoEditorView photoEditorView = findViewById(R.id.image_id);
        photoEditorView.getSource().setImageResource(R.drawable.joyofmotion);

        Typeface font = ResourcesCompat.getFont(this, R.font.roboto_regular);
        //Typeface emoji = Typeface.createFromAsset(getAssets(), "emojione-android.ttf");

        photoEditor = new PhotoEditor.Builder(this, photoEditorView)
                .setPinchTextScalable(true)
                .setDefaultTextTypeface(font)
                //.setDefaultEmojiTypeface(emoji)
                .build();

        drawButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                toggleDrawButton();
            }
        });

        eraseButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                toggleEraseButton();
            }
        });

        textButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleTextButton();
            }
        });


        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoEditor.undo();
            }
        });

    }

    public void toggleDrawButton() {
        drawCurrentState = !drawCurrentState;
        if (drawCurrentState) {
            photoEditor.setBrushDrawingMode(true);
            drawButton.setBackgroundColor(getResources().getColor(R.color.darkteal));

            if (eraseCurrentState) {
                eraseButton.setBackgroundColor(getResources().getColor(R.color.teal));
                eraseCurrentState = false;
            }
        }
        else {
            photoEditor.setBrushDrawingMode(false);
            drawButton.setBackgroundColor(getResources().getColor(R.color.teal));
        }
    }

    public void toggleEraseButton() {
        eraseCurrentState = !eraseCurrentState;
        if (eraseCurrentState) {
            photoEditor.brushEraser();
            eraseButton.setBackgroundColor(getResources().getColor(R.color.darkteal));

            if (drawCurrentState) {
                drawButton.setBackgroundColor(getResources().getColor(R.color.teal));
                drawCurrentState = false;
            }
        }
        else {
            photoEditor.setBrushDrawingMode(false);
            eraseButton.setBackgroundColor(getResources().getColor(R.color.teal));
        }
    }

    public void toggleTextButton() {
        TextEditorDialogFragment textEditorDialogFragment = TextEditorDialogFragment.show(this);
        textEditorDialogFragment.setOnTextEditorListener(
                new TextEditorDialogFragment.TextEditor() {
            @Override
            public void onDone(String text, int color) {
                    photoEditor.addText(text, color);
            }
        });
        if (eraseCurrentState) {
            eraseButton.setBackgroundColor(getResources().getColor(R.color.teal));
            eraseCurrentState = false;
        }
        if (drawCurrentState) {
            drawButton.setBackgroundColor(getResources().getColor(R.color.teal));
            drawCurrentState = false;
        }
    }
}
