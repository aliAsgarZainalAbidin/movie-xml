package com.example.movie_app_xml.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.movie_app_xml.R
import com.example.movie_app_xml.databinding.ActivityBaseBinding

class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}