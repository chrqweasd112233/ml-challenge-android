package com.christianalexandre.mlchallengeandroid.data.repository

import com.christianalexandre.mlchallengeandroid.data.util.ApiResponse
import com.christianalexandre.mlchallengeandroid.domain.model.ItemDetail
import com.christianalexandre.mlchallengeandroid.domain.model.SearchItem

interface ItemRepository {
    suspend fun search(query: String): ApiResponse<List<SearchItem>?>
    suspend fun getItemDetail(itemId: String): ApiResponse<ItemDetail?>
    suspend fun getItemDescription(itemId: String): ApiResponse<String?>
}