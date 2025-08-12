package com.example.weatherimageapp.di

import com.example.weatherimageapp.core.state.Constaint
import com.example.weatherimageapp.data.unsplash.remote.UnSplashApi
import com.example.weatherimageapp.data.weather.remote.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WeatherSplashModule {

    @Provides
    @Singleton
    @Named("Weather_Base_Url")
    fun provideWeatherBaseUrl() : String = Constaint.BASE_URL_WEATHER

    @Provides
    @Singleton
    @Named("UnSplash_Base_Url")
    fun provideUnSplashBaseUrl(): String = Constaint.BASE_URL_UNSPLASH

    @Provides
    @Singleton
    @Named("Retrofit_Weather")
    fun provideRetrofitWeather(@Named("Weather_Base_Url") url: String): Retrofit =
        Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build()

    @Provides
    @Singleton
    @Named("Retrofit_UnSplash")
    fun provideRetrofitUnsplash(@Named("UnSplash_Base_Url") url: String): Retrofit =
        Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build()

    @Singleton
    @Provides
    fun providesNetworkCallWeather(@Named("Retrofit_Weather") retrofit: Retrofit): WeatherApi =
        retrofit.create(WeatherApi::class.java)

    @Singleton
    @Provides
    fun providesNetworkCallUnSplash(@Named("Retrofit_UnSplash") retrofit: Retrofit): UnSplashApi =
        retrofit.create(UnSplashApi::class.java)

}