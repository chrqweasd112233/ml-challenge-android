package com.christianalexandre.domain.repository

import com.christianalexandre.domain.model.ApiResponse
import com.christianalexandre.domain.model.ItemCategory
import com.christianalexandre.domain.model.ItemDetail
import com.christianalexandre.domain.model.SearchItem

interface ItemRepository {
    suspend fun search(query: String): ApiResponse<List<SearchItem>?>
    suspend fun getItemDetail(itemId: String): ApiResponse<ItemDetail?>
    suspend fun getItemDescription(itemId: String): ApiResponse<String?>
    suspend fun getItemCategory(itemId: String): ApiResponse<ItemCategory?>
}