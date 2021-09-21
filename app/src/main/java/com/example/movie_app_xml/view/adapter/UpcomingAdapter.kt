package com.example.movie_app_xml.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movie_app_xml.BuildConfig
import com.example.movie_app_xml.R
import com.example.movie_app_xml.data.entity.Upcoming
import com.example.movie_app_xml.databinding.ItemLandscapeBinding
import com.example.movie_app_xml.util.Const
import com.example.movie_app_xml.util.DetailConst

class UpcomingAdapter(
    private val listUpcoming : List<Upcoming>,
    private val navController: NavController
) : RecyclerView.Adapter<UpcomingAdapter.UpcomingViewHolder>() {

    class UpcomingViewHolder(private val binding: ItemLandscapeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data : Upcoming){
            with(binding){
                ivIlImage.load("${BuildConfig.BASE_IMAGE_URL}${data.backdropPath}"){
                    placeholder(R.color.grey)
                    crossfade(true)
                }
                val progress = data.voteAverage?.times(10)?.toInt() ?: 0
                mtvIlTitle.text = data.title
                mtvIlDate.text = data.releaseDate
                mtvIlProgress.text = progress.toString()
                cpiIlIndicator.progress = progress
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingViewHolder {
        val view = ItemLandscapeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UpcomingViewHolder(view)
    }

    override fun onBindViewHolder(holder: UpcomingViewHolder, position: Int) {
        holder.bind(listUpcoming[position])
        holder.itemView.setOnClickListener {
            val item = listUpcoming[position]
            val data = bundleOf(
                DetailConst.ID to item.id,
                Const.TYPE to Const.TYPE_MOVIE,
                Const.TYPE_REPO to Const.TYPE_REPO_REMOTE
            )
            navController.navigate(R.id.detailFragment, data)
        }
    }

    override fun getItemCount(): Int {
        return listUpcoming.size
    }
}