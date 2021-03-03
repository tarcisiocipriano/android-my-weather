package com.example.my_weather.data.remote.model

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat

data class Forecast(
    @SerializedName("id")
    var id: Int,

    @SerializedName("dt_txt")
    var dateTime: String,

    @SerializedName("main")
    var main: Main,

    @SerializedName("weather")
    var weathers: List<Weather>,

    @SerializedName("wind")
    var wind: Wind,

    @SerializedName("clouds")
    var clouds: Clouds
)