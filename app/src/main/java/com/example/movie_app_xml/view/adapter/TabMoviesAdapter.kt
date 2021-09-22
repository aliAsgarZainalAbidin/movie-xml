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
import com.example.movie_app_xml.data.entity.MyMovie
import com.example.movie_app_xml.databinding.ItemRowBinding
import com.example.movie_app_xml.util.Const
import com.example.movie_app_xml.util.DetailConst
import java.io.File

data class TabMoviesAdapter(
    private val listMovies: List<MyMovie>,
    private val navController: NavController,
    private val activity: Activity
) : RecyclerView.Adapter<TabMoviesAdapter.MoviesViewHolder>() {
    class MoviesViewHolder(private val binding: ItemRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MyMovie,activity: Activity) {
            with(binding){
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
        holder.bind(listMovies[position], activity)
        holder.itemView.setOnClickListener {
            val item = listMovies[position]
            val data = bundleOf(
                DetailConst.ID to item.id,
                Const.TYPE to Const.TYPE_MOVIE,
                Const.TYPE_REPO to listMovies[position].typeRepo
            )
            navController.navigate(R.id.detailFragment, data)
        }
    }

    override fun getItemCount(): Int {
        return listMovies.size
    }

}
