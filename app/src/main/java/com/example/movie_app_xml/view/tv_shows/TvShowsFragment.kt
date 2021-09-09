package com.example.movie_app_xml.view.tv_shows

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
import com.example.movie_app_xml.databinding.FragmentTvShowsBinding
import com.example.movie_app_xml.view.adapter.AiringTodayAdapter
import com.example.movie_app_xml.view.adapter.OnTheAirAdapter
import com.example.movie_app_xml.view.adapter.PopularTvShowsAdapter
import com.example.movie_app_xml.view_model.TvViewModel

class TvShowsFragment : Fragment() {

    private lateinit var _binding : FragmentTvShowsBinding
    private lateinit var tvViewModel: TvViewModel
    private val binding get() = _binding
    private val restApi by lazy { ApiFactory.create() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTvShowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvViewModel = TvViewModel()
        tvViewModel.repository = Repository(restApi, AppDatabase.getDatabase(requireContext()))

        val navController = (activity as AppCompatActivity).findNavController(R.id.fcv_base_container)
        tvViewModel.getOnTheAir().observe(viewLifecycleOwner, {
            val ontheairAdapter = OnTheAirAdapter(it,navController)
            binding.rvTvShowsOntheair.apply {
                adapter = ontheairAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        })

        tvViewModel.getAiringToday().observe(viewLifecycleOwner, {
            val airingTodayAdapter = AiringTodayAdapter(it,navController)
            binding.rvTvShowsAiring.apply {
                adapter = airingTodayAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        })

        tvViewModel.getPopularTv().observe(viewLifecycleOwner, {
            val popularTvShowsAdapter = PopularTvShowsAdapter(it,navController)
            binding.rvTvShowsPopularTvShows.apply {
                adapter = popularTvShowsAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
        })
    }

}