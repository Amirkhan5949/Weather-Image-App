package com.example.weatherimageapp.data

import com.example.weatherimageapp.data.unsplash.remote.UnSplashApi
import com.example.weatherimageapp.data.weather.remote.WeatherApi
import javax.inject.Inject

class WeatherImageRepo @Inject constructor(
    private val weatherApi: WeatherApi,
    private val unSplashApi: UnSplashApi,
) {
    suspend fun getCityWeatherWithImage(
        city: String,
        weatherKey: String,
        unSplashKey: String,
    ): CityWeatherWithImage? {
        val weatherRes = weatherApi.getWeatherByCity(city,weatherKey)

        if (!weatherRes.isSuccessful) return null
        val weather = weatherRes.body() ?: return null

        val unSplashRes = unSplashApi.getUnSplashApi(client_id = unSplashKey)
        if (!unSplashRes.isSuccessful) return null
        val photo = unSplashRes.body() ?: return null

        return CityWeatherWithImage(
            name = weather.name,
            temperature = weather.main.temp,
            latitude = weather.coord.lat,
            longitude = weather.coord.lon,
            imageUrl = photo.results.get(0).urls.small
        )
    }
}