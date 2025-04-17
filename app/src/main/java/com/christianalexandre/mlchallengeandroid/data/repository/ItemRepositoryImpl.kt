package com.christianalexandre.mlchallengeandroid.data.repository

import com.christianalexandre.mlchallengeandroid.data.api.ItemApiService
import com.christianalexandre.mlchallengeandroid.data.model.itemcategory.toDomain
import com.christianalexandre.mlchallengeandroid.data.model.itemdetail.toDomain
import com.christianalexandre.mlchallengeandroid.data.model.search.toDomain
import com.christianalexandre.domain.model.ApiResponse
import com.christianalexandre.domain.model.ItemCategory
import com.christianalexandre.domain.model.ItemDetail
import com.christianalexandre.domain.model.SearchItem
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val itemApiService: ItemApiService
) : com.christianalexandre.domain.repository.ItemRepository {
    override suspend fun search(query: String): ApiResponse<List<SearchItem>?> {
        return when (val result = itemApiService.search(query)) {
            is ApiResponse.Error -> ApiResponse.Error(result.error)
            is ApiResponse.Success -> ApiResponse.Success(
                result.data?.results?.map { it.toDomain() }
            )
        }
    }

    override suspend fun getItemDetail(itemId: String): ApiResponse<ItemDetail?> {
        return when(val result = itemApiService.getItemDetail(itemId)) {
            is ApiResponse.Error -> ApiResponse.Error(result.error)
            is ApiResponse.Success -> ApiResponse.Success(result.data?.toDomain())
        }
    }

    override suspend fun getItemDescription(itemId: String): ApiResponse<String?> {
        return when(val result = itemApiService.getItemDescription(itemId)) {
            is ApiResponse.Error -> ApiResponse.Error(result.error)
            is ApiResponse.Success -> ApiResponse.Success(result.data?.plainText)
        }
    }

    override suspend fun getItemCategory(itemId: String): ApiResponse<ItemCategory?> {
        return when(val result = itemApiService.getItemCategory(itemId)) {
            is ApiResponse.Error -> ApiResponse.Error(result.error)
            is ApiResponse.Success -> ApiResponse.Success(result.data?.toDomain())
        }
    }
}