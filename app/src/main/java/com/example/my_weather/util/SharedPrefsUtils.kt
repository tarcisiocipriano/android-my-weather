package com.example.my_weather.util

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class SharedPrefsUtils {
    companion object {
        const val UNIT_KEY = "pref_temp_key"
        const val LANG_KEY = "pref_lang_key"
        private var currentTempUnitSearched: String = "metric"

        fun getUnitKey(context: Context,): String {
            val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sp.getString(UNIT_KEY, "metric")!!
        }

        fun getLangKey(context: Context): String {
            val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sp.getString(LANG_KEY, "EN")!!
        }

        fun updateTempUnitSearched(context: Context) {
            currentTempUnitSearched = getLangKey(context)
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