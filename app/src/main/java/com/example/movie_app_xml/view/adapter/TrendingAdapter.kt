package com.example.movie_app_xml.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movie_app_xml.BuildConfig
import com.example.movie_app_xml.R
import com.example.movie_app_xml.data.entity.Trending
import com.example.movie_app_xml.databinding.ItemPotraitBigBinding
import com.example.movie_app_xml.util.DetailConst

class TrendingAdapter(
    private val listItem: List<Trending>,
//    private val navController: NavController
) : RecyclerView.Adapter<TrendingAdapter.TrendingViewholder>() {

    class TrendingViewholder(private val binding: ItemPotraitBigBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Trending) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingViewholder {
        val view = ItemPotraitBigBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrendingViewholder(view)
    }

    override fun onBindViewHolder(holder: TrendingViewholder, position: Int) {
        holder.bind(listItem[position])
        holder.itemView.setOnClickListener {
            val movie = listItem[position]
            val data = bundleOf(
                DetailConst.TITLE to movie.title,
                DetailConst.BACKDROP_PATH to movie.backdropPath,
                DetailConst.DATE to movie.releaseDate,
                DetailConst.TITLE_DATE to "Release Date",
                DetailConst.POPULARITY to movie.popularity,
                DetailConst.ADULT to if (movie.adult == true) "YES" else "NO",
                DetailConst.LANGUAGE to movie.originalLanguage,
                DetailConst.GENRE to movie.genreIds,
                DetailConst.OVERVIEW to movie.overview
            )
//            navController.navigate(R.id.detailFragment, data)
        }
    }

    override fun getItemCount(): Int {
        return listItem.size
    }
}