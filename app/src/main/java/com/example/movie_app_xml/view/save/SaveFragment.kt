package com.example.movie_app_xml.view.save

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movie_app_xml.R
import com.example.movie_app_xml.databinding.FragmentSaveBinding
import com.example.movie_app_xml.view.adapter.SaveFragmentAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SaveFragment : Fragment() {

    private lateinit var _binding : FragmentSaveBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSaveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pager.adapter = SaveFragmentAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = when(position){
                0 -> "Movies"
                1 -> "Tv Shows"
                else -> ""
            }
        }.attach()
    }


}