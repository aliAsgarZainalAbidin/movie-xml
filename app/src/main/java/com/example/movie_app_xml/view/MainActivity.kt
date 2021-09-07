package com.example.movie_app_xml.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.movie_app_xml.R
import com.example.movie_app_xml.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = supportFragmentManager.findFragmentById(R.id.fcv_main_container)?.findNavController() as NavController
        navController.popBackStack( navController.currentDestination?.id ?: R.id.overview, true)
        binding.botnavMainContainer.setOnNavigationItemSelectedListener {
            navController.popBackStack()
            when(it.itemId){
                R.id.overview -> {
                    navController.navigate(R.id.overviewFragment)
                    true
                }
                R.id.movies -> {
                    navController.navigate(R.id.moviesFragment)
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