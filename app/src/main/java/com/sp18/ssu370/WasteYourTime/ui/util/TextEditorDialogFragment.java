package com.sp18.ssu370.WasteYourTime.ui.util;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sp18.ssu370.baseprojectapp.R;

public class TextEditorDialogFragment extends DialogFragment {

    TextEditor textEditor;
    String text;

    public interface TextEditor {
        void onDone(String text);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.text_editor_popup, null));

        return builder.create();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        EditText t = getDialog().findViewById(R.id.text_popup);
        text = t.getText().toString();
        finish();
    }

    private void finish() {
        textEditor.onDone(text);
    }

    public void setOnTextEditorListener(TextEditor t) {
        textEditor = t;
    }
}
