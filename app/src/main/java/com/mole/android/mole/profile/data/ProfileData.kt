package com.mole.android.mole.profile.data

import com.google.gson.annotations.SerializedName

data class ProfileData(
    @SerializedName("id")
    val id: Long,
    @SerializedName("login")
    val login: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("idVk")
    val vkId: String,
)
