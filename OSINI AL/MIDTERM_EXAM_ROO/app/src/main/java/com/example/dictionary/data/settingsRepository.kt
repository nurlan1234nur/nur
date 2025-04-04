package com.example.dictionary.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
private val DISPLAY_MODE_KEY = stringPreferencesKey("display_mode")

class SettingsRepository(private val context: Context) {
    val displayMode: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[DISPLAY_MODE_KEY] ?: "both"
        }

    suspend fun setDisplayMode(mode: String) {
        context.dataStore.edit { preferences ->
            preferences[DISPLAY_MODE_KEY] = mode
        }
    }
}