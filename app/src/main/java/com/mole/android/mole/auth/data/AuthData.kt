package com.mole.android.mole.auth.data

data class AuthData(
    val login: String,
) {
    fun setLogin(login: String): AuthData {
        return this.copy(login = login)
    }

}
