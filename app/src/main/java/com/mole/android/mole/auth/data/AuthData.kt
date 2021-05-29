package com.mole.android.mole.auth.data

class AuthData(
    val login: String,
) {

    fun copy(
        login: String = this.login
    ) = AuthData(login)

    fun setLogin(login: String): AuthData {
        return this.copy(login = login)
    }

}
