package com.christianalexandre.mlchallengeandroid.data.model

import com.google.gson.annotations.SerializedName

data class AttributesDTO(
    @SerializedName("name")
    val key: String,
    @SerializedName("value_name")
    val value: String
)

fun List<AttributesDTO>.toDictionary(): Map<String, String> {
    val dictionary = mutableMapOf<String, String>()
    for (attribute in this) {
        val name = attribute.key
        val value = attribute.value
        dictionary[name] = value
    }
    return dictionary
}