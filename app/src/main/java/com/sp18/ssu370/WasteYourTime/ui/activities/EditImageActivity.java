package com.sp18.ssu370.WasteYourTime.ui.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.rtugeek.android.colorseekbar.ColorSeekBar;
import com.sp18.ssu370.WasteYourTime.ui.util.TextEditorDialogFragment;
import com.sp18.ssu370.baseprojectapp.R;

import java.io.File;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;

public class EditImageActivity extends AppCompatActivity {

    boolean drawCurrentState = false;
    boolean eraseCurrentState = false;

    Button drawButton;
    Button eraseButton;
    Button textButton;
    Button undoButton;
    Button redoButton;
    Button saveButton;

    SeekBar brushSizeBar;
    ColorSeekBar brushColorBar;

    PhotoEditor photoEditor;

    String chosenPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_image_view);

        drawButton = findViewById(R.id.draw_button);
        eraseButton = findViewById(R.id.erase_button);
        textButton = findViewById(R.id.text_button);
        undoButton = findViewById(R.id.undo_button);
        redoButton = findViewById(R.id.redo_button);
        saveButton = findViewById(R.id.save_button);

        brushSizeBar = findViewById(R.id.brush_sizebar);
        brushColorBar = findViewById(R.id.brush_colorbar);

        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);

        PhotoEditorView photoEditorView = findViewById(R.id.image_id);

        chosenPath = getIntent().getStringExtra("EXTRA_CHOSEN_PATH");

        if (chosenPath == null || chosenPath.equals("")) {
            Toast.makeText(this, "Oh god how did this happen", Toast.LENGTH_LONG)
                    .show();
        }
        else {
            Bitmap b = BitmapFactory.decodeFile(chosenPath);
            photoEditorView.getSource().setImageBitmap(b);
        }

        Typeface font = ResourcesCompat.getFont(this, R.font.roboto_regular);

        photoEditor = new PhotoEditor.Builder(this, photoEditorView)
                .setPinchTextScalable(true)
                .setDefaultTextTypeface(font)
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
                view.startAnimation(animAlpha);
            }
        });

        redoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                photoEditor.redo();
                view.startAnimation(animAlpha);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                photoEditor.saveImage(chosenPath, new PhotoEditor.OnSaveListener() {
                    @Override
                    public void onSuccess(@NonNull String imagePath) {
                        doToast("Image saved successfully");
                        leaveActivity();
                    }

                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        doToast("Image saving failed");
                    }
                });
            }

        });



        brushSizeBar.setProgress(25);
        brushSizeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                alterBrushSize(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        brushColorBar.setMaxPosition(100);
        brushColorBar.setBarHeight(5);
        brushColorBar.setOnColorChangeListener(new ColorSeekBar.OnColorChangeListener() {
            @Override
            public void onColorChangeListener(int i, int i1, int i2) {
                alterBrushColor(i, i1, i2);
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
        textButton.setBackgroundColor(getResources().getColor(R.color.darkteal));

        TextEditorDialogFragment editorDialogFragment = new TextEditorDialogFragment();
        editorDialogFragment.setOnTextEditorListener(new TextEditorDialogFragment.TextEditor() {
            @Override
            public void onDone(String text) {
                textButton.setBackgroundColor(getResources().getColor(R.color.teal));
                photoEditor.addText(text, getResources().getColor(R.color.black));
            }
        });

        editorDialogFragment.show(getSupportFragmentManager(), "Text_Editor");

        if (eraseCurrentState) {
            eraseButton.setBackgroundColor(getResources().getColor(R.color.teal));
            eraseCurrentState = false;
        }
        if (drawCurrentState) {
            drawButton.setBackgroundColor(getResources().getColor(R.color.teal));
            drawCurrentState = false;
        }
    }

    public void alterBrushSize(int size) {
        photoEditor.setBrushSize(size);
        if (eraseCurrentState) {
            photoEditor.brushEraser();
        }
    }

    public void alterBrushColor(int progress, int alpha_progress, int color) {

        if (progress == 0) {
            color = 0x000000;
        }
        if (progress == 100) {
            color = 0xffffff;
        }

        photoEditor.setBrushColor(color);

        if (eraseCurrentState) {
            photoEditor.brushEraser();
        }
    }

    private void doToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void leaveActivity() {
        Context thisContext = this;
        Intent upload = new Intent(thisContext, UploadActivity.class);
        upload.putExtra("EXTRA_CAME_FROM_EDIT", chosenPath);
        thisContext.startActivity(upload);
    }
}
