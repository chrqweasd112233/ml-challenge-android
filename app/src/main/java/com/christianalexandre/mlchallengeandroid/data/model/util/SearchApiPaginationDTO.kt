package com.christianalexandre.mlchallengeandroid.data.model.util

import com.google.gson.annotations.SerializedName

data class SearchApiPaginationDTO(
    val total: Int?,
    val off: Int?,
    val limit: Int?,
    @SerializedName("primary_results")
    val primaryResults: Int?
)
