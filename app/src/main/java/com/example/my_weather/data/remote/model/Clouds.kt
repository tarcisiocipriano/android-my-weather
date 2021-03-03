package com.example.my_weather.data.remote.model

import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    var percentage: Int
)