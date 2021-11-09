package com.mole.android.mole.auth.model

interface AuthModel {
    suspend fun addUser(login: String): Boolean

    suspend fun getUserVk(code: String): String

    suspend fun getUserGoogle(code: String): String
}