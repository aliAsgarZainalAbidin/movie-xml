package com.example.movie_app_xml.view.adapter

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.movie_app_xml.BuildConfig
import com.example.movie_app_xml.R
import com.example.movie_app_xml.data.entity.OnTheAir
import com.example.movie_app_xml.databinding.ItemLandscapeBinding
import com.example.movie_app_xml.util.Const
import com.example.movie_app_xml.util.DetailConst
import java.io.File

class OnTheAirAdapter(
    private val listOnTheAir : List<OnTheAir>,
    private val navController: NavController,
    private val activity: Activity
) : RecyclerView.Adapter<OnTheAirAdapter.OnTheAirViewHolder>() {

    class OnTheAirViewHolder(private val binding: ItemLandscapeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data : OnTheAir,activity: Activity){
            with(binding){
                var bitmap : Bitmap? = null

                when(data.typeOnTheAir){
                    Const.TYPE_REPO_REMOTE -> {
                        ivIlImage.load("${BuildConfig.BASE_IMAGE_URL}${data.backdropPath}"){
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
                            binding.ivIlImage.setImageBitmap(it)
                        }
                    }
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
        holder.bind(listOnTheAir[position],activity)
        holder.itemView.setOnClickListener {
            val item = listOnTheAir[position]
            val data = bundleOf(
                DetailConst.ID to item.id,
                Const.TYPE to Const.TYPE_TV,
                Const.TYPE_REPO to listOnTheAir[position].typeOnTheAir
            )
            navController.navigate(R.id.detailFragment, data)
        }
    }

    override fun getItemCount(): Int {
        return listOnTheAir.size
    }
}