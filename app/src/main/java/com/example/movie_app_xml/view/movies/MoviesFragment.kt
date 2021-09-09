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
            val playingAdapter = NowPlayingAdapter(it,navController)
            val lm = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvMoviesPlaying.apply {
                adapter = playingAdapter
                layoutManager = lm
            }
        })

        movieViewModel.getUpcomingMovies().observe(viewLifecycleOwner, {
            val adapterUpcoming = UpcomingAdapter(it,navController)
            val lm = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvMoviesUpcoming.apply {
                adapter = adapterUpcoming
                layoutManager = lm
            }
        })

        movieViewModel.getPopularMovies().observe(viewLifecycleOwner, {
            val popularMoviesAdapter = PopularMoviesAdapter(it,navController)
            val lm = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvMoviesPopularMovies.apply {
                adapter = popularMoviesAdapter
                layoutManager = lm
            }
        })


    }

}