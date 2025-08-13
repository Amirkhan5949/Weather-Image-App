package com.example.weatherimageapp.data.unsplash.remote

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UnSplashApi {
    @GET("search/photos")
    suspend fun getUnSplashApi(
        @Query("query") query : String ,
        @Query("client_id") client_id : String,
        @Query("orientation") orientation : String = "portrait"
    ) : Response<UnSplashApiResponse>
}