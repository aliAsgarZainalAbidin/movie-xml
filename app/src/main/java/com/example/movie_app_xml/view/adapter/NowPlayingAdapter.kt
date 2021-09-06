package com.example.movie_app_xml.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movie_app_xml.BuildConfig
import com.example.movie_app_xml.R
import com.example.movie_app_xml.data.entity.Playing
import com.example.movie_app_xml.databinding.ItemPotraitBigBinding

class NowPlayingAdapter(
    private val listPlayingMovies : List<Playing>
) : RecyclerView.Adapter<NowPlayingAdapter.PlayingViewHolder>(){

    class PlayingViewHolder(private val binding: ItemPotraitBigBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data : Playing){
            with(binding){
                ivIpbImage.load("${BuildConfig.BASE_IMAGE_URL}${data.posterPath}") {
                    crossfade(true)
                    placeholder(R.color.grey)
                }
                val progress = data.voteAverage?.times(10)?.toInt() ?: 0
                mtvIpbTitle.text = data.title
                mtvIpbDate.text = data.releaseDate
                cpiIpbIndicator.progress = progress
                mtvIpbProgress.text = "$progress%"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayingViewHolder {
        val view = ItemPotraitBigBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayingViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayingViewHolder, position: Int) {
        holder.bind(listPlayingMovies[position])
    }

    override fun getItemCount(): Int {
        return listPlayingMovies.size
    }
}