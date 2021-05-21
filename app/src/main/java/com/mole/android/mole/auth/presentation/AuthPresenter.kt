package com.mole.android.mole.auth.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.auth.data.AuthData
import com.mole.android.mole.auth.model.AuthModel
import com.mole.android.mole.auth.view.AuthLoginFragment

class AuthPresenter(private val model: AuthModel): MoleBasePresenter<AuthLoginFragment>() {

    override fun attachView(view: AuthLoginFragment) {
        this.view = view
        val user = model.getUser()
        val login = user.login
        val prefix = if (login == "") "" else "@"
        view.setUserLogin(prefix + login)
    }

    fun onFabClick() {
        if (model.addUser(AuthData(view?.getUserLogin() ?: ""))) {
            view?.hideError()

        } else {
            view?.showError("Такой логин уже существует")
        }
    }

    fun textChanged() {
        view?.hideError()
    }

}
