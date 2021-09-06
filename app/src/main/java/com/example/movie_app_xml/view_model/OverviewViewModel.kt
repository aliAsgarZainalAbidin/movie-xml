package com.example.movie_app_xml.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movie_app_xml.BuildConfig.TAG
import com.example.movie_app_xml.data.Repository
import com.example.movie_app_xml.data.entity.OnTheAir
import com.example.movie_app_xml.data.entity.People
import com.example.movie_app_xml.data.entity.Trending

class OverviewViewModel : ViewModel() {
    lateinit var repositor : Repository

    fun getPopularPeople():LiveData<List<People>>{
        repositor.requestAllPeople()
        val data = repositor.getAllPeople()
        Log.d(TAG, "getPopularPeople: ${data.value}")
        return data
    }

    fun getTrendingMovies():LiveData<List<Trending>>{
        repositor.requestTrendingMovie()
        return repositor.getTrending()
    }

    fun getOnTheAir():LiveData<List<OnTheAir>>{
        repositor.requestOnTheAir()
        return repositor.getOnTheAir()
    }
}