package com.ankur.noteninja.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ankur.noteninja.Models.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note:Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("Select * From Note_Table order by id  ASC")
    fun getAllNotes():LiveData<List<Note>>

    @Query("UPDATE Note_Table Set title=:title ,note=:note  WHERE id=:id")
    suspend fun update(id:Int? ,title:String?,note:String?)


}