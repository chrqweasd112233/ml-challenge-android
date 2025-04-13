package com.christianalexandre.mlchallengeandroid.domain.repository

import com.christianalexandre.mlchallengeandroid.data.api.ItemApiService
import com.christianalexandre.mlchallengeandroid.data.model.itemdetail.toDomain
import com.christianalexandre.mlchallengeandroid.data.model.search.toDomain
import com.christianalexandre.mlchallengeandroid.data.repository.ItemRepository
import com.christianalexandre.mlchallengeandroid.data.util.ApiResponse
import com.christianalexandre.mlchallengeandroid.domain.model.SearchItem
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val itemApiService: ItemApiService
) : ItemRepository {
    override suspend fun search(query: String): ApiResponse<List<SearchItem>?> {
        return when (val result = itemApiService.search(query)) {
            is ApiResponse.Error -> ApiResponse.Error(result.error)
            is ApiResponse.Success -> ApiResponse.Success(
                result.data?.results?.map { it.toDomain() }
            )
        }
    }

    override suspend fun getItemDetail(itemId: String) {
        val result = itemApiService.getItemDetail(itemId)

        val teste = result.data?.toDomain()

        println(teste)
    }
}