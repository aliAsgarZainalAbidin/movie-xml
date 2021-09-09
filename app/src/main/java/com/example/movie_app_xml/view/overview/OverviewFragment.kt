package com.example.movie_app_xml.view.overview

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie_app_xml.R
import com.example.movie_app_xml.api.ApiFactory
import com.example.movie_app_xml.data.AppDatabase
import com.example.movie_app_xml.data.Repository
import com.example.movie_app_xml.databinding.FragmentOverviewBinding
import com.example.movie_app_xml.view.adapter.OnTheAirAdapter
import com.example.movie_app_xml.view.adapter.PopularAdapter
import com.example.movie_app_xml.view.adapter.TrendingAdapter
import com.example.movie_app_xml.view_model.OverviewViewModel

class OverviewFragment : Fragment() {

    private lateinit var overviewViewModel : OverviewViewModel
    private lateinit var _binding : FragmentOverviewBinding
    private val restApi by lazy { ApiFactory.create() }
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        overviewViewModel = OverviewViewModel()
        overviewViewModel.repositor = Repository(restApi, AppDatabase.getDatabase(this.requireContext()))

        overviewViewModel.getPopularPeople().observe(viewLifecycleOwner,{
            val peopleAdapter = PopularAdapter(it)
            peopleAdapter.notifyDataSetChanged()
            val lm = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvOverviewPopular.apply {
                adapter = peopleAdapter
                layoutManager = lm
            }
        })

        val navController = (activity as AppCompatActivity).findNavController(R.id.fcv_base_container)
        overviewViewModel.getTrendingMovies().observe(viewLifecycleOwner, {
            val trendingAdapter = TrendingAdapter(it,navController)
            trendingAdapter.notifyDataSetChanged()
            val lm = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvOverviewTrending.apply {
                adapter = trendingAdapter
                layoutManager = lm
            }
        })

        overviewViewModel.getOnTheAir().observe(viewLifecycleOwner, {
            val onTheAirAdapter = OnTheAirAdapter(it,navController)
            onTheAirAdapter.notifyDataSetChanged()
            val lm = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvOverviewOntheair.apply {
                adapter = onTheAirAdapter
                layoutManager = lm
            }
        })
    }
}