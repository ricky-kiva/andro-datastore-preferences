package com.rickyslash.datastoreapp

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val THEME_KEY = booleanPreferencesKey("theme_setting")

    companion object {
        // @Volatile means a variable 'may be modified by multiple threads', and should 'always be read from main memory'
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        // this is a way of creating new instance, called 'singleton pattern'
        // 'singleton patterns' ensure that ONLY 1 INSTANCE CAN BE CREATED BY THIS CLASS (SettingPreferences)
        // the difference between 'singleton pattern' and just 'SettingPreferences' creating an instance is:
        // - 'singleton pattern' works best when the 'instance need to be shared across multiple parts of the code' / 'from different threads or coroutines'
        // - it 'prevent' 'race conditions'
        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            // `synchronized(this)` ensure that the code is executed 'atomically' (only 1 thread could execute at a time)
            return INSTANCE ?: synchronized(this) {
                // make an instance of 'SettingPreference'
                val instance = SettingPreferences(dataStore)
                // assigning the 'instance' to variable 'INSTANCE'
                INSTANCE = instance
                instance
            }
        }
    }

    // 'get' the 'value' of 'preference'
    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    // 'edit' the 'value' of 'preference'
    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }

}