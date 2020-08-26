package com.android.ahmedali.listeners;

import com.android.ahmedali.entities.DBNote;

public interface NotesListeners {
    void onNoteClicked(DBNote dbNote,int position);
}
