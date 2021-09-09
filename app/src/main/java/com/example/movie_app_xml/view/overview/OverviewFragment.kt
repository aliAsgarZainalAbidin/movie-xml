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
import com.example.movie_app_xml.data.entity.*
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
    private var listPeople : ArrayList<People> = arrayListOf()
    private var listTrending : ArrayList<Trending> =arrayListOf()
    private var listOnTheAir : ArrayList<OnTheAir> =arrayListOf()

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
            listPeople.addAll(it)
            val peopleAdapter = PopularAdapter(listPeople)
            peopleAdapter.notifyDataSetChanged()
            val lm = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvOverviewPopular.apply {
                adapter = peopleAdapter
                layoutManager = lm
            }
            setLoading()
        })

        val navController = (activity as AppCompatActivity).findNavController(R.id.fcv_base_container)
        overviewViewModel.getTrendingMovies().observe(viewLifecycleOwner, {
            listTrending.addAll(it)
            val trendingAdapter = TrendingAdapter(listTrending,navController)
            trendingAdapter.notifyDataSetChanged()
            val lm = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvOverviewTrending.apply {
                adapter = trendingAdapter
                layoutManager = lm
            }
            setLoading()
        })

        overviewViewModel.getOnTheAir().observe(viewLifecycleOwner, {
            listOnTheAir.addAll(it)
            val onTheAirAdapter = OnTheAirAdapter(listOnTheAir,navController)
            onTheAirAdapter.notifyDataSetChanged()
            val lm = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvOverviewOntheair.apply {
                adapter = onTheAirAdapter
                layoutManager = lm
            }
            setLoading()
        })
    }

    fun setLoading(){
        if (listPeople.size > 0 && listTrending.size > 0 && listOnTheAir.size > 0){
            binding.containerItemFoCl.visibility = View.VISIBLE
            binding.loading.root.visibility = View.GONE
        } else {
            binding.containerItemFoCl.visibility = View.GONE
            binding.loading.root.visibility = View.VISIBLE
        }
    }
}