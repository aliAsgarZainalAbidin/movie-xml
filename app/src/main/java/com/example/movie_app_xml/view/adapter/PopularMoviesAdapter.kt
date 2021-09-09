package com.example.movie_app_xml.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movie_app_xml.BuildConfig
import com.example.movie_app_xml.R
import com.example.movie_app_xml.data.entity.Playing
import com.example.movie_app_xml.data.entity.PopularMovies
import com.example.movie_app_xml.databinding.ItemPortraitBinding
import com.example.movie_app_xml.databinding.ItemPotraitBigBinding
import com.example.movie_app_xml.util.Const
import com.example.movie_app_xml.util.DetailConst

class PopularMoviesAdapter(
    private val listPlayingMovies : List<PopularMovies>,
    private val navController: NavController
) : RecyclerView.Adapter<PopularMoviesAdapter.PopularMoviesViewHolder>(){

    class PopularMoviesViewHolder(private val binding: ItemPortraitBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data : PopularMovies){
            with(binding){
                ivIpImage.load("${BuildConfig.BASE_IMAGE_URL}${data.posterPath}") {
                    crossfade(true)
                    placeholder(R.color.grey)
                }
                val progress = data.voteAverage?.times(10)?.toInt() ?: 0
                mtvIpTitle.text = data.title
                mtvIpDate.text = data.releaseDate
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
        holder.itemView.setOnClickListener {
            val item = listPlayingMovies[position]
            val data = bundleOf(
                DetailConst.ID to item.id,
                Const.TYPE to Const.TYPE_MOVIE,
                Const.TYPE_REPO to Const.TYPE_REPO_REMOTE
            )
            navController.navigate(R.id.detailFragment, data)
        }
    }

    override fun getItemCount(): Int {
        return listPlayingMovies.size
    }
}