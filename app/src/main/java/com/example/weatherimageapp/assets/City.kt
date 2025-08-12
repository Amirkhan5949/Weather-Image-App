package com.example.weatherimageapp.assets

data class City(
    val name: String,
    val country: String,
    val latitude: Double,
    val longitude: Double,
    val imageUrl: String,
    val temperature: String
)