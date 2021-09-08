package com.example.movie_app_xml.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movie_app_xml.BuildConfig
import com.example.movie_app_xml.data.entity.MyMovie
import com.example.movie_app_xml.data.entity.MyTvShow
import com.example.movie_app_xml.databinding.FragmentTabMoviesBinding
import com.example.movie_app_xml.databinding.ItemRowBinding

data class TabTvShowsAdapter(
    private val listTvShows: List<MyTvShow>
) : RecyclerView.Adapter<TabTvShowsAdapter.TvShowsViewHolder>() {
    class TvShowsViewHolder(private val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MyTvShow) {
            with(binding) {
                ivFtmImage.load("${data.posterPath}")
                mtvFtmTitle.text = data.name
                mtvFtmDate.text = data.firstAirDate
                mtvFtmOverview.text = data.overview
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TvShowsViewHolder {
        val view = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TvShowsViewHolder, position: Int) {
        holder.bind(listTvShows[position])
    }

    override fun getItemCount(): Int {
        return listTvShows.size
    }

}
