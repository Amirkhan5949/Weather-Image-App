package com.example.weatherimageapp.data

data class CityWeatherWithImage(
    val name: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val imageUrl: String = "",
    val temperature: Double = 0.0
)