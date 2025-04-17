package com.christianalexandre.mlchallengeandroid.data.api

import com.christianalexandre.mlchallengeandroid.data.model.itemcategory.ItemCategoryDTO
import com.christianalexandre.mlchallengeandroid.data.model.itemdescription.ItemDescriptionDTO
import com.christianalexandre.mlchallengeandroid.data.model.itemdetail.ItemDetailDTO
import com.christianalexandre.mlchallengeandroid.data.model.util.SearchResponseDTO
import com.christianalexandre.domain.model.ApiResponse

interface ItemApiService {
    suspend fun search(query: String): ApiResponse<SearchResponseDTO>
    suspend fun getItemDetail(itemId: String): ApiResponse<ItemDetailDTO>
    suspend fun getItemDescription(itemId: String): ApiResponse<ItemDescriptionDTO>
    suspend fun getItemCategory(itemId: String): ApiResponse<ItemCategoryDTO>
}