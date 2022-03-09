package com.mole.android.mole.profile.data

import android.net.Uri

data class ProfileUserInfo(
    val login: String,
    val name: String,
    val photoSmall: Uri,
    val photoNormal: Uri,
    val tags: List<String>,
    val totalSum: Long,
){
    constructor(profileUserInfoDomain: ProfileUserInfoDomain) : this(
        profileUserInfoDomain.profile.login,
        profileUserInfoDomain.profile.name,
        profileUserInfoDomain.photo.photoSmall,
        profileUserInfoDomain.photo.photoNormal,
        listOf("empty", "empty"),
        5000
    )
}
