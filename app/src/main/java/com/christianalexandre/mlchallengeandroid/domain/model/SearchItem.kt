package com.christianalexandre.mlchallengeandroid.domain.model

import com.christianalexandre.mlchallengeandroid.data.model.SellerDTO

data class SearchItem(
    val id: String?,
    val siteId: String?,
    val categoryId: String?,
    val title: String?,
    val price: Double?,
    val originalPrice: Double?,
    val thumbnail: String?,
    val seller: SellerDTO?,
    val attributes: Map<String, String>?
)
