package com.example.movie_app_xml.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movie_app_xml.data.entity.PopularMovies
import com.example.movie_app_xml.data.entity.Upcoming

@Dao
interface PopularMoviesDao {
    @Query(value = "SELECT * FROM PopularMovies")
    fun getPopularMovies():List<PopularMovies>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<PopularMovies>)
}