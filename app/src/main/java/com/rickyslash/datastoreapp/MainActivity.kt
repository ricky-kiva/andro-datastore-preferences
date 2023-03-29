package com.rickyslash.datastoreapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial

// this extends 'dataStore' from 'Context' then create an instance of 'DataStore<Preferences>' that is backed by 'preferencesDataStore()'
// `preferenceDataStore(name="settings")` will set the name of the dataStore
// 'Context.dataStore' is 'delegated' by 'preferencesDataStore()'. It also means, the setter & getter are configured by 'preferencesDataStore()'
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name="settings")

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        // making a "singleton' 'instance" of SettingPreferences
        val pref = SettingPreferences.getInstance(dataStore)
        // making new instance of ViewModel using 'custom ViewModelFactory'
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref))[MainViewModel::class.java]

        // observe 'isDarkModeActive' livedata (THEME_KEY in SettingPreference) from 'getThemeSettings()' and do things
        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                // AppCompatDelegate is a class that 'allows an app' to use 'new UI features' in 'later versions of Android'
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        // update the state of 'SettingPreference' data via 'MainViewModel' 'saveThemeSetting'
        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            mainViewModel.saveThemeSetting(isChecked)
        }

        /* Not using this again because we use SettingPreferences
        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                // AppCompatDelegate is a class that 'allows an app' to use 'new UI features' in 'later versions of Android'
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }*/

    }
}

// DataStore is a way of storing data by 'key-value' that is included in 'Jetpack Library'
// DataStore could store data along with 'Coroutine & Flow'. So data could be stored 'asynchronously', consistent, & transactional

// DataStore has 2 type:
// - Preferences DataStore: same as 'sharedPreferences' but 'don't need to define schema'. Thus, it 'doesn't support type-safety'
// - Proto DataStore: save data as 'instance' of particular data. Need to 'define schema' using 'protocol buffer' that 'supports type safety'

// Usage of the 2 types of DataStore:
// - Preferences DataStore: used to save simple 'key-values' (profile information, theme config, etc)
// - Proto DataStore: user for complex data and need a fast access

// Difference between 'SharedPreferences' & 'Preference DataStore' API:
// - 'handle data update' in 'transactional' way
// - uses 'Flow' to send 'data update status'
// - didn't have persistent data method like 'apply()' / 'commit()'
// - use API like 'Map' & 'MutableMap'