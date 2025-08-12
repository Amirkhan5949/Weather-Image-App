package com.example.weatherimageapp.data.weather.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi  {
    @GET("weather")
    suspend fun getWeatherByCity(
        @Query("q") city : String,
        @Query("appid") apikey : String,
        @Query("units") unis : String = "metric"
    ) : Response<WeatherResponse>
}