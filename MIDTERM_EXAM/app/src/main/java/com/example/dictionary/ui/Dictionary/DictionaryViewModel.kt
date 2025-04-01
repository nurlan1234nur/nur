package com.example.dictionary.ui.Dictionary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.data.WordRepository
import com.example.dictionary.model.Word
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

class DictionaryViewModel(private val wordRepository: WordRepository) : ViewModel() {

    // Үгсийн жагсаалт
    private val _words = mutableStateOf<List<Word>>(emptyList())
    val words: State<List<Word>> = _words

    init {
        loadWords()
    }

    // Үгсийг ачаалах
    private fun loadWords() {
        viewModelScope.launch {
            // Шаардлагатай хөрвүүлэлт одоо хэрэггүй болсон
            _words.value = wordRepository.getAllWords() // Шууд Word объектоор авах
        }
    }
}
