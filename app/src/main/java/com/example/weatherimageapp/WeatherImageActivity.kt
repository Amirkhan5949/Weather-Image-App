package com.example.weatherimageapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.weatherimageapp.assets.City
import com.example.weatherimageapp.databinding.WetherImageActivityBinding
import com.example.weatherimageapp.ui.WeatherImageAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WeatherImageActivity : ComponentActivity() {
    private lateinit var binding: WetherImageActivityBinding
    private lateinit var adapter: WeatherImageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WetherImageActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewPager.adapter = WeatherImageAdapter(loadCities(this))
    }

    fun loadCities(context: Context): List<City> {
        val jsonString = context.assets.open("cities.json")
            .bufferedReader()
            .use { it.readText() }
        val gson = Gson()
        val type = object : TypeToken<List<City>>() {}.type
        return gson.fromJson(jsonString, type)
    }
}

