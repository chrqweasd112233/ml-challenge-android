package com.christianalexandre.data.model.itemdescription

import com.google.gson.annotations.SerializedName

data class ItemDescriptionDTO(
    @SerializedName("plain_text")
    val plainText: String?
)
