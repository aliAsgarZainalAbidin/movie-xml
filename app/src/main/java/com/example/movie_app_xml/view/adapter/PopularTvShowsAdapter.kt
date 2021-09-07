package com.example.movie_app_xml.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movie_app_xml.BuildConfig
import com.example.movie_app_xml.R
import com.example.movie_app_xml.data.entity.Playing
import com.example.movie_app_xml.data.entity.PopularMovies
import com.example.movie_app_xml.data.entity.PopularTv
import com.example.movie_app_xml.databinding.ItemPortraitBinding
import com.example.movie_app_xml.databinding.ItemPotraitBigBinding

class PopularTvShowsAdapter(
    private val listPlayingMovies : List<PopularTv>
) : RecyclerView.Adapter<PopularTvShowsAdapter.PopularMoviesViewHolder>(){

    class PopularMoviesViewHolder(private val binding: ItemPortraitBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data : PopularTv){
            with(binding){
                ivIpImage.load("${BuildConfig.BASE_IMAGE_URL}${data.posterPath}") {
                    crossfade(true)
                    placeholder(R.color.grey)
                }
                val progress = data.voteAverage?.times(10)?.toInt() ?: 0
                mtvIpTitle.text = data.name
                mtvIpDate.text = data.firstAirDate
                cpiIpIndicator.progress = progress
                mtvIpProgress.text = "$progress%"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesViewHolder {
        val view = ItemPortraitBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularMoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: PopularMoviesViewHolder, position: Int) {
        holder.bind(listPlayingMovies[position])
    }

    override fun getItemCount(): Int {
        return listPlayingMovies.size
    }
}