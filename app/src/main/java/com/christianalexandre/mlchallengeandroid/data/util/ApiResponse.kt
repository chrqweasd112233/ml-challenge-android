package com.christianalexandre.mlchallengeandroid.data.util

sealed class ApiResponse<T>(
    val data: T? = null,
    val error: Exception? = null
) {
    class Uninitialized<T> : ApiResponse<T>()
    class Loading<T>(data: T? = null) : ApiResponse<T>(data)
    class Success<T>(data: T) : ApiResponse<T>(data)
    class Error<T>(e: Exception?) : ApiResponse<T>(null, e)
}