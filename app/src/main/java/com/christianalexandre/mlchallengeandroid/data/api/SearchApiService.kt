package com.christianalexandre.mlchallengeandroid.data.api

import com.christianalexandre.mlchallengeandroid.data.model.SearchResponseDTO
import com.christianalexandre.mlchallengeandroid.data.util.ApiResponse

interface SearchApiService {
    suspend fun search(query: String): ApiResponse<SearchResponseDTO>
}