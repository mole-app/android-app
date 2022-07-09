package com.mole.android.mole.create.model

import com.google.gson.annotations.SerializedName

data class TagsPreviewRemote(
    @SerializedName("tags")
    val tags: List<TagPreviewRemote>
)

data class TagPreviewRemote(
    @SerializedName("debtCount")
    val debtCount: Int,
    @SerializedName("tag")
    val name: String
)

fun TagsPreviewRemote.asDomain(): List<TagPreview> = tags.map { tag ->
    TagPreview(
        name = tag.name,
        count = tag.debtCount
    )
}