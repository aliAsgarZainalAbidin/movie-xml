package com.example.movie_app_xml.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_app_xml.databinding.ItemChipBinding
import com.example.movie_app_xml.model.Genre

class GenreAdapter(
    private val listGenre : List<Genre>
) : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {
    class GenreViewHolder(private val binding : ItemChipBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data : Genre){
            binding.apply {
                mtvTitleChip.text = data.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = ItemChipBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return GenreViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(listGenre[position])
    }

    override fun getItemCount(): Int {
        return listGenre.size
    }
}