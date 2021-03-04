package com.example.my_weather.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_favorites")
data class Favorite(

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long,

    @ColumnInfo(name = "city_name")
    var cityName: String,

    @ColumnInfo(name = "city_country")
    var cityCountry: String
)