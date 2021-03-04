package com.example.my_weather.ui.main.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CityRaw(
    val id: Long,
    val name: String,
    val country: String,
    val tempAmount: String,
    val tempUnit: String,
    val tempIcon: String
): Parcelable
