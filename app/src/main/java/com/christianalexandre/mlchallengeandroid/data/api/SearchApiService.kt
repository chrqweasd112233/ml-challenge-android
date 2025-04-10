package com.christianalexandre.mlchallengeandroid.data.api

import com.christianalexandre.mlchallengeandroid.data.model.SearchResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchApiService {
    @GET("search?q={query}")
    fun search(@Path("query") query: String): Response<SearchResponseDTO>
}