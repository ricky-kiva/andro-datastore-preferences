package com.rickyslash.datastoreapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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