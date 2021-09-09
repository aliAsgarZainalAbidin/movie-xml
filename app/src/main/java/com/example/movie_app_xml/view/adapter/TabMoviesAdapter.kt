package com.example.movie_app_xml.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movie_app_xml.BuildConfig
import com.example.movie_app_xml.R
import com.example.movie_app_xml.data.entity.MyMovie
import com.example.movie_app_xml.databinding.FragmentTabMoviesBinding
import com.example.movie_app_xml.databinding.ItemRowBinding
import com.example.movie_app_xml.util.Const
import com.example.movie_app_xml.util.DetailConst

data class TabMoviesAdapter(
    private val listMovies: List<MyMovie>,
    private val navController: NavController
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
        holder.itemView.setOnClickListener {
            val item = listMovies[position]
            val data = bundleOf(
                DetailConst.ID to item.id,
                Const.TYPE to Const.TYPE_MOVIE,
                Const.TYPE_REPO to Const.TYPE_REPO_LOCAL
            )
            navController.navigate(R.id.detailFragment, data)
        }
    }

    override fun getItemCount(): Int {
        return listMovies.size
    }

}
