package com.christianalexandre.mlchallengeandroid.data.model.itemcategory

import com.christianalexandre.mlchallengeandroid.domain.model.ItemCategory
import com.google.gson.annotations.SerializedName

data class ItemCategoryDTO(
    @SerializedName("path_from_root")
    val pathFromRoot: List<PathFromRootDTO>?
)

fun ItemCategoryDTO.toDomain() = ItemCategory(
    pathFromRoot = this.pathFromRoot?.mapNotNull { it.name }
)
