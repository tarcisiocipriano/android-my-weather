package com.example.my_weather.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.my_weather.data.local.model.CitySearched
import com.example.my_weather.data.local.model.Favorite

@Database(entities = [Favorite::class, CitySearched::class], version = 1)
abstract class DatabaseApp: RoomDatabase() {

    abstract fun getFavoriteDao(): FavoriteDao

    abstract fun getCitySearchedDao(): CitySearchedDao

    companion object {
        private var instance: DatabaseApp? = null

        fun getInstance(context: Context): DatabaseApp {
            if (instance == null) {
                synchronized(DatabaseApp::class.java) {
                    // criar DB
                    instance = Room.databaseBuilder(
                        context,
                        DatabaseApp::class.java,
                        "Weather.db"
                    ).allowMainThreadQueries()
                        .build()
                }
            }
            return instance!!
        }
    }
}