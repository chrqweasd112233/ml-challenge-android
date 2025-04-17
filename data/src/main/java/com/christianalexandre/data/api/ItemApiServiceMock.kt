package com.christianalexandre.data.api

import com.christianalexandre.data.model.itemcategory.ItemCategoryDTO
import com.christianalexandre.data.model.itemdescription.ItemDescriptionDTO
import com.christianalexandre.data.model.itemdetail.ItemDetailDTO
import com.christianalexandre.data.model.util.SearchResponseDTO
import com.christianalexandre.domain.model.ApiException
import com.christianalexandre.domain.model.ApiResponse
import com.google.gson.Gson
import kotlinx.coroutines.delay

class ItemApiServiceMock : ItemApiService {
    override suspend fun search(query: String): ApiResponse<SearchResponseDTO> {
        val fileName = "search-MLA-$query.json"
        delay(1000)
        return parseJsonFromAssets(fileName)
    }

    override suspend fun getItemDetail(itemId: String): ApiResponse<ItemDetailDTO> {
        val fileName = "item-$itemId.json"
        delay(3000)
        return parseJsonFromAssets(fileName)
    }

    override suspend fun getItemDescription(itemId: String): ApiResponse<ItemDescriptionDTO> {
        val fileName = "item-$itemId-description.json"
        delay(1000)
        return parseJsonFromAssets(fileName)
    }

    override suspend fun getItemCategory(itemId: String): ApiResponse<ItemCategoryDTO> {
        val fileName = "item-$itemId-category.json"
        delay(1000)
        return parseJsonFromAssets(fileName)
    }

    private inline fun <reified T> parseJsonFromAssets(fileName: String): ApiResponse<T> =
        try {
            val inputStream = javaClass.classLoader.getResourceAsStream(fileName)
            val json = inputStream.bufferedReader().use { it.readText() }
            val result = Gson().fromJson(json, T::class.java)
            ApiResponse.Success(result)
        } catch (e: Exception) {
            ApiResponse.Error(
                ApiException(
                    code = 500,
                    message = e.localizedMessage ?: "Failure parsing json",
                ),
            )
        }
}
