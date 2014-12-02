package com.agh.gopr.app.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.agh.gopr.app.GOPRMobile;
import com.agh.gopr.app.R;
import com.agh.gopr.app.common.Preferences_;
import com.agh.gopr.app.database.entity.Note;
import com.agh.gopr.app.service.NoteService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import roboguice.fragment.RoboFragment;

@EFragment(R.layout.note_fragment)
public class NoteFragment extends RoboFragment {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
    private static final String PHOTO_FOLDER_NAME = "/photo/";
    private static final int TAKE_PHOTO_CODE = 0;

    @StringRes
    protected String notAttachedPhoto;

    @StringRes
    protected String photoTakeSuccess;

    @Pref
    protected Preferences_ preferences;

    @ViewById
    protected EditText noteEditText;

    @ViewById
    protected Button saveNoteButton;

    @ViewById
    protected TextView photoName;

    @InstanceState
    protected String photoPath;

    @Inject
    private NoteService noteService;

    private Note.Type type = Note.Type.TEXT;

    @AfterViews
    protected void init() {
        hide();
    }

    @Click(R.id.save_note_button)
    public void saveNote() {
        Note note = createNote();
        boolean result = noteService.save(note);
        if (result) {
            displayToast(getString(R.string.note_save_success));
        } else {
            displayToast(getString(R.string.note_save_error));
        }
        restoreDefaultView();
    }

    @Click(R.id.take_photo_button)
    public void takePhoto() {
        removePhotoIfExists();

        File newPhotoFile;
        try {
            String fileName = makeFileName();
            newPhotoFile = createFile(fileName);
        } catch (IOException e) {
            displayToast(getString(R.string.photo_take_error));
            return;
        }
        photoPath = newPhotoFile.getPath();
        Uri outputFileUri = Uri.fromFile(newPhotoFile);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
    }

    private void removePhotoIfExists() {
        if (photoPath != null && !photoPath.isEmpty()) {
            File photo = new File(photoPath);
            photo.delete();
        }
    }

    @Click(R.id.cancel_note_button)
    public void cancel() {
        removePhotoIfExists();
        restoreDefaultView();
    }

    @OnActivityResult(TAKE_PHOTO_CODE)
    public void onResult(int resultCode) {
        if (resultCode == Activity.RESULT_OK) {
            type = Note.Type.PHOTO;
            displayToast(photoTakeSuccess);
            String photoFileName = photoPath.substring(photoPath.lastIndexOf("/") + 1);
            photoName.setText(photoFileName);
        } else {
            File file = new File(photoPath);
            file.delete();
            photoPath = null;
        }
        show();
    }

    private Note createNote() {
        Note note = new Note();
        note.setType(type);
        note.setText(noteEditText.getText().toString());
        if (type == Note.Type.PHOTO) {
            note.setResourcePath(photoPath);
        }
        return note;
    }

    private void restoreDefaultView() {
        photoPath = null;
        noteEditText.setText(null);
        noteEditText.clearFocus();
        photoName.setText(notAttachedPhoto);
        type = Note.Type.TEXT;
        hide();
    }

    private String makeFileName() {
        Date now = new Date();
        String strDate = DATE_FORMAT.format(now);
        String actionId = preferences.actionId().get();
        return String.format("%s-%s", actionId, strDate);
    }

    private File createFile(String photoName) throws IOException {
        String dirName = String.format("%s%s", GOPRMobile.getAppDirectory(), PHOTO_FOLDER_NAME);
        File dir = new File(dirName);
        dir.mkdirs();
        String fileName = String.format("%s%s.jpg", dirName, photoName);
        File file = new File(fileName);
        file.createNewFile();
        return file;
    }

    @UiThread
    protected void displayToast(final String text) {
        Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
    }


    public void show() {
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().show(this).commit();
    }

    public void hide() {
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().hide(this).commit();
    }
}
