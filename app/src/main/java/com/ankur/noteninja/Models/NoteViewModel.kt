package com.ankur.noteninja.Models

import androidx.lifecycle.ViewModel
import com.ankur.noteninja.Database.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(private val repository:NoteRepository ):ViewModel() {


    fun insert(note: Note)
    {
        CoroutineScope(Dispatchers.IO).launch {

            repository.insert(note)
        }
    }

    fun delete(note: Note)
    {

        CoroutineScope(Dispatchers.IO).launch {
            repository.delete(note)
        }
    }

    fun getAllNotes()=repository.getNotes

    fun updateNote(note: Note){
        CoroutineScope(Dispatchers.IO).launch {
            repository.update(note)
        }
    }




}