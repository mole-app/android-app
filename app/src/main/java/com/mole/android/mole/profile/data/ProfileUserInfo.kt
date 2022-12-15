package com.mole.android.mole.profile.data

data class ProfileUserInfo(
    val login: String,
    val name: String,
    val photoSmall: String,
    val photoNormal: String,
    val tags: List<String>,
    val totalSum: Long,
){
    constructor(profileUserInfoRemote: ProfileUserInfoRemote) : this(
        profileUserInfoRemote.profile.login,
        profileUserInfoRemote.profile.name,
        profileUserInfoRemote.photo.photoSmall,
        profileUserInfoRemote.photo.photoNormal,
        profileUserInfoRemote.tags,
        profileUserInfoRemote.debtSum,
    )
}
