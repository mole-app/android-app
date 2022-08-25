package com.mole.android.mole.profile.data

import com.google.gson.annotations.SerializedName

data class ProfileUserInfoDomain(
    @SerializedName("profile")
    val profile: ProfileData,
    @SerializedName("photo")
    val photo: ProfilePhoto,
    @SerializedName("tags")
    val tags: List<String>,
    @SerializedName("debtSum")
    val debtSum: Long,
)
