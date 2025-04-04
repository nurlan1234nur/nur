package com.example.dictionary.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dictionary.model.Word

@Database(entities = [Word::class], version = 1)
abstract class WordDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var INSTANCE: WordDatabase? = null

        fun getDatabase(context: Context): WordDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordDatabase::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
} 