package com.example.weatherimageapp.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.weatherimageapp.core.state.ApiState
import com.example.weatherimageapp.core.state.Constaint
import com.example.weatherimageapp.data.CityWeatherWithImage
import com.example.weatherimageapp.databinding.WetherImageActivityBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherImageActivity : ComponentActivity() {
    private val weatherImageViewModel : WeatherImageViewModel by viewModels()
    private lateinit var binding: WetherImageActivityBinding
    private lateinit var adapter: WeatherImageAdapter
    private  var data : CityWeatherWithImage? = CityWeatherWithImage()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = WetherImageActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.viewPager.adapter = WeatherImageAdapter(loadCities(this))
        lifecycleScope.launch {
            weatherImageViewModel.loadCityData("Bhopal", Constaint.API_KEY_WEATHER, Constaint.API_KEY_UNSPLASH)
        }
        observeApi()
    }

     fun observeApi(){
         lifecycleScope.launch {
             weatherImageViewModel.apiState.collect {state->
                 when(state){
                     is ApiState.Error -> {}
                     ApiState.Loading -> {}
                     ApiState.Success -> {
                         data = weatherImageViewModel.weatherImageData
//                         adapter.updateList(data)
                     }
                 }
             }
         }

    }
    fun loadCities(context: Context): List<CityWeatherWithImage> {
        val jsonString = context.assets.open("cities.json")
            .bufferedReader()
            .use { it.readText() }
        val gson = Gson()
        val type = object : TypeToken<List<CityWeatherWithImage>>() {}.type
        return gson.fromJson(jsonString, type)
    }
}