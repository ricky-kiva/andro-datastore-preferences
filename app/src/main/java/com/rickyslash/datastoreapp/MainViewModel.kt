package com.rickyslash.datastoreapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(private val pref: SettingPreferences): ViewModel() {

    // call the 'getThemeSetting()' from 'SettingPreference' and typecast as 'LiveData'
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    // run 'saveThemeSetting()' from 'SettingPreference' in 'viewModelScope'
    // 'viewModelScope' allow to change state without 'observe'
    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}