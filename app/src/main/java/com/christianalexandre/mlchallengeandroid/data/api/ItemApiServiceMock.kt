package com.christianalexandre.mlchallengeandroid.data.api

import android.content.Context
import com.christianalexandre.mlchallengeandroid.data.model.itemdetail.ItemDetailDTO
import com.christianalexandre.mlchallengeandroid.data.util.ApiException
import com.christianalexandre.mlchallengeandroid.data.util.ApiResponse
import com.christianalexandre.mlchallengeandroid.data.util.SearchResponseDTO
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
        delay(500)
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
        delay(500)
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
}

private fun ItemApiServiceMock.readJsonFromAssets(context: Context, fileName: String): String {
    val inputStream = context.assets.open(fileName)
    return inputStream.bufferedReader().use { it.readText() }
}
