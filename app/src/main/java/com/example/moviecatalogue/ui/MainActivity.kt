package com.example.moviecatalogue.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.moviecatalogue.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.elevation = 0f

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.movie -> {
                    findNavController(R.id.frame_container).navigate(R.id.moviesFragment)
                    true
                }
                R.id.tv_show -> {
                    findNavController(R.id.frame_container).navigate(R.id.tvShowsFragment)
                    true
                }
                R.id.favorites -> {
                    findNavController(R.id.frame_container).navigate(
                        R.id.favoriteFragment
                    )
                    true
                }
                else -> false
            }
        }
    }

}