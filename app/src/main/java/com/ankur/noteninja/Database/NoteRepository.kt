package com.ankur.noteninja.Database

import androidx.lifecycle.LiveData
import com.ankur.noteninja.Models.Note

class NoteRepository(private val noteDao: NoteDao){

    val getNotes:LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insert(note: Note){

        noteDao.insertNote(note)
    }


    suspend fun update(note: Note)
    {
        noteDao.update(note.id,note.title,note.note)
    }

    suspend fun delete(note: Note){

        noteDao.delete(note)
    }

}