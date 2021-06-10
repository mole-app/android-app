package com.mole.android.mole.auth.model

import com.mole.android.mole.auth.data.AuthData

interface AuthModel {
    fun addUser(user: AuthData): Boolean

    fun getUser(): AuthData
}