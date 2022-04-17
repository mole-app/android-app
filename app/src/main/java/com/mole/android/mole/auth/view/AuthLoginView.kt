package com.mole.android.mole.auth.view

import com.mole.android.mole.MoleBaseView

interface AuthLoginView : MoleBaseView {
    fun showLoginExistError()

    fun hideError()

    fun setUserLogin(login: String)

    fun openDebts()
}