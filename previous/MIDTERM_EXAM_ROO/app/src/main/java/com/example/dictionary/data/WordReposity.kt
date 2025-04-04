package com.example.dictionary.data

import androidx.lifecycle.LiveData
import com.example.dictionary.model.Word
import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordDao: WordDao) {
    val allWords: Flow<List<Word>> = wordDao.getAllWords()

    suspend fun getWordById(id: Int): Word? = wordDao.getWordById(id)

    suspend fun insert(word: Word) = wordDao.insert(word)

    suspend fun update(word: Word) = wordDao.update(word)

    suspend fun delete(word: Word) = wordDao.delete(word)
}
