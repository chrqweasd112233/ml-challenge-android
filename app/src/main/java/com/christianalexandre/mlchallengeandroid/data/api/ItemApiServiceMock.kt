package com.christianalexandre.mlchallengeandroid.data.api

import android.content.Context
import com.christianalexandre.mlchallengeandroid.data.model.itemdetail.ItemDescriptionDTO
import com.christianalexandre.mlchallengeandroid.data.model.itemdetail.ItemDetailDTO
import com.christianalexandre.mlchallengeandroid.data.util.ApiException
import com.christianalexandre.mlchallengeandroid.data.util.ApiResponse
import com.christianalexandre.mlchallengeandroid.data.util.SearchResponseDTO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemApiServiceMock @Inject constructor(
    @ApplicationContext private val context: Context
) : ItemApiService {
    // TODO: remove repetitive code
    override suspend fun search(query: String): ApiResponse<SearchResponseDTO> {
        val fileName = "search-MLA-$query.json"
        delay(1000)
        return try {
            val json = readJsonFromAssets(context, fileName)
            val result = Gson().fromJson(json, SearchResponseDTO::class.java)
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

    override suspend fun getItemDetail(itemId: String): ApiResponse<ItemDetailDTO> {
        val fileName = "item-$itemId.json"
        delay(3000)
        return try {
            val json = readJsonFromAssets(context, fileName)
            val result = Gson().fromJson(json, ItemDetailDTO::class.java)
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

    override suspend fun getItemDescription(itemId: String): ApiResponse<ItemDescriptionDTO> {
        val fileName = "item-$itemId-description.json"
        delay(1000)
        return try {
            val json = readJsonFromAssets(context, fileName)
            val result = Gson().fromJson(json, ItemDescriptionDTO::class.java)
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

private fun ItemApiServiceMock.readJsonFromAssets(context: Context, fileName: String): String {
    val inputStream = context.assets.open(fileName)
    return inputStream.bufferedReader().use { it.readText() }
}
