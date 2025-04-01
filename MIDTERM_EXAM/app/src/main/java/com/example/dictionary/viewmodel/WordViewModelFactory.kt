package com.example.dictionary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dictionary.data.WordRepository

class WordViewModelFactory(private val wordRepository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            return WordViewModel(wordRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
