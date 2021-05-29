package com.mole.android.mole.auth.view

interface AuthLoginView {
    fun showLoginExistError()

    fun hideError()

    fun setUserLogin(login: String)
}