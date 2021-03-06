package com.example.my_weather.data.remote.model

import com.google.gson.annotations.SerializedName

data class ForecastResult(
        @SerializedName("cod")
        var cod: Int,

        @SerializedName("message")
        var message: String,

        @SerializedName("list")
        var forecast: List<Forecast>
)
