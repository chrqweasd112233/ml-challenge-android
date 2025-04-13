package com.christianalexandre.mlchallengeandroid.data.model.itemdetail

import com.google.gson.annotations.SerializedName

data class ItemDescriptionDTO(
    @SerializedName("plain_text")
    val plainText: String
)
