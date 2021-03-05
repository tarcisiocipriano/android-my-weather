package com.example.my_weather.data.local

import androidx.room.*
import com.example.my_weather.data.local.model.CitySearched

@Dao
interface CitySearchedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(citySearched: CitySearched)

    @Update
    fun update(citySearched: CitySearched)

    @Delete
    fun delete(citySearched: CitySearched)

    @Query("SELECT * FROM tb_city_searched")
    fun getAll(): List<CitySearched>

    @Query("SELECT * FROM tb_city_searched WHERE (name LIKE '%' || :q || '%')")
    fun getAllFiltered(q: String): List<CitySearched>?

    @Query("SELECT * FROM tb_city_searched WHERE id = :id")
    fun getById(id: Long): CitySearched?

}