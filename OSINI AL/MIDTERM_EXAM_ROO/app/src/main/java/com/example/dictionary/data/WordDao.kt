package com.example.dictionary.data

import androidx.room.*
import com.example.dictionary.model.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Query("SELECT * FROM words ORDER BY englishWord ASC")
    fun getAllWords(): Flow<List<Word>>

    @Query("SELECT * FROM words ORDER BY englishWord ASC")
    suspend fun getAllWordsSync(): List<Word>

    @Query("SELECT * FROM words WHERE id = :id")
    suspend fun getWordById(id: Int): Word?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(word: Word)

    @Update
    suspend fun update(word: Word)

    @Delete
    suspend fun delete(word: Word)
} 