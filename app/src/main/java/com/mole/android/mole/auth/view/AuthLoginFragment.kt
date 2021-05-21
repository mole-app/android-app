package com.mole.android.mole.auth.view

interface AuthLoginFragment {
    fun showError(error: String)

    fun hideError()

    fun getUserLogin(): String

    fun setUserLogin(login: String)
}