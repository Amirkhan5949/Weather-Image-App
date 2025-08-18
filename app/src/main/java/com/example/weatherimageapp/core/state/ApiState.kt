package com.example.weatherimageapp.core.state

sealed interface ApiState {
    data object Loading : ApiState
    data object Success : ApiState
    data class Error(val error: String) : ApiState
}