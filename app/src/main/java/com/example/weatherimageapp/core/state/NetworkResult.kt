package com.example.githublink.core.state

import com.example.weatherimageapp.core.exception.WeatherImageException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import okhttp3.Headers
import retrofit2.Response

internal sealed interface Result<out T> {
    data class Success<T>(val data: T, val header: Headers?) : Result<T>
    data class Error(val exception: Throwable? = null) : Result<Nothing>
    data object Loading : Result<Nothing>
}

internal fun <T> Flow<Response<T>>.asResult(): Flow<Result<T>> {
    return this.map {
        mapApiResponse(it)
    }.map {
        processResponse(it)
    }.onStart { emit(Result.Loading) }.catch { emit(Result.Error(it)) }
}

@Throws(WeatherImageException::class)
internal fun <T> processResponse(response: Response<T>): Result<T> {
    val data = response.body() ?: throw WeatherImageException()
    return Result.Success(data, response.headers())
}

@Throws(WeatherImageException::class)
internal fun <T> mapApiResponse(response: Response<T>): Response<T> {
    if (response.isSuccessful) {
        return response
    } else {
        throw WeatherImageException("Failed to parse response to ErrorBody")
    }
}