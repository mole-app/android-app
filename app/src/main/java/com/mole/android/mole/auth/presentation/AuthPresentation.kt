package com.mole.android.mole.auth.presentation

import com.mole.android.mole.auth.data.AuthData
import com.mole.android.mole.auth.model.AuthModel
import com.mole.android.mole.auth.view.AuthLoginFragment

class AuthPresentation(private val model: AuthModel) {
    private var view: AuthLoginFragment? = null

    fun attachView(view: AuthLoginFragment) {
        this.view = view
        val user = model.getUser()
        val login = user.login
        val prefix = if (login == "") "" else "@"
        view.setUserLogin(prefix + login)
    }

    fun detachView() {
        view = null
    }

    fun nextFragment() {
        if (model.addUser(AuthData(view?.getUserLogin() ?: ""))) {
            view?.hideError()

        } else {
            view?.showError("Такой логин уже существует")
        }
    }

    fun textChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        view?.hideError()
    }

}
