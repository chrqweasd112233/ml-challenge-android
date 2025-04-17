package com.christianalexandre.data.model.itemdetail

import com.christianalexandre.data.model.search.AttributesDTO
import com.christianalexandre.data.model.search.ShippingDTO
import com.christianalexandre.data.model.search.toDictionary
import com.christianalexandre.domain.model.ItemDetail
import com.google.gson.annotations.SerializedName

data class ItemDetailDTO(
    val id: String?,
    @SerializedName("site_id")
    val siteId: String?,
    val title: String?,
    val price: Double?,
    @SerializedName("original_price")
    val originalPrice: Double?,
    val thumbnail: String?,
    @SerializedName("official_store_name")
    val officialStoreName: String?,
    val shipping: ShippingDTO?,
    val attributes: List<AttributesDTO>?,
    val pictures: List<PictureDTO>?,
    @SerializedName("sale_terms")
    val saleTerms: List<AttributesDTO>?,
)

fun ItemDetailDTO.toDomain() =
    ItemDetail(
        id = this.id,
        siteId = this.siteId,
        title = this.title,
        price = this.price,
        originalPrice = this.originalPrice,
        thumbnail = this.thumbnail,
        officialStoreName = this.officialStoreName,
        freeShipping = this.shipping?.freeShipping,
        attributes = this.attributes?.toDictionary(),
        pictures = this.pictures?.mapNotNull { it.url },
        saleTerms = this.saleTerms?.toDictionary(),
    )
