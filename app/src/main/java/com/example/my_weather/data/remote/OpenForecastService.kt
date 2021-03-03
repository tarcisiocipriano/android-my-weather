package com.example.my_weather.data.remote

import com.example.my_weather.data.remote.model.ForecastResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenForecastService {

    // data/2.5/forecast?id=3390760&units=metric&lang=PT&appid=989c2cec50552dd532208380980ea229
    // http://api.openweathermap.org

    @GET("/data/2.5/forecast")
    fun listForecasts(
        @Query("id")
        cityId: Long,

        @Query("units")
        units: String,

        @Query("lang")
        lang: String,

        @Query("appid")
        appId: String
    ): Call<ForecastResult>

}