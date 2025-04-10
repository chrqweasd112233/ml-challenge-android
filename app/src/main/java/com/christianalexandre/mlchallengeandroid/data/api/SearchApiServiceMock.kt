package com.christianalexandre.mlchallengeandroid.data.api

import android.content.Context
import com.christianalexandre.mlchallengeandroid.data.model.SearchResponseDTO
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchApiServiceMock @Inject constructor(
    @ApplicationContext private val context: Context
) : SearchApiService {
    override fun search(query: String): Response<SearchResponseDTO> {
        val fileName = "$query/search-MLA-$query.json"
        return try {
            val json = readJsonFromAssets(fileName)
            val result = Gson().fromJson(json, SearchResponseDTO::class.java)
            Response.success(result)
        } catch (e: Exception) {
            println("error")
            throw e
        }
    }

    private fun readJsonFromAssets(fileName: String): String {
        val inputStream = context.assets.open(fileName)
        return inputStream.bufferedReader().use { it.readText() }
    }
}
