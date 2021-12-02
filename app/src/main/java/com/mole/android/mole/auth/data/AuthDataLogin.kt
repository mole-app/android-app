package com.mole.android.mole.auth.data

data class AuthDataLogin(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: String,
    val login: String,
    val profileId: String
)
