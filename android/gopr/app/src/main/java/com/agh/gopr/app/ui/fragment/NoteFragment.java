package com.agh.gopr.app.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.agh.gopr.app.R;
import com.agh.gopr.app.common.Preferences_;
import com.agh.gopr.app.database.entity.Note;
import com.agh.gopr.app.service.NoteService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import roboguice.fragment.RoboFragment;

@EFragment(R.layout.note_fragment)
public class NoteFragment extends RoboFragment {

    final static String PHOTO_FOLDER_NAME = "/photoFolder/";
    final static int TAKE_PHOTO_CODE = 0;

    @Inject
    private NoteService noteService;

    @Pref
    protected Preferences_ preferences;

    @ViewById
    protected EditText noteEditText;

    @ViewById
    protected Button takePhotoButton;

    @ViewById
    protected Button saveNoteButton;

    @InstanceState
    protected String photoPath;

    private Note.Type type = Note.Type.TEXT;

    @AfterViews
    protected void init(){
        hide();
    }

    @Click(R.id.save_note_button)
    public void saveNote(){
        Note note = createNote();
        boolean result = noteService.save(note);
        if (result)
            displayToast(getString(R.string.note_save_success));
        else
            displayToast(getString(R.string.note_save_error));
        restoreDefaultView();
    }

    @Click(R.id.take_photo_button)
    public void takePhoto(){
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

    @Click(R.id.cancel_note_button)
    public void cancel(){
        if (photoPath != null && !photoPath.isEmpty()){
            File photo = new File(photoPath);
            photo.delete();
        }
        restoreDefaultView();
    }

    @OnActivityResult(TAKE_PHOTO_CODE)
    public void onResult(int resultCode) {
        if (resultCode == Activity.RESULT_OK){
            type = Note.Type.PHOTO;
            displayToast(getString(R.string.photo_take_success));
            takePhotoButton.setVisibility(View.GONE);
        } else {
            File file = new File(photoPath);
            file.delete();
            photoPath = null;
        }
        show();
    }

    private Note createNote(){
        Note note = new Note();
        note.setType(type);
        note.setText(noteEditText.getText().toString());
        if (type == Note.Type.PHOTO)
            note.setResourcePath(photoPath);
        return note;
    }

    private void restoreDefaultView(){
        photoPath = null;
        takePhotoButton.setVisibility(View.VISIBLE);
        noteEditText.setText(null);
        noteEditText.clearFocus();
        type = Note.Type.TEXT;
        hide();
    }

    private String makeFileName(){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        String actionId = preferences.actionId().get();
        String fileName = String.format("%s-%s", actionId, strDate);
        return fileName;
    }

    private File createFile(String photoName) throws IOException {
        final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + PHOTO_FOLDER_NAME;
        File newDir = new File(dir);
        newDir.mkdirs();
        String file = dir + photoName + ".jpg";
        File newFile = new File(file);
        newFile.createNewFile();
        return newFile;
    }

    private void displayToast(final String text) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
            }
        });
    }


    public void show(){
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().show(this).commit();
    }

    public void hide(){
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().hide(this).commit();
    }
}
