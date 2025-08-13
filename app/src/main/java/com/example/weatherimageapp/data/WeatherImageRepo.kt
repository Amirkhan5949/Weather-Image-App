package com.example.weatherimageapp.data

import com.example.weatherimageapp.data.unsplash.remote.UnSplashApi
import com.example.weatherimageapp.data.weather.remote.WeatherApi
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class WeatherImageRepo @Inject constructor(
    private val weatherApi: WeatherApi,
    private val unSplashApi: UnSplashApi,
) {
    fun getCityWeatherWithImage(
        city: String,
        weatherKey: String,
        unSplashKey: String
    ): Flow<Response<CityWeatherWithImage>> = flow {
        coroutineScope {
            val weatherDeferred = async { weatherApi.getWeatherByCity(city, weatherKey) }
            val photoDeferred = async { unSplashApi.getUnSplashApi(city, client_id = unSplashKey) }

            val weatherRes = weatherDeferred.await()
            val photoRes = photoDeferred.await()

            if (!weatherRes.isSuccessful) throw Exception("Weather API failed with code ${weatherRes.code()}")
            if (!photoRes.isSuccessful) throw Exception("Photo API failed with code ${photoRes.code()}")

            val weather = weatherRes.body() ?: throw Exception("Weather body is null")
            val photo = photoRes.body() ?: throw Exception("Photo body is null")

            emit(
                Response.success(
                    CityWeatherWithImage(
                        name = weather.name,
                        temperature = weather.main.temp,
                        latitude = weather.coord.lat,
                        longitude = weather.coord.lon,
                        imageUrl = photo.results[0].urls.regular
                    )
                )
            )
        }
    }
}


