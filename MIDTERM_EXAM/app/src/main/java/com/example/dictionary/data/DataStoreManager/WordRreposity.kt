package com.example.dictionary.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.dictionary.model.Word

class WordRepository(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    // 📌 Шинэ үг нэмэх
    fun add(word: Word) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("word", word.englishWord)
            put("meaning", word.mongolianMeaning)
        }
        db.insert("words", null, values)
        db.close()
    }
    fun deleteWord(word: Word) {
        val db = dbHelper.writableDatabase
        db.delete("words", "id = ?", arrayOf(word.id.toString()))
        db.close()
    }
    // 📌 Үгийг засах
    fun update(word: Word) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("word", word.englishWord)
            put("meaning", word.mongolianMeaning)
        }
        db.update("words", values, "id = ?", arrayOf(word.id.toString()))
        db.close()
    }

    fun getWordById(id: Int): Word? {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM words WHERE id = ?", arrayOf(id.toString()))

        return if (cursor.moveToFirst()) {
            val word = Word(
                id = cursor.getInt(0),
                englishWord = cursor.getString(1),
                mongolianMeaning = cursor.getString(2)
            )
            cursor.close()
            word
        } else {
            cursor.close()
            null
        }
    }


    // 📌 Бүх үгсийг авах
    fun getAllWords(): List<Word> {
        val wordsList = mutableListOf<Word>()
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT id, word, meaning FROM words", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val word = cursor.getString(1)
                val meaning = cursor.getString(2)
                wordsList.add(Word(id, word, meaning))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return wordsList
    }
}
