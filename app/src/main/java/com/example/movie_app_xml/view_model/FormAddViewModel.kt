package com.example.movie_app_xml.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movie_app_xml.BuildConfig.TAG
import com.example.movie_app_xml.data.Repository
import com.example.movie_app_xml.data.entity.*

class FormAddViewModel : ViewModel() {
    lateinit var repositor : Repository

    fun insertTrendingMovie(data : TrendingLocal){
        repositor.addTrendingMovies(data)
    }

    fun insertOnTheAir(data : OnTheAirLocal){
        repositor.addOnTheAirTvShow(data)
    }
}