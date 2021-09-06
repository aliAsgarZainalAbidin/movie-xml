package com.example.movie_app_xml.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movie_app_xml.data.Repository
import com.example.movie_app_xml.data.entity.Playing
import com.example.movie_app_xml.data.entity.PopularMovies
import com.example.movie_app_xml.data.entity.Upcoming

class MovieViewModel : ViewModel() {
    lateinit var repository: Repository

    fun getPlayingMovies():LiveData<List<Playing>>{
        repository.requestPlayingMovies()
        return repository.getPlayingMovies()
    }

    fun getUpcomingMovies(): LiveData<List<Upcoming>>{
        repository.requestUpcoming()
        return repository.getUpcomingMovies()
    }

    fun getPopularMovies():LiveData<List<PopularMovies>>{
        repository.requestPopularMovies()
        return repository.getPopularMovies()
    }
}