package com.christianalexandre.data.model.itemdetail

import com.google.gson.annotations.SerializedName

data class PictureDTO(
    val id: String?,
    val url: String?,
    @SerializedName("secure_url")
    val secureUrl: String?
)
