package com.example.movie_app_xml.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movie_app_xml.data.entity.Trending

@Dao
interface TrendingDao {
    @Query(value = "SELECT * FROM Trending")
    fun getTrending():List<Trending>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Trending>)

    @Query("DELETE FROM Trending WHERE id=:id")
    fun deleteById(id: String)
}