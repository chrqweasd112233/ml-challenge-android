package com.christianalexandre.data.api

import com.christianalexandre.data.model.itemcategory.ItemCategoryDTO
import com.christianalexandre.data.model.itemdescription.ItemDescriptionDTO
import com.christianalexandre.data.model.itemdetail.ItemDetailDTO
import com.christianalexandre.data.model.util.SearchResponseDTO
import com.christianalexandre.domain.model.ApiResponse

interface ItemApiService {
    suspend fun search(query: String): ApiResponse<SearchResponseDTO>

    suspend fun getItemDetail(itemId: String): ApiResponse<ItemDetailDTO>

    suspend fun getItemDescription(itemId: String): ApiResponse<ItemDescriptionDTO>

    suspend fun getItemCategory(itemId: String): ApiResponse<ItemCategoryDTO>
}
