package com.christianalexandre.mlchallengeandroid.domain.model

data class SearchItem(
    val id: String?,
    val siteId: String?,
    val categoryId: String?,
    val title: String?,
    val price: Double?,
    val originalPrice: Double?,
    val thumbnail: String?,
    val officialStoreName: String?,
    val freeShipping: Boolean?,
    val seller: Any?,
    val attributes: Map<String, String>?
)
