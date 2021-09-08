package com.example.movie_app_xml.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movie_app_xml.BuildConfig
import com.example.movie_app_xml.data.entity.MyMovie
import com.example.movie_app_xml.databinding.FragmentTabMoviesBinding
import com.example.movie_app_xml.databinding.ItemRowBinding

data class TabMoviesAdapter(
    private val listMovies: List<MyMovie>
) : RecyclerView.Adapter<TabMoviesAdapter.MoviesViewHolder>() {
    class MoviesViewHolder(private val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MyMovie) {
            with(binding){
                ivFtmImage.load("${data.posterPath}")
                mtvFtmTitle.text = data.title
                mtvFtmDate.text = data.releaseDate
                mtvFtmOverview.text = data.overview
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TabMoviesAdapter.MoviesViewHolder {
        val view = ItemRowBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return MoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: TabMoviesAdapter.MoviesViewHolder, position: Int) {
        holder.bind(listMovies[position])
    }

    override fun getItemCount(): Int {
        return listMovies.size
    }

}
