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
import com.example.movie_app_xml.R
import com.example.movie_app_xml.data.entity.MyTvShow
import com.example.movie_app_xml.databinding.ItemRowBinding
import com.example.movie_app_xml.util.Const
import com.example.movie_app_xml.util.DetailConst
import java.io.File

data class TabTvShowsAdapter(
    private val listTvShows: List<MyTvShow>,
    private val navController: NavController,
    private val activity: Activity
) : RecyclerView.Adapter<TabTvShowsAdapter.TvShowsViewHolder>() {
    class TvShowsViewHolder(private val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MyTvShow, activity: Activity) {
            with(binding) {
                var bitmap : Bitmap? = null
                when(data.typeRepo){
                    Const.TYPE_REPO_REMOTE -> {
                        ivFtmImage.load("${data.posterPath}"){
                            placeholder(R.color.grey)
                            crossfade(true)
                        }
                    }
                    Const.TYPE_TRENDING_LOCAL, Const.TYPE_ONTHEAIR_LOCAL -> {
                        val imageUri = Uri.fromFile(File(data.posterPath))
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
                            binding.ivFtmImage.setImageBitmap(it)
                        }
                    }
                }
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
        holder.bind(listTvShows[position], activity)
        holder.itemView.setOnClickListener {
            val item = listTvShows[position]
            val data = bundleOf(
                DetailConst.ID to item.id,
                Const.TYPE to Const.TYPE_TV,
                Const.TYPE_REPO to listTvShows[position].typeRepo
            )
            navController.navigate(R.id.detailFragment, data)
        }
    }

    override fun getItemCount(): Int {
        return listTvShows.size
    }

}
