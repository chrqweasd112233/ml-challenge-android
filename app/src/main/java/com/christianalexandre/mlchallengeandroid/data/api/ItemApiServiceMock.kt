package com.christianalexandre.mlchallengeandroid.data.api

import android.content.Context
import com.christianalexandre.mlchallengeandroid.data.model.itemcategory.ItemCategoryDTO
import com.christianalexandre.mlchallengeandroid.data.model.itemdescription.ItemDescriptionDTO
import com.christianalexandre.mlchallengeandroid.data.model.itemdetail.ItemDetailDTO
import com.christianalexandre.domain.model.ApiException
import com.christianalexandre.domain.model.ApiResponse
import com.christianalexandre.mlchallengeandroid.data.model.util.SearchResponseDTO
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemApiServiceMock @Inject constructor(
    @ApplicationContext private val context: Context
) : ItemApiService {
    override suspend fun search(query: String): ApiResponse<SearchResponseDTO> {
        val fileName = "search-MLA-$query.json"
        delay(1000)
        return parseJsonFromAssets(context, fileName)
    }

    override suspend fun getItemDetail(itemId: String): ApiResponse<ItemDetailDTO> {
        val fileName = "item-$itemId.json"
        delay(3000)
        return parseJsonFromAssets(context, fileName)
    }

    override suspend fun getItemDescription(itemId: String): ApiResponse<ItemDescriptionDTO> {
        val fileName = "item-$itemId-description.json"
        delay(1000)
        return parseJsonFromAssets(context, fileName)
    }

    override suspend fun getItemCategory(itemId: String): ApiResponse<ItemCategoryDTO> {
        val fileName = "item-$itemId-category.json"
        delay(1000)
        return parseJsonFromAssets(context, fileName)
    }

    private inline fun <reified T> parseJsonFromAssets(context: Context, fileName: String): ApiResponse<T> {
        return try {
            val inputStream = context.assets.open(fileName)
            val json = inputStream.bufferedReader().use { it.readText() }
            val result = Gson().fromJson(json, T::class.java)
            ApiResponse.Success(result)
        } catch (e: Exception) {
            ApiResponse.Error(
                ApiException(
                    code = 500,
                    message = e.localizedMessage ?: "Failure parsing json"
                )
            )
        }
    }

}
