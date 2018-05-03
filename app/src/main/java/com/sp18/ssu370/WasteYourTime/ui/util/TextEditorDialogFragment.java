package com.sp18.ssu370.WasteYourTime.ui.util;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;

import com.sp18.ssu370.baseprojectapp.R;

public class TextEditorDialogFragment extends DialogFragment {

    TextEditor textEditor;

    public interface TextEditor {
        void onDone(String text, int color);
    }

    public static TextEditorDialogFragment show(AppCompatActivity activity) {
        Bundle args = new Bundle();
        args.putString("extra_input_text", "");
        args.putInt("extra_color_code", R.color.white);
        TextEditorDialogFragment fragment = new TextEditorDialogFragment();
        fragment.setArguments(args);
        fragment.show(activity.getSupportFragmentManager(), "Tag");
        return fragment;
    }

    public void setOnTextEditorListener(TextEditor t) {
        textEditor = t;
    }
}
