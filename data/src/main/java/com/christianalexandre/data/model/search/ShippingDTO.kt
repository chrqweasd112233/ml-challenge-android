package com.christianalexandre.data.model.search

import com.google.gson.annotations.SerializedName

data class ShippingDTO(
    @SerializedName("free_shipping")
    val freeShipping: Boolean
)