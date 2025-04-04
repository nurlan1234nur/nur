package com.example.dictionary.ui.addedit

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AddEditWordViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddEditWordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddEditWordViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 