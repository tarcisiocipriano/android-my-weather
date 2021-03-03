package com.example.my_weather.util

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class SharedPrefsUtils {
    companion object {
        const val UNIT_KEY = "pref_temp_key"
        const val LANG_KEY = "pref_lang_key"
        private var currentTempUnitSearched: String = "metric"

        fun getUnitKey(context: Context, string: String): String {
            val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sp.getString(string, "metric")!!
        }

        fun getLangKey(context: Context, string: String): String {
            val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return sp.getString(string, "EN")!!
        }

        fun updateTempUnitSearched(context: Context) {
            currentTempUnitSearched = getLangKey(context, UNIT_KEY)
        }

        fun getTempUnitSearched(): String {
            return currentTempUnitSearched
        }
    }
}