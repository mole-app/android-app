package com.mole.android.mole.auth.model

import com.mole.android.mole.auth.data.AuthDataVkLogin

interface AuthModel {
    suspend fun addUser(login: String): Boolean

    suspend fun getUserVk(code: String): AuthDataVkLogin?

    suspend fun getUserGoogle(code: String): String
}