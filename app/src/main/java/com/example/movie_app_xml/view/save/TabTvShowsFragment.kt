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
import com.example.movie_app_xml.data.entity.MyTvShow
import com.example.movie_app_xml.databinding.FragmentTabTvShowsBinding
import com.example.movie_app_xml.view.adapter.TabTvShowsAdapter
import com.example.movie_app_xml.view_model.TabTvShowViewModel

class TabTvShowsFragment : Fragment() {

    private val restApi by lazy { ApiFactory.create() }
    private lateinit var _binding : FragmentTabTvShowsBinding
    private val binding get() = _binding
    private lateinit var tabTvShowViewModel : TabTvShowViewModel
    private var listTv : ArrayList<MyTvShow> =arrayListOf()

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

        val navController = (activity as AppCompatActivity).findNavController(R.id.fcv_base_container)
        tabTvShowViewModel.getAllTvShow().observe(viewLifecycleOwner, {
            listTv.clear()
            listTv.addAll(it)
            binding.rvFttsList.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                val adaptertvShow = TabTvShowsAdapter(listTv,navController)
                adaptertvShow.notifyDataSetChanged()
                adapter = adaptertvShow
            }
            setLoading()
        })
    }

    fun setLoading(){
        if (listTv.size > 0){
            binding.containerItemFttCl.visibility = View.VISIBLE
            binding.empty.root.visibility = View.GONE
        } else {
            binding.containerItemFttCl.visibility = View.GONE
            binding.empty.root.visibility = View.VISIBLE
        }
    }
}