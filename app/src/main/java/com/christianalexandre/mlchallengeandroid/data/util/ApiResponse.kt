package com.christianalexandre.mlchallengeandroid.data.util

class ApiException(val code: Int, override val message: String) : Exception(message)

sealed class ApiResponse<T>(
    val data: T? = null,
    val error: ApiException? = null
) {
    class Success<T>(data: T) : ApiResponse<T>(data)
    class Error<T>(e: ApiException?) : ApiResponse<T>(null, e)
}