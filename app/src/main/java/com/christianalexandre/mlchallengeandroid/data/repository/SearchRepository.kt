package com.christianalexandre.mlchallengeandroid.data.repository

import com.christianalexandre.mlchallengeandroid.data.util.ApiResponse
import com.christianalexandre.mlchallengeandroid.domain.model.SearchItem

interface SearchRepository {
    suspend fun search(query: String): ApiResponse<List<SearchItem>?>
}