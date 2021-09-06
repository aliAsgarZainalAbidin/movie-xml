package com.example.movie_app_xml.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movie_app_xml.data.Repository
import com.example.movie_app_xml.data.entity.MyMovie

class TabMovieViewModel : ViewModel() {
    lateinit var repository : Repository

    fun getAllMyMovies(): LiveData<List<MyMovie>>{
        repository.requestAllMyMovies()
        return repository.getAllMyMovies()
    }
}