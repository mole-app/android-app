package com.mole.android.mole.profile.data

data class ProfileUserInfo(
    val login: String,
    val name: String,
    val photoSmall: String,
    val photoNormal: String,
    val tags: List<String>,
    val totalSum: Long,
){
    constructor(profileUserInfoDomain: ProfileUserInfoDomain) : this(
        profileUserInfoDomain.profile.login,
        profileUserInfoDomain.profile.name,
        profileUserInfoDomain.photo.photoSmall,
        profileUserInfoDomain.photo.photoNormal,
        profileUserInfoDomain.tags,
        profileUserInfoDomain.debtSum,
    )
}
