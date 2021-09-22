package com.example.movie_app_xml.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.movie_app_xml.R
import com.example.movie_app_xml.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var _binding: FragmentMainBinding
    private val binding get() = _binding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController =
            childFragmentManager.findFragmentById(R.id.fcv_main_container)?.findNavController()
                ?: this.navController
        navController.popBackStack(navController.currentDestination?.id ?: R.id.home, true)
        binding.botnavMainContainer.setOnNavigationItemSelectedListener {
            navController.popBackStack()
            when (it.itemId) {
                R.id.home -> {
                    navController.navigate(R.id.overviewFragment)
                    true
                }
                R.id.movies -> {
                    navController.navigate(R.id.moviesFragment)
                    true
                }
                R.id.add -> {
                    navController.navigate(R.id.addFragment)
                    true
                }
                R.id.tvShows -> {
                    navController.navigate(R.id.tvShowsFragment)
                    true
                }
                R.id.save -> {
                    navController.navigate(R.id.saveFragment)
                    true
                }
                else -> true
            }
        }
    }

}