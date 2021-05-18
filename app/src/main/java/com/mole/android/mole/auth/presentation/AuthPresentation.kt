package com.mole.android.mole.auth.presentation

import com.mole.android.mole.auth.data.AuthData
import com.mole.android.mole.auth.model.AuthModel
import com.mole.android.mole.auth.view.AuthFragment

class AuthPresentation(private val model: AuthModel) {
    private var view: AuthFragment? = null

    fun attachView(view: AuthFragment?) {
        this.view = view
    }

    fun detachView() {
        view = null
    }

    fun viewIsReady() {
        val user = model.getUser()
        val login = user.login
        val prefix = if (login == "") "" else "@"
        view?.setUserLogin(prefix + login)
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
