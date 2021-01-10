package com.n.nota.Details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.n.nota.Common.Constants;
import com.n.nota.Home.MainActivity;
import com.n.nota.R;
import com.n.nota.RecyclerPackage.Notes;
import com.n.nota.databinding.ActivityNoteDetailsBinding;
import com.squareup.picasso.Picasso;


public class NoteDetails extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference myRef;
    public ActivityNoteDetailsBinding binding;
    private int noteId = -1;
    private String key, title, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoteDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.save.setOnClickListener(this);
        binding.delete.setOnClickListener(this);
        binding.edit.setOnClickListener(this);

        noteId = getIntent().getIntExtra(Constants.NOTE_ID, -1); // to know you want to show data from recycler or add new note
        key = getIntent().getStringExtra(Constants.NOTE_KEY);         // to differentiate between notes clicked in recyclerView
        title = getIntent().getStringExtra(Constants.TITLE_KEY);      // title of note which is clicked in recyclerView
        description = getIntent().getStringExtra(Constants.DESCRIPTION_KEY); // description of note which is clicked in recyclerView

        if (noteId == -1) {
            // عملية اضافة

        } else {
            // عملية عرض
            showNotePhase();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                if (noteId == -1) {
                    // Insert new note
                    addNote();
                    publishMainActivity(R.string.added_successfully);
                } else {
                    // Update note
                    updateNote(key, binding.title.getEditText().getText().toString(), binding.Description.getEditText().getText().toString());
                    publishMainActivity(R.string.edited_successfully);
                }
                break;
            case R.id.delete:
                deleteNote(key);
                publishMainActivity(R.string.note_deleted);
                break;
            case R.id.edit:
                editNotePhase();
                break;
        }
    }

    // start MainActivity
    private void publishMainActivity(int message) {
        startActivity(new Intent(getBaseContext(), MainActivity.class));
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }

    // when you click on edit button some buttons may be disappeared and fields will be enabled
    private void editNotePhase() {
        binding.edit.setVisibility(View.GONE);
        binding.delete.setVisibility(View.GONE);
        binding.save.setVisibility(View.VISIBLE);
        binding.title.getEditText().setEnabled(true);
        binding.Description.getEditText().setEnabled(true);
    }

    // when you click on any note in recyclerView some buttons may be disappeared and some buttons appeared and data will be disabled
    private void showNotePhase() {
        binding.titleInput.setText(title);
        binding.descriptionInput.setText(description);
        binding.save.setVisibility(View.GONE);
        binding.edit.setVisibility(View.VISIBLE);
        binding.delete.setVisibility(View.VISIBLE);
        binding.title.getEditText().setEnabled(false);
        binding.Description.getEditText().setEnabled(false);
    }


    // insert note into firebase
    public void addNote() {
        myRef = FirebaseDatabase.getInstance().getReference(Constants.NOTES_PATH_NAME);
        String noteKey = myRef.push().getKey();
        Notes notes = new Notes(noteKey, binding.title.getEditText().getText().toString(), binding.Description.getEditText().getText().toString());
        myRef.child(noteKey).setValue(notes);
    }

    // update note from firebase
    private void updateNote(String key, String title, String description) {
        myRef = FirebaseDatabase.getInstance().getReference(Constants.NOTES_PATH_NAME).child(key);
        Notes notes = new Notes(key, title, description);
        myRef.setValue(notes);
    }

    // Delete note from firebase
    private void deleteNote(String key) {
        myRef = FirebaseDatabase.getInstance().getReference(Constants.NOTES_PATH_NAME).child(key);
        myRef.removeValue();
    }


}