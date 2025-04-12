package com.christianalexandre.mlchallengeandroid.data.api

import com.christianalexandre.mlchallengeandroid.data.model.SearchResponseDTO
import com.christianalexandre.mlchallengeandroid.data.util.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchApiService {
    @GET("search?q={query}")
    suspend fun search(@Path("query") query: String): ApiResponse<SearchResponseDTO>
}