package com.mole.android.mole.auth.model

import com.mole.android.mole.auth.data.AuthData
import java.util.*


class AuthModel {

    val data: AuthData = AuthData("vasiapupkin")

    fun addUser(user: AuthData): Boolean {
        return user.login != "first"
    }

    fun getUser(): AuthData {
        return data
    }

}
