package com.mole.android.mole.profile.data

data class ProfileEditUserInfo(
    val login: String,
    val name: String,
    val photoNormal: String,
)

fun ProfileUserInfo.toEditUserInfo() =
    ProfileEditUserInfo(
        login = login,
        name = name,
        photoNormal = photoNormal
    )
