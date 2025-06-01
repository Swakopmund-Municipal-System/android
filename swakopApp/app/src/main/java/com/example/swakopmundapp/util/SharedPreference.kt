package com.example.swakopmundapp.util

import android.content.Context
import android.content.SharedPreferences
import com.example.swakopmundapp.SwakopmundApplication

private const val PREFERENCE_FILE_KEY = "SWAKOPMUND_APP_PREFERENCE"

private val appSharedPreference: SharedPreferences = SwakopmundApplication.instance.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE)

fun clearSharedPreference() { appSharedPreference.edit().clear().apply() }

var PREF_TOKEN: String
    get() {
        return appSharedPreference.getString("TOKEN", "") ?: ""
    }
    set(value) {
        with(appSharedPreference.edit()) {
            putString("TOKEN", value)
            apply()
        }
    }
