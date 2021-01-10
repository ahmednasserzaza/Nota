package com.n.nota.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.n.nota.Common.Constants;
import com.n.nota.Details.NoteDetails;
import com.n.nota.RecyclerPackage.Notes;
import com.n.nota.RecyclerPackage.NotesAdapter;
import com.n.nota.RecyclerPackage.OnNotesClickListener;
import com.n.nota.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NotesAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Notes> myNotes = new ArrayList<>();
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // another thread for long time operation
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                getNotes();
            }
        });
        thread.start();

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), NoteDetails.class);
                startActivity(intent);
                finish();
            }
        });

    }

    // method for populate notes in recyclerView
    private void populateDataInRecyclerView() {
        adapter = new NotesAdapter(myNotes, new OnNotesClickListener() {
            @Override
            public void onItemClickListener(int itemPosition) {
                Intent intent = new Intent(getBaseContext(), NoteDetails.class);
                intent.putExtra(Constants.NOTE_ID, itemPosition);
                intent.putExtra(Constants.NOTE_KEY, myNotes.get(itemPosition).getKey());
                intent.putExtra(Constants.TITLE_KEY, myNotes.get(itemPosition).getTitle());
                intent.putExtra(Constants.DESCRIPTION_KEY, myNotes.get(itemPosition).getDescription());
                startActivity(intent);
                finish();
            }
        });
        layoutManager = new GridLayoutManager(getBaseContext(), 2);
        binding.rv.setAdapter(adapter);
        binding.rv.setLayoutManager(layoutManager);
    }

    // retrieve notes from firebase
    private void getNotes() {
        myRef = FirebaseDatabase.getInstance().getReference(Constants.NOTES_PATH_NAME);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myNotes.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Notes note = ds.getValue(Notes.class);
                    myNotes.add(note);
                }
                populateDataInRecyclerView();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}