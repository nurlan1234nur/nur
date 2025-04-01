package com.example.dictionary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.data.WordRepository
import com.example.dictionary.model.Word
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

class WordViewModel(private val wordRepository: WordRepository) : ViewModel() {

    private val _words = mutableStateOf<List<Word>>(emptyList())
    val words: State<List<Word>> = _words

    init {
        loadWords()
    }

    private fun loadWords() {
        viewModelScope.launch {
            _words.value = wordRepository.getAllWords()
        }
    }

    fun addWord(word: Word) {
        viewModelScope.launch {
            wordRepository.add(word)
            loadWords()
        }
    }

    fun deleteWord(word: Word) {
        viewModelScope.launch {
            wordRepository.deleteWord(word)
            loadWords() // Update the list after deletion
        }
    }

    fun updateWord(word: Word) {
        viewModelScope.launch {
            wordRepository.update(word)
            loadWords()
        }
    }

    fun getWordById(id: Int): Word? {
        return wordRepository.getWordById(id)
    }
}
