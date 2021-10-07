package com.example.movie_app_xml.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movie_app_xml.data.entity.OnTheAirLocal
import com.example.movie_app_xml.data.entity.Trending
import com.example.movie_app_xml.data.entity.TrendingLocal

@Dao
interface OnTheAirLocalDao {
    @Query(value = "SELECT * FROM OnTheAirLocal ORDER BY id ASC")
    fun getOnTheAirLocal():List<OnTheAirLocal>

    @Query(value = "SELECT * FROM OnTheAirLocal WHERE id=:id")
    fun getOnTheAirLocalById(id : String):OnTheAirLocal

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<OnTheAirLocal>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data : OnTheAirLocal)

    @Query("DELETE FROM OnTheAirLocal WHERE id=:id")
    fun deleteById(id: String)

    @Query(value = "UPDATE OnTheAirLocal SET vote_average=:voteAverage WHERE id=:id")
    fun updateVoteAverage(voteAverage : Float, id: String)
}