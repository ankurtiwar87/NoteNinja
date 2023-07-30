package com.ankur.noteninja.Models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ankur.noteninja.Database.NoteRepository

class NoteViewModelFactory(private val repository: NoteRepository):ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(repository) as T
    }
}