package com.sp18.ssu370.WasteYourTime.ui.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

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
    Button undoButton;

    SeekBar brushSizeBar;
    SeekBar brushColorBar;

    PhotoEditor photoEditor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_image_view);

        drawButton = findViewById(R.id.draw_button);
        eraseButton = findViewById(R.id.erase_button);
        textButton = findViewById(R.id.text_button);
        undoButton = findViewById(R.id.undo_button);

        brushSizeBar = findViewById(R.id.brush_sizebar);
        brushColorBar = findViewById(R.id.brush_colorbar);

        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);

        PhotoEditorView photoEditorView = findViewById(R.id.image_id);
        photoEditorView.getSource().setImageResource(R.drawable.joyofmotion);

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

        brushColorBar.setMax(1275);
        brushColorBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                alterBrushColor(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

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
        TextEditorDialogFragment editorDialogFragment = new TextEditorDialogFragment();
        editorDialogFragment.setOnTextEditorListener(new TextEditorDialogFragment.TextEditor() {
            @Override
            public void onDone(String text) {
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

    public void alterBrushColor(int progress) {
        int color;
        int red, green, blue;
        red = green = blue = 0;

        //0-255 red - yellow
        if (progress <= 255) {
            red = 255;
            green = progress;
            blue = 0;
        }

        //256-510 yellow - green
        else if (progress <= 510) {
            red = 255 - (progress - 255);
            green = 255;
            blue = 0;
        }

        //511-765 green - blue
        else if (progress <= 765) {
            red = 0;
            green = 255 - (progress - 510);
            blue = progress - 510;
        }

        //765-1020 blue - purple
        else if (progress <= 1020) {
            red = progress - 765;
            green = 0;
            blue = 255;
        }

        //1021-1275 purple - red
        else if (progress <= 1275) {
            red = 255;
            green = 0;
            blue = 255 - (progress - 1020);
        }

        red = red << 16;
        green= green << 8;
        color = red | green | blue;

        if (progress == 0) {
            color = 0x000000;
        }
        if (progress == 1275) {
            color = 0xffffff;
        }

        photoEditor.setBrushColor(color);

        if (eraseCurrentState) {
            photoEditor.brushEraser();
        }
    }
}
