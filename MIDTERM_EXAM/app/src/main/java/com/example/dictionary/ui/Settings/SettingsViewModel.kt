package com.example.dictionary.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    // Үгийн харуулах горим
    private val _setting = MutableStateFlow("both") // Default is "both"
    val setting: StateFlow<String> = _setting

    // Харуулах горимыг шинэчлэх
    fun setSetting(newSetting: String) {
        _setting.value = newSetting
    }
}
