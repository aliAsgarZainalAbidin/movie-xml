package com.example.movie_app_xml.view.detail

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.movie_app_xml.BuildConfig
import com.example.movie_app_xml.R
import com.example.movie_app_xml.api.ApiFactory
import com.example.movie_app_xml.data.AppDatabase
import com.example.movie_app_xml.data.Repository
import com.example.movie_app_xml.data.entity.MyMovie
import com.example.movie_app_xml.data.entity.MyTvShow
import com.example.movie_app_xml.databinding.FragmentDetailBinding
import com.example.movie_app_xml.model.Genre
import com.example.movie_app_xml.util.Const
import com.example.movie_app_xml.util.DetailConst
import com.example.movie_app_xml.view.adapter.GenreAdapter
import com.example.movie_app_xml.view_model.DetailViewModel

class DetailFragment : Fragment() {

    private lateinit var _binding: FragmentDetailBinding
    private val binding get() = _binding
    private lateinit var detailViewModel: DetailViewModel
    private val restApi by lazy { ApiFactory.create() }
    private var listGenre: List<Genre> = listOf()
    private var isSaved: Boolean = false
    private var backdropPath: String = ""
    private var posterPath: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt(DetailConst.ID)
        val type = arguments?.getString(Const.TYPE)
        val typeRepo = arguments?.getString(Const.TYPE_REPO)

        detailViewModel = DetailViewModel()
        detailViewModel.repository = Repository(restApi, AppDatabase.getDatabase(requireContext()))

        binding.apply {
            rvFdChipGroup.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            lavFdSave.setOnClickListener {
                if (isSaved) {
                    when (type) {
                        Const.TYPE_MOVIE -> detailViewModel.deleteMovieById(id.toString())
                        Const.TYPE_TV -> detailViewModel.deleteTvShowById(id.toString())
                    }
                    lavFdSave.progress = 0f
                    isSaved = false
                } else {
                    when (type) {
                        Const.TYPE_MOVIE -> {
                            val myMovie = MyMovie(
                                releaseDate = mtvFdDate.text.toString(),
                                isSaved = true,
                                adult = mtvFdAdult.text.toString().equals("YES", true),
                                backdropPath = backdropPath,
                                language = mtvFdLang.text.toString(),
                                id = id,
                                title = tvDetailTitle.text.toString(),
                                overview = mtvFdOverview.text.toString(),
                                popularity = mtvFdPopularity.text.toString().toDouble(),
                                type = type,
                                genreIds = listGenre,
                                posterPath = posterPath
                            )
                            detailViewModel.insertToMyMovies(myMovie)
                        }
                        Const.TYPE_TV -> {
                            val myTvShow = MyTvShow(
                                id = id,
                                type = type,
                                name = tvDetailTitle.text.toString(),
                                backdropPath = backdropPath,
                                firstAirDate = mtvFdDate.text.toString(),
                                popularity = mtvFdPopularity.text.toString().toDouble(),
                                language = mtvFdLang.text.toString(),
                                overview = mtvFdOverview.text.toString(),
                                genres = listGenre,
                                posterPath = posterPath,
                                isSaved = true
                            )
                            detailViewModel.insertToMyTvShow(myTvShow)
                        }
                    }
                    lavFdSave.progress = 1f
                    isSaved = true
                }
            }
        }

        val connectionManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectionManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)?.state == NetworkInfo.State.CONNECTED ||
            connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)?.state == NetworkInfo.State.CONNECTED && !typeRepo?.equals(Const.TYPE_REPO_LOCAL)!!
        ) {
            binding.containerItemFdCl.visibility = View.VISIBLE
            binding.offline.root.visibility = View.GONE
            if (id != null && type != null) {
                when (typeRepo) {
                    Const.TYPE_REPO_REMOTE -> {
                        detailViewModel.getDetail(id.toString(), type).observe(viewLifecycleOwner, {
                            binding.apply {
                                ivFdImage.load("${BuildConfig.BASE_IMAGE_URL}${it.backdrop_path}") {
                                    crossfade(true)
                                    placeholder(R.color.grey)
                                }
                                backdropPath = "${BuildConfig.BASE_IMAGE_URL}${it.backdrop_path}"
                                posterPath = "${BuildConfig.BASE_IMAGE_URL}${it.poster_path}"
                                when (type) {
                                    Const.TYPE_MOVIE -> {
                                        tvDetailTitle.text = it.title
                                        mtvFdTitleDate.text = "Release Date"
                                        mtvFdDate.text = it.release_date
                                        detailViewModel.getMovieById(id.toString())
                                            .observe(viewLifecycleOwner, {
                                                if (it != null) {
                                                    isSaved = it.isSaved ?: false
                                                    if (isSaved) {
                                                        lavFdSave.playAnimation()
                                                    }
                                                }
                                            })
                                    }
                                    Const.TYPE_TV -> {
                                        tvDetailTitle.text = it.name
                                        mtvFdTitleDate.text = "First Air Date"
                                        mtvFdDate.text = it.first_air_date
                                        detailViewModel.getTvShowById(id.toString())
                                            .observe(viewLifecycleOwner, {
                                                if (it != null) {
                                                    isSaved = it.isSaved ?: false
                                                    if (isSaved) {
                                                        lavFdSave.playAnimation()
                                                    }
                                                }
                                            })
                                    }
                                }
                                mtvFdPopularity.text = it.popularity.toString()
                                mtvFdAdult.text = if (it.adult == true) "YES" else "NO"
                                mtvFdLang.text = it.original_language
                                listGenre = it.genres ?: listOf()
                                rvFdChipGroup.adapter = GenreAdapter(listGenre)
                                rvFdChipGroup.adapter?.notifyDataSetChanged()
                                mtvFdOverview.text = it.overview
                            }
                        })
                    }
                    Const.TYPE_REPO_LOCAL -> {
                        when (type) {
                            Const.TYPE_MOVIE -> {
                                getLocalMovie(id.toString())
                            }
                            Const.TYPE_TV -> {
                                getLocalTvShow(id.toString())
                            }
                        }
                    }
                }
            }
//            IdleContent()
        } else {
            binding.containerItemFdCl.visibility = View.GONE
            binding.offline.root.visibility = View.VISIBLE
        }


    }

    fun getLocalMovie(id: String) {
        detailViewModel.getMovieById(id).observe(viewLifecycleOwner, {
            binding.apply {
                ivFdImage.load(it.backdropPath)
                tvDetailTitle.text = it.title
                mtvFdTitleDate.text = "Release Date"
                mtvFdDate.text = it.releaseDate
                mtvFdPopularity.text = it.popularity.toString()
                mtvFdAdult.text = if (it.adult == true) "YES" else "NO"
                mtvFdLang.text = it.originalLanguage
                listGenre = it.genreIds ?: listOf()
                rvFdChipGroup.adapter = GenreAdapter(listGenre)
                rvFdChipGroup.adapter?.notifyDataSetChanged()
                mtvFdOverview.text = it.overview
                isSaved = it.isSaved ?: false
                if (isSaved) {
                    lavFdSave.playAnimation()
                }
            }
        })
    }

    fun getLocalTvShow(id: String) {
        detailViewModel.getTvShowById(id).observe(viewLifecycleOwner, {
            binding.apply {
                ivFdImage.load(it.backdropPath)
                tvDetailTitle.text = it.name
                mtvFdTitleDate.text = "First Air Date"
                mtvFdDate.text = it.firstAirDate
                mtvFdPopularity.text = it.popularity.toString()
                mtvFdAdult.text = "-"
                mtvFdLang.text = it.language
                listGenre = it.genres
                rvFdChipGroup.adapter = GenreAdapter(listGenre)
                rvFdChipGroup.adapter?.notifyDataSetChanged()
                mtvFdOverview.text = it.overview
                isSaved = it.isSaved ?: false
                if (isSaved) {
                    lavFdSave.playAnimation()
                }
            }
        })
    }

}