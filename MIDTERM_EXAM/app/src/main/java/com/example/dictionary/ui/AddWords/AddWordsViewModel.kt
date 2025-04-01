package com.example.dictionary.ui.AddWords

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.data.WordRepository
import com.example.dictionary.model.Word
import kotlinx.coroutines.launch

class AddEditWordViewModel(private val wordRepository: WordRepository) : ViewModel() {

    // Үг нэмэх эсвэл засах
    fun addOrUpdateWord(word: Word) {
        viewModelScope.launch {
            if (word.id == 0) {
                wordRepository.add(word)  // Шинэ үг нэмэх
            } else {
                wordRepository.update(word)  // Үгийг засах
            }
        }
    }
}