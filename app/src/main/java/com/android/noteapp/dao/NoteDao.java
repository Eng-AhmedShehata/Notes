package com.android.noteapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.android.noteapp.entities.DBNote;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT*FROM notes ORDER BY id DESC")
    List<DBNote> getAllNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(DBNote dbNote);

    @Delete
    void deleteNote(DBNote dbNote);
}
