package com.rickyslash.datastoreapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// 'custom implementation' of 'ViewModelFactory' to 'support non-default constructor' (SettingPreferences)
class ViewModelFactory(private val pref: SettingPreferences):
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}