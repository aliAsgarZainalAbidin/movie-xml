package com.example.movie_app_xml.view.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movie_app_xml.view.save.TabMoviesFragment
import com.example.movie_app_xml.view.save.TabTvShowsFragment

class SaveFragmentAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> {
                return TabMoviesFragment()
            }
            1 -> {
                return TabTvShowsFragment()
            }
            else -> return TabMoviesFragment()
        }
    }
}