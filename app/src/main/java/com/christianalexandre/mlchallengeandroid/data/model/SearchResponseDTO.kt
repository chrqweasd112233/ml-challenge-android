package com.christianalexandre.mlchallengeandroid.data.model

import com.google.gson.annotations.SerializedName

data class SearchResponseDTO(
    @SerializedName("site_id")
    val siteId: String,
    val query: String?,
    val paging: SearchApiPaginationDTO,
    val results: List<SearchItemDTO>
)
