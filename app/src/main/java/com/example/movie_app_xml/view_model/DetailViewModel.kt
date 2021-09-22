package com.example.movie_app_xml.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movie_app_xml.data.Repository
import com.example.movie_app_xml.data.entity.MyMovie
import com.example.movie_app_xml.data.entity.MyTvShow
import com.example.movie_app_xml.data.entity.OnTheAirLocal
import com.example.movie_app_xml.data.entity.TrendingLocal
import com.example.movie_app_xml.model.Detail

class DetailViewModel : ViewModel() {
    lateinit var repository: Repository

    fun getDetail(id : String, type : String):LiveData<Detail>{
        repository.requestDetail(id, type)
        return repository.getDetail()
    }

    fun getMovieById(id: String):LiveData<MyMovie>{
        repository.requestMovieById(id)
        return repository.getMovieById()
    }

    fun insertToMyMovies(myMovie: MyMovie){
        repository.insertToMyMovie(myMovie)
    }

    fun deleteMovieById(id: String){
        repository.deleteMovieById(id)
    }

    fun getTvShowById(id: String):LiveData<MyTvShow>{
        repository.requestTvShowById(id)
        return repository.getTvShowById()
    }

    fun insertToMyTvShow(tvShow: MyTvShow){
        repository.insertToTvShow(tvShow)
    }

    fun deleteTvShowById(id: String){
        repository.deleteTvShowById(id)
    }

    fun getLocalTrending(id: String):LiveData<TrendingLocal>{
        repository.requestLocalTrendingById(id)
        return repository.getDetailLocalTrending()
    }

    fun getLocalOnTheAir(id: String):LiveData<OnTheAirLocal>{
        repository.requestlocalOnTheAirById(id)
        return repository.getDetaillocalOnTheAir()
    }

    fun deletedLocalOnTheAirById(id : String){
        repository.deletelocalOnTheAirById(id)
    }

    fun deletedLocalTrendingById(id : String){
        repository.deleteLocalTrendingById(id)
    }

}