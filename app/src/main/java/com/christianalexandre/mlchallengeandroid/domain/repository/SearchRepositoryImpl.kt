package com.christianalexandre.mlchallengeandroid.domain.repository

import com.christianalexandre.mlchallengeandroid.data.api.SearchApiService
import com.christianalexandre.mlchallengeandroid.data.model.toDomain
import com.christianalexandre.mlchallengeandroid.data.repository.SearchRepository
import com.christianalexandre.mlchallengeandroid.data.util.ApiResponse
import com.christianalexandre.mlchallengeandroid.domain.model.SearchItem
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchApiService: SearchApiService
) : SearchRepository {
    override suspend fun search(query: String): ApiResponse<List<SearchItem>?> {
        return when (val result = searchApiService.search(query)) {
            is ApiResponse.Error -> ApiResponse.Error(result.error)
            is ApiResponse.Loading -> ApiResponse.Loading()
            is ApiResponse.Success -> {
                // TODO: handle optional
                val searchItems = result.data?.results?.map { it.toDomain() }
                ApiResponse.Success(searchItems)
            }
            is ApiResponse.Uninitialized -> ApiResponse.Uninitialized()
        }
    }
}