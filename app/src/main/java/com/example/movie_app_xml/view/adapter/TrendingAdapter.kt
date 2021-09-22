package com.example.movie_app_xml.view.adapter

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
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
import com.example.movie_app_xml.util.Const
import com.example.movie_app_xml.util.DetailConst
import java.io.File

class TrendingAdapter(
    private val listItem: List<Trending>,
    private val navController: NavController,
    private val activity: Activity
) : RecyclerView.Adapter<TrendingAdapter.TrendingViewholder>() {

    class TrendingViewholder(private val binding: ItemPotraitBigBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Trending, activity: Activity) {
            with(binding){
                var bitmap : Bitmap? = null

                when(data.typeTrending){
                    Const.TYPE_REPO_REMOTE -> {
                        ivIpbImage.load("${BuildConfig.BASE_IMAGE_URL}${data.backdropPath}"){
                            placeholder(R.color.grey)
                            crossfade(true)
                        }
                    }
                    Const.TYPE_TRENDING_LOCAL, Const.TYPE_ONTHEAIR_LOCAL -> {
                        val imageUri = Uri.fromFile(File(data.backdropPath))
                        imageUri.let {
                            if (Build.VERSION.SDK_INT < 28) {
                                bitmap = it?.let { image ->
                                    MediaStore.Images
                                        .Media.getBitmap(activity.contentResolver, image)
                                }

                            } else {
                                val source = it?.let { image ->
                                    ImageDecoder
                                        .createSource(activity.contentResolver, image)
                                }
                                bitmap = source?.let { deco -> ImageDecoder.decodeBitmap(deco) }
                            }
                        }
                        bitmap.let {
                            binding.ivIpbImage.setImageBitmap(it)
                        }
                    }
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
        holder.bind(listItem[position],activity)
        holder.itemView.setOnClickListener {
            val movie = listItem[position]
            val data = bundleOf(
                DetailConst.ID to movie.id,
                Const.TYPE to Const.TYPE_MOVIE,
                Const.TYPE_REPO to listItem[position].typeTrending
            )
            navController.navigate(R.id.detailFragment, data)
        }
    }

    override fun getItemCount(): Int {
        return listItem.size
    }
}