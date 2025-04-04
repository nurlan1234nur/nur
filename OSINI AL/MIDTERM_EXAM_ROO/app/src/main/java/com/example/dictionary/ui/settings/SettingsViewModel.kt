package com.example.dictionary.ui.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionary.data.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = SettingsRepository(application)
    private val _setting = MutableStateFlow("both")
    val setting: StateFlow<String> = _setting

    init {
        viewModelScope.launch {
            repository.displayMode.collectLatest { mode ->
                _setting.value = mode
            }
        }
    }

    fun setSetting(newSetting: String) {
        viewModelScope.launch {
            repository.setDisplayMode(newSetting)
        }
    }
}
