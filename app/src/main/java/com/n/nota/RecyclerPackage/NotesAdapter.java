package com.n.nota.RecyclerPackage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.n.nota.databinding.CustomNotesItemBinding;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    private ArrayList<Notes> myNotes ;
    private OnNotesClickListener listener ;

    public NotesAdapter(ArrayList<Notes> myNotes , OnNotesClickListener listener) {
        this.myNotes = myNotes;
        this.listener = listener ;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CustomNotesItemBinding binding = CustomNotesItemBinding.inflate(LayoutInflater.from(parent.getContext()) , parent , false);
        return new NotesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Notes notes = myNotes.get(position) ;

        holder.binding.customNotesTitle.setText(notes.getTitle());
        holder.binding.customNotesDescription.setText(notes.getDescription());


    }

    @Override
    public int getItemCount() {
        return myNotes.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {
        CustomNotesItemBinding binding ;
        public NotesViewHolder(@NonNull CustomNotesItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView ;
            binding.container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickListener(getAdapterPosition());
                }
            });
        }
    }

}
