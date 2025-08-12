package com.example.weatherimageapp.data.unsplash.remote

data class UnSplashApiResponse(val results: List<Result>)
data class UnSplashApiObj(val results: Result)
data class Result(
    val id: String, val urls: Urls
)
data class Urls(val small: String, val regular: String)
