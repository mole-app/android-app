package com.mole.android.mole.profile.data

import com.google.gson.annotations.SerializedName

data class ProfilePhoto(
    @SerializedName("photoSmall")
    val photoSmall: String,
    @SerializedName("photoNormal")
    val photoNormal: String,
)
