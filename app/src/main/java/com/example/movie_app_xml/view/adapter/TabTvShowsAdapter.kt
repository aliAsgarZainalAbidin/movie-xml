package com.example.movie_app_xml.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movie_app_xml.R
import com.example.movie_app_xml.data.entity.MyTvShow
import com.example.movie_app_xml.databinding.ItemRowBinding
import com.example.movie_app_xml.util.Const
import com.example.movie_app_xml.util.DetailConst

data class TabTvShowsAdapter(
    private val listTvShows: List<MyTvShow>,
    private val navController: NavController
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
        holder.itemView.setOnClickListener {
            val item = listTvShows[position]
            val data = bundleOf(
                DetailConst.ID to item.id,
                Const.TYPE to Const.TYPE_TV,
                Const.TYPE_REPO to Const.TYPE_REPO_LOCAL
            )
            navController.navigate(R.id.detailFragment, data)
        }
    }

    override fun getItemCount(): Int {
        return listTvShows.size
    }

}
