package com.example.movie_app_xml.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movie_app_xml.BuildConfig
import com.example.movie_app_xml.R
import com.example.movie_app_xml.data.entity.People
import com.example.movie_app_xml.databinding.ItemPopularBinding

class PopularAdapter(
    private val listPopularPeople : List<People>
) : RecyclerView.Adapter<PopularAdapter.PopularViewHolder>() {

    class PopularViewHolder(private val binding: ItemPopularBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: People){
            binding.mtvIpName.text = data.name
            binding.civIpImage.load("${BuildConfig.BASE_IMAGE_URL}${data.profile_path}"){
                crossfade(true)
                placeholder(R.color.grey)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        val view = ItemPopularBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularViewHolder(view)
    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        holder.bind(listPopularPeople[position])
    }

    override fun getItemCount(): Int {
        return listPopularPeople.size
    }
}