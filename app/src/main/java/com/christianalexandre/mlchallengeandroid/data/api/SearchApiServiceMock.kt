package com.christianalexandre.mlchallengeandroid.data.api

import android.content.Context
import com.christianalexandre.mlchallengeandroid.data.model.SearchResponseDTO
import com.christianalexandre.mlchallengeandroid.data.util.ApiException
import com.christianalexandre.mlchallengeandroid.data.util.ApiResponse
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchApiServiceMock @Inject constructor(
    @ApplicationContext private val context: Context
) : SearchApiService {
    override suspend fun search(query: String): ApiResponse<SearchResponseDTO> {
        val fileName = "$query/search-MLA-$query.json"
        delay(500)
        return try {
            val json = readJsonFromAssets(fileName)
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

    private fun readJsonFromAssets(fileName: String): String {
        val inputStream = context.assets.open(fileName)
        return inputStream.bufferedReader().use { it.readText() }
    }
}
