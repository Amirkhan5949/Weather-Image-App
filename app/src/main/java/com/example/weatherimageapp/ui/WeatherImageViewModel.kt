package com.example.weatherimageapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherimageapp.core.state.ApiState
import com.example.weatherimageapp.core.state.Constaint
import com.example.weatherimageapp.data.CityWeatherWithImage
import com.example.weatherimageapp.data.WeatherImageRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class WeatherImageViewModel @Inject constructor(val repo: WeatherImageRepo) : ViewModel() {
    private val _apiState = MutableStateFlow<ApiState>(ApiState.Loading)
    val apiState : StateFlow<ApiState> = _apiState.asStateFlow()

    val weatherImageData = MutableStateFlow<CityWeatherWithImage?>(CityWeatherWithImage())

     fun loadCityData(city: String/*, weatherKey: String, unsplashKey: String*/){
        viewModelScope.launch {
            val result = repo.getCityWeatherWithImage(city = city, Constaint.API_KEY_WEATHER,
                Constaint.API_KEY_UNSPLASH)
            weatherImageData.value = result
            _apiState.value = ApiState.Success
        }
    }
}