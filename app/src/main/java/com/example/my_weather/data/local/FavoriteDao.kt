package com.example.my_weather.data.local

import androidx.room.*
import com.example.my_weather.data.local.model.Favorite

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorite: Favorite)

    @Update
    fun update(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

    @Query("SELECT * FROM tb_favorites")
    fun getAll(): List<Favorite>

    @Query("SELECT * FROM tb_favorites WHERE (city_name LIKE '%' || :q || '%' OR city_country LIKE '%' || :q || '%')")
    fun getAllFiltered(q: String): List<Favorite>?

    @Query("SELECT * FROM tb_favorites WHERE id = :id")
    fun getById(id: Long): Favorite?

}