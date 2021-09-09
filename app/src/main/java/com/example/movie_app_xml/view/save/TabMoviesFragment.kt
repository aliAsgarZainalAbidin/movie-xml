package com.example.movie_app_xml.view.save

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
import com.example.movie_app_xml.databinding.FragmentTabMoviesBinding
import com.example.movie_app_xml.databinding.FragmentTabTvShowsBinding
import com.example.movie_app_xml.view.adapter.TabMoviesAdapter
import com.example.movie_app_xml.view.adapter.TabTvShowsAdapter
import com.example.movie_app_xml.view_model.TabMovieViewModel
import com.example.movie_app_xml.view_model.TabTvShowViewModel

class TabMoviesFragment : Fragment() {

    private val restApi by lazy { ApiFactory.create() }
    private lateinit var _binding : FragmentTabMoviesBinding
    private val binding get() = _binding
    private lateinit var tabMovieViewModel: TabMovieViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabMoviesBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = Repository(restApi, AppDatabase.getDatabase(requireContext()))
        tabMovieViewModel = TabMovieViewModel()
        tabMovieViewModel.repository = repository

        val navController = (activity as AppCompatActivity).findNavController(R.id.fcv_base_container)
        tabMovieViewModel.getAllMyMovies().observe(viewLifecycleOwner, {
            binding.rvFtmList.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                val adaptertvShow = TabMoviesAdapter(it,navController)
                adaptertvShow.notifyDataSetChanged()
                adapter = adaptertvShow
            }
        })
    }

}