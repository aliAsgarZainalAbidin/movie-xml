package com.example.movie_app_xml.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movie_app_xml.data.entity.AiringToday
import com.example.movie_app_xml.data.entity.MyMovie
import com.example.movie_app_xml.data.entity.MyTvShow
import com.example.movie_app_xml.data.entity.PopularTv

@Dao
interface MyTvShowDao {
    @Query("SELECT * FROM MyTvShow")
    fun getAllTvShow(): List<MyTvShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<MyTvShow>)

    @Query("SELECT * FROM MyTvShow WHERE id=:id")
    fun getTvShowsById(id : String): MyTvShow

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data : MyTvShow)

    @Query("DELETE FROM MyTvShow WHERE id=:id")
    fun deleteTvShowById(id: String)
}