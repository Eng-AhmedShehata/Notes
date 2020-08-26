package com.example.note.database;

import android.content.Context;
import android.provider.ContactsContract;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.note.dao.NoteDao;
import com.example.note.entities.DBNote;

@Database(entities = DBNote.class, version = 1, exportSchema = false)
public abstract class NotesDatabase extends RoomDatabase {
    private static NotesDatabase notesDatabase;

    public static synchronized NotesDatabase getDatabase(Context context) {
        if (notesDatabase == null) {
            notesDatabase = Room.databaseBuilder(context, NotesDatabase.class, "notes_db").build();
        }
        return notesDatabase;
    }
    public abstract NoteDao noteDao();
}
