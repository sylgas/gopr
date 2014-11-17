package com.agh.gopr.app.ui.fragment;

import android.app.DialogFragment;
import android.widget.Button;
import android.widget.EditText;

import com.agh.gopr.app.R;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.notes_dialog)
public class NoteDialogFragment extends DialogFragment {

    @ViewById(R.id.noteEditText)
    protected EditText noteEditText;

    @ViewById(R.id.takePhotoButton)
    protected Button takePhotoButton;

    @ViewById(R.id.saveNoteButton)
    protected Button saveNoteButton;
}
