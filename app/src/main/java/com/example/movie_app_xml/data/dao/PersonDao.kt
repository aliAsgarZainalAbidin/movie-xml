package com.example.movie_app_xml.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movie_app_xml.data.entity.People

@Dao
interface PersonDao {
    @Query(value = "SELECT * FROM people")
    fun getAllPerson():List<People>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<People>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPeople(people: People)
}