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
import com.example.movie_app_xml.data.entity.*
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
    private var listOnTheAir : ArrayList<OnTheAir> = arrayListOf()
    private var listAiringToday : ArrayList<AiringToday> =arrayListOf()
    private var listPopularTv : ArrayList<PopularTv> =arrayListOf()

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
            listOnTheAir.addAll(it)
            val ontheairAdapter = OnTheAirAdapter(listOnTheAir,navController,requireActivity())
            binding.rvTvShowsOntheair.apply {
                adapter = ontheairAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
            setLoading()
        })

        tvViewModel.getAiringToday().observe(viewLifecycleOwner, {
            listAiringToday.addAll(it)
            val airingTodayAdapter = AiringTodayAdapter(listAiringToday,navController)
            binding.rvTvShowsAiring.apply {
                adapter = airingTodayAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
            setLoading()
        })

        tvViewModel.getPopularTv().observe(viewLifecycleOwner, {
            listPopularTv.addAll(it)
            val popularTvShowsAdapter = PopularTvShowsAdapter(listPopularTv,navController)
            binding.rvTvShowsPopularTvShows.apply {
                adapter = popularTvShowsAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
            setLoading()
        })
    }

    fun setLoading(){
        if (listPopularTv.size > 0 && listAiringToday.size > 0 && listOnTheAir.size > 0){
            binding.containerItemFvsCl.visibility = View.VISIBLE
            binding.loading.root.visibility = View.GONE
        } else {
            binding.containerItemFvsCl.visibility = View.GONE
            binding.loading.root.visibility = View.VISIBLE
        }
    }

}