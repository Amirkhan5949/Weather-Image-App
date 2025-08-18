package com.example.weatherimageapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githublink.core.state.Result
import com.example.githublink.core.state.asResult
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

    var weatherImageData = CityWeatherWithImage()

     fun loadCityData(city: String){
        viewModelScope.launch {
            repo.getCityWeatherWithImage(city = city, Constaint.API_KEY_WEATHER,
                Constaint.API_KEY_UNSPLASH).asResult().collect{result ->
                    when(result) {
                        Result.Loading -> _apiState.value = ApiState.Loading
                        is Result.Error -> _apiState.value = ApiState.Error(result.exception?.message.orEmpty())
                        is Result.Success<*> -> {
                            weatherImageData = result.data as CityWeatherWithImage
                            _apiState.value = ApiState.Success
                        }
                    }
            }

        }
    }
}