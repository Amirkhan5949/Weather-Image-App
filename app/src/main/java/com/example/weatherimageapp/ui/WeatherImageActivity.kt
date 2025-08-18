package com.example.weatherimageapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherimageapp.databinding.WetherImageActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherImageActivity : AppCompatActivity() {
    private lateinit var binding: WetherImageActivityBinding
    
    private val cities = listOf(
        "Bhopal", "London", "New York", "Tokyo", "Paris", "Delhi", "Sydney", "Moscow", "Dubai",
        "Rome", "Berlin", "Los Angeles", "Chicago", "Madrid", "Toronto", "Bangkok", "Istanbul",
        "Singapore", "Hong Kong", "Cape Town"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WetherImageActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewPager.adapter = CityPagerAdapter(this, cities)
    }
}