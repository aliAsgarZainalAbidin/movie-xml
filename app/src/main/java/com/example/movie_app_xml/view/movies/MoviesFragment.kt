package com.example.movie_app_xml.view.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie_app_xml.R
import com.example.movie_app_xml.api.ApiFactory
import com.example.movie_app_xml.data.AppDatabase
import com.example.movie_app_xml.data.Repository
import com.example.movie_app_xml.data.entity.Playing
import com.example.movie_app_xml.data.entity.PopularMovies
import com.example.movie_app_xml.data.entity.Upcoming
import com.example.movie_app_xml.databinding.FragmentMoviesBinding
import com.example.movie_app_xml.view.adapter.NowPlayingAdapter
import com.example.movie_app_xml.view.adapter.PopularMoviesAdapter
import com.example.movie_app_xml.view.adapter.UpcomingAdapter
import com.example.movie_app_xml.view_model.MovieViewModel

class MoviesFragment : Fragment() {

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var _binding : FragmentMoviesBinding
    private val restApi by lazy { ApiFactory.create() }
    private val binding get() = _binding
    private var listPlaying : ArrayList<Playing> = arrayListOf()
    private var listUpComing : ArrayList<Upcoming> =arrayListOf()
    private var listPopularMovies : ArrayList<PopularMovies> =arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesBinding.inflate(inflater,container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieViewModel = MovieViewModel()
        movieViewModel.repository = Repository(restApi, AppDatabase.getDatabase(requireContext()))

        val navController = (activity as AppCompatActivity).findNavController(R.id.fcv_base_container)
        movieViewModel.getPlayingMovies().observe(viewLifecycleOwner, {
            listPlaying.addAll(it)
            val playingAdapter = NowPlayingAdapter(listPlaying,navController)
            val lm = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvMoviesPlaying.apply {
                adapter = playingAdapter
                layoutManager = lm
            }
            setLoading()
        })

        movieViewModel.getUpcomingMovies().observe(viewLifecycleOwner, {
            listUpComing.addAll(it)
            val adapterUpcoming = UpcomingAdapter(listUpComing,navController)
            val lm = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvMoviesUpcoming.apply {
                adapter = adapterUpcoming
                layoutManager = lm
            }
            setLoading()
        })

        movieViewModel.getPopularMovies().observe(viewLifecycleOwner, {
            listPopularMovies.addAll(it)
            val popularMoviesAdapter = PopularMoviesAdapter(listPopularMovies,navController)
            val lm = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvMoviesPopularMovies.apply {
                adapter = popularMoviesAdapter
                layoutManager = lm
            }
            setLoading()
        })

    }

    fun setLoading(){
        if (listPlaying.size > 0 && listUpComing.size > 0 && listPopularMovies.size > 0){
            binding.containerItemFmCl.visibility = View.VISIBLE
            binding.loading.root.visibility = View.GONE
        } else {
            binding.containerItemFmCl.visibility = View.GONE
            binding.loading.root.visibility = View.VISIBLE
        }
    }

}