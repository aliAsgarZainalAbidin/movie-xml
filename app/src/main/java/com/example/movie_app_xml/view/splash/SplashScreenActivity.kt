package com.example.movie_app_xml.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.movie_app_xml.BuildConfig.TAG
import com.example.movie_app_xml.R
import com.example.movie_app_xml.view.BaseActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Log.d(TAG, "onCreate: Halo")
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, BaseActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            this.startActivity(intent)
            finish()
        },1500)
    }
}