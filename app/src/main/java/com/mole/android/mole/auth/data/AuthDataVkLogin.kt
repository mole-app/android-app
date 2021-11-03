package com.mole.android.mole.auth.data

data class AuthDataVkLogin(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: String,
    val login: String
)
