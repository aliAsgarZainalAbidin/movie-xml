package com.example.movie_app_xml.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movie_app_xml.BuildConfig
import com.example.movie_app_xml.R
import com.example.movie_app_xml.data.entity.AiringToday
import com.example.movie_app_xml.data.entity.OnTheAir
import com.example.movie_app_xml.databinding.ItemLandscapeBinding

class AiringTodayAdapter(
    private val listOnTheAir : List<AiringToday>
) : RecyclerView.Adapter<AiringTodayAdapter.OnTheAirViewHolder>() {

    class OnTheAirViewHolder(private val binding: ItemLandscapeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data : AiringToday){
            with(binding){
                ivIlImage.load("${BuildConfig.BASE_IMAGE_URL}${data.backdropPath}"){
                    placeholder(R.color.grey)
                    crossfade(true)
                }
                val progress = data.voteAverage?.times(10)?.toInt() ?: 0
                mtvIlTitle.text = data.name
                mtvIlDate.text = data.firstAirDate
                mtvIlProgress.text = progress.toString()
                cpiIlIndicator.progress = progress
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnTheAirViewHolder {
        val view = ItemLandscapeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OnTheAirViewHolder(view)
    }

    override fun onBindViewHolder(holder: OnTheAirViewHolder, position: Int) {
        holder.bind(listOnTheAir[position])
    }

    override fun getItemCount(): Int {
        return listOnTheAir.size
    }
}