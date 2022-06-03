package com.mole.android.mole.web.service

data class AuthTokenData(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: String
)
