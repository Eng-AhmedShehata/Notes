package com.example.note.listeners;

import com.example.note.entities.DBNote;

public interface NotesListeners {
    void onNoteClicked(DBNote dbNote,int position);
}
