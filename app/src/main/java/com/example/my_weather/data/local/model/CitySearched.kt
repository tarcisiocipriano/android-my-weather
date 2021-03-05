package com.example.my_weather.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_city_searched")
data class CitySearched(

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "country")
    var country: String,

    @ColumnInfo(name = "temp")
    var temp: Double,

    @ColumnInfo(name = "weather")
    var weather: String,

    @ColumnInfo(name = "description")
    var description: String,

    @ColumnInfo(name = "icon")
    var icon: String,

    @ColumnInfo(name = "wind")
    var wind: Double,

    @ColumnInfo(name = "clouds")
    var clouds: Int

)

