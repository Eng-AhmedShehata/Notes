package com.android.noteapp.listeners;

import com.android.noteapp.entities.DBNote;

public interface NotesListeners {
    void onNoteClicked(DBNote dbNote,int position);
}
