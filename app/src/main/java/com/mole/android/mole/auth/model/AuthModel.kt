package com.mole.android.mole.auth.model

import com.mole.android.mole.auth.data.AuthData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


object AuthModel {

    private val data: AuthData = AuthData("vasiapupkin")

    fun addUser(user: AuthData): Boolean {
        return user.login != "first"
    }

    fun getUser(): AuthData {
        return data
    }

}
