package com.example.my_weather.ui.main.model

data class CitySearched(
    val id: Long,
    val name: String,
    val country: String,
    val temp: Double,
    val weather: String,
    val description: String,
    val icon: String,
    val wind: Double,
    val clouds: Int
)