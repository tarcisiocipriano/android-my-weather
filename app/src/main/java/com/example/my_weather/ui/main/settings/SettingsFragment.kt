package com.example.my_weather.ui.main.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.my_weather.R

class SettingsFragment: PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.pref_screen)
    }

}