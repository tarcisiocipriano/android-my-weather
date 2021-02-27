package com.example.my_weather.data.remote.model

import com.google.gson.annotations.SerializedName

data class FindResult(
    @SerializedName("cod")
    var cod: Int,

    @SerializedName("message")
    var message: String,

    @SerializedName("list")
    var cities: List<City>
)

data class City(
    @SerializedName("id")
    var id: Long,

    @SerializedName("name")
    var name: String,

    @SerializedName("sys")
    var country: Country,

    @SerializedName("weather")
    var weathers: List<Weather>
)

data class Country(
    @SerializedName("country")
    var name: String
)

data class Weather(
    @SerializedName("main")
    var main: String,

    @SerializedName("description")
    var description: String,

    @SerializedName("icon")
    var icon: String
)