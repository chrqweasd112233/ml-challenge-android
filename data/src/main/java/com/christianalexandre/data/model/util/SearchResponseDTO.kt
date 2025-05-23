package com.christianalexandre.data.model.util

import com.christianalexandre.data.model.search.SearchItemDTO
import com.google.gson.annotations.SerializedName

data class SearchResponseDTO(
    @SerializedName("site_id")
    val siteId: String?,
    val query: String?,
    val paging: SearchApiPaginationDTO?,
    val results: List<SearchItemDTO>?
)
