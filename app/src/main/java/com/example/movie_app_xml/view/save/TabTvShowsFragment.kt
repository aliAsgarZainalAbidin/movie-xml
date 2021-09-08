package com.example.movie_app_xml.view.save

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie_app_xml.R
import com.example.movie_app_xml.api.ApiFactory
import com.example.movie_app_xml.data.AppDatabase
import com.example.movie_app_xml.data.Repository
import com.example.movie_app_xml.databinding.FragmentTabTvShowsBinding
import com.example.movie_app_xml.view.adapter.TabTvShowsAdapter
import com.example.movie_app_xml.view_model.TabTvShowViewModel

class TabTvShowsFragment : Fragment() {

    private val restApi by lazy { ApiFactory.create() }
    private lateinit var _binding : FragmentTabTvShowsBinding
    private val binding get() = _binding
    private lateinit var tabTvShowViewModel : TabTvShowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTabTvShowsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = Repository(restApi, AppDatabase.getDatabase(requireContext()))
        tabTvShowViewModel = TabTvShowViewModel()
        tabTvShowViewModel.repository = repository

        tabTvShowViewModel.getAllTvShow().observe(viewLifecycleOwner, {
            binding.rvFttsList.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                val adaptertvShow = TabTvShowsAdapter(it)
                adaptertvShow.notifyDataSetChanged()
                adapter = adaptertvShow
            }
        })
    }
}