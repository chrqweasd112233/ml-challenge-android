package com.christianalexandre.domain.model

data class ItemDetail(
    val id: String?,
    val siteId: String?,
    val title: String?,
    val price: Double?,
    val originalPrice: Double?,
    val thumbnail: String?,
    val officialStoreName: String?,
    val freeShipping: Boolean?,
    val attributes: Map<String, String>?,
    val pictures: List<String>?,
    val saleTerms: Map<String, String>?
)