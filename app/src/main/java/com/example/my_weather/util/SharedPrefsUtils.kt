package com.example.my_weather.util

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class SharedPrefsUtils {
    companion object {
        private const val UNIT_KEY = "pref_temp_key"
        private const val LANG_KEY = "pref_lang_key"
        private const val OFFLINE_MODE_KEY = "pref_offline_mode_key"
        private var currentTempUnitSearched: String = "metric"

        fun getUnitKey(context: Context): String {
            val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sp.getString(UNIT_KEY, "metric")!!
        }

        fun getLangKey(context: Context): String {
            val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sp.getString(LANG_KEY, "EN")!!
        }

        fun getOfflineMode(context: Context): Boolean {
            val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sp.getBoolean(OFFLINE_MODE_KEY, false)
        }

        fun updateTempUnitSearched(context: Context) {
            currentTempUnitSearched = getUnitKey(context)
        }

        fun getTempUnitSearched(): String {
            return when (currentTempUnitSearched) {
                "metric" -> "C°"
                "imperial" -> "F°"
                else -> "C°"
            }
        }

    }
}