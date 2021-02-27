package com.example.my_weather.data.remote

import com.example.my_weather.data.remote.model.FindResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherService {

    // data/2.5/find?q=recife&units=metric&lang=PT&appid=989c2cec50552dd532208380980ea229
    // http://api.openweathermap.org

    @GET("/data/2.5/find")
    fun findCity(
        @Query("q")
        cityName: String,

        @Query("units")
        units: String,

        @Query("lang")
        lang: String,

        @Query("appid")
        appId: String
    ): Call<FindResult>

}