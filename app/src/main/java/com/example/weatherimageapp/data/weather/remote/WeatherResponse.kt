package com.example.weatherimageapp.data.weather.remote

data class WeatherResponse(
    val coord : Coord,
    val weather : List<Weather>,
    val main : Main,
    val wind : Wind,
    val name : String
)

data class Coord(
    val lon : Double,
    val lat : Double
)

data class Weather(
    val main : String,
    val description : String,
    val icon : String
)

data class Main(
    val temp : Double,
    val humidity : Double
)

data class Wind(
    val speed : Double
)