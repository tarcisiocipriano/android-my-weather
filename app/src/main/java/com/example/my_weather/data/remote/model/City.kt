package com.example.my_weather.data.remote.model

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("id")
    var id: Long,

    @SerializedName("main")
    var main: Main,

    @SerializedName("name")
    var name: String,

    @SerializedName("sys")
    var country: Country,

    @SerializedName("weather")
    var weathers: List<Weather>,

    @SerializedName("wind")
    var wind: Wind,

    @SerializedName("clouds")
    var clouds: Clouds
)