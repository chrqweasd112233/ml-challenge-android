package com.christianalexandre.mlchallengeandroid.data.model

import com.christianalexandre.mlchallengeandroid.domain.model.SearchItem
import com.google.gson.annotations.SerializedName

data class SearchItemDTO(
    val id: String?,
    @SerializedName("site_id")
    val siteId: String?,
    @SerializedName("category_id")
    val categoryId: String?,
    val title: String?,
    val price: Double?,
    @SerializedName("original_price")
    val originalPrice: Double?,
    val thumbnail: String?,
    val seller: SellerDTO?,
    val attributes: List<AttributesDTO>?
)

fun SearchItemDTO.toDomain() = SearchItem(
    id = this.id,
    siteId = this.siteId,
    categoryId = this.categoryId,
    title = this.title,
    price = this.price,
    originalPrice = this.originalPrice,
    thumbnail = this.thumbnail,
    seller = this.seller,
    attributes = this.attributes?.toDictionary()
)