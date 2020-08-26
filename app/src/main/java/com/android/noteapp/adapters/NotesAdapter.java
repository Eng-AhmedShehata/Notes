package com.android.noteapp.adapters;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.noteapp.R;
import com.android.noteapp.entities.DBNote;
import com.android.noteapp.listeners.NotesListeners;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {
    private List<DBNote> dbNotes;
    private NotesListeners notesListeners;
    private Timer timer;
    private List<DBNote> dbNotesSource;

    public NotesAdapter(List<DBNote> dbNotes, NotesListeners notesListeners) {
        this.dbNotes = dbNotes;
        this.notesListeners = notesListeners;
        dbNotesSource = dbNotes;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_note, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, final int position) {
        holder.setNote(dbNotes.get(position));
        holder.layoutNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notesListeners.onNoteClicked(dbNotes.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dbNotes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textSubtitle, textDateTime;
        LinearLayout layoutNote;
        RoundedImageView imageNote;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textSubtitle = itemView.findViewById(R.id.textSubtitle);
            textDateTime = itemView.findViewById(R.id.textDateTime);
            layoutNote = itemView.findViewById(R.id.layoutNote);
            imageNote = itemView.findViewById(R.id.imageNote);

        }

        void setNote(DBNote dbNote) {
            textTitle.setText(dbNote.getTitle());
            if (dbNote.getSubtitle().trim().isEmpty()) {
                textSubtitle.setVisibility(View.GONE);
            } else {
                textSubtitle.setText(dbNote.getSubtitle());

            }
            textDateTime.setText(dbNote.getDateTime());
            GradientDrawable gradientDrawable = (GradientDrawable) layoutNote.getBackground();
            if (dbNote.getColor() != null) {
                gradientDrawable.setColor(Color.parseColor(dbNote.getColor()));
            } else {
                gradientDrawable.setColor(Color.parseColor("#333333"));
            }
            if (dbNote.getImagePath() != null) {
                imageNote.setImageBitmap(BitmapFactory.decodeFile(dbNote.getImagePath()));
                imageNote.setVisibility(View.VISIBLE);
            } else {
                imageNote.setVisibility(View.GONE);
            }
        }
    }

    public void searchNotes(final String searchKeyWord) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (searchKeyWord.trim().isEmpty()) {
                    dbNotes = dbNotesSource;
                } else {
                    ArrayList<DBNote> temp = new ArrayList<>();
                    for (DBNote dbNote : dbNotesSource) {
                        if (dbNote.getTitle().toLowerCase().contains(searchKeyWord.toLowerCase()) || dbNote.getSubtitle().toLowerCase().contains(searchKeyWord.toLowerCase())
                                || dbNote.getNoteText().toLowerCase().contains(searchKeyWord.toLowerCase())) {
                            temp.add(dbNote);
                        }

                    }
                    dbNotes = temp;
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        }, 500);
    }

    public void cancelTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }
}
