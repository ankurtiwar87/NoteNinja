package com.ankur.noteninja.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ankur.noteninja.Models.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase:RoomDatabase() {

    abstract fun getNoteDao() :NoteDao

    companion object {
        private var INSTANCE: NoteDatabase? = null
        fun getDatabase(context: Context): NoteDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =
                        Room.databaseBuilder(context, NoteDatabase::class.java, "NoteDB.db").build()
                }
            }
            return INSTANCE!!
        }
    }

}