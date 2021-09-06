package com.example.movie_app_xml.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movie_app_xml.data.Repository
import com.example.movie_app_xml.data.entity.MyTvShow

class TabTvShowViewModel : ViewModel() {
    lateinit var repository : Repository

    fun getAllTvShow(): LiveData<List<MyTvShow>>{
        repository.requestAllMyTvShow()
        return repository.getAllMyTvShow()
    }
}