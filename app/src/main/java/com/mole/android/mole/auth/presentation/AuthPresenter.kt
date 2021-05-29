package com.mole.android.mole.auth.presentation

import android.util.Log
import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.auth.data.AuthData
import com.mole.android.mole.auth.model.AuthModel
import com.mole.android.mole.auth.view.AuthLoginView

class AuthPresenter(private val model: AuthModel) : MoleBasePresenter<AuthLoginView>() {

    private var login: String = ""

    override fun attachView(view: AuthLoginView) {
        this.view = view
        val user = model.getUser()
        val login = user.login
        val prefix = if (login == "") "" else "@"
        view.setUserLogin(prefix + login)
    }

    fun onFabClick() {
        Log.i("AuthPresenter", "Fab login = $login")
        if (model.addUser(AuthData(login))) {
            view?.hideError()

        } else {
            view?.showLoginExistError()
        }
    }

    fun onTextChanged(charSequence: CharSequence) {
        login = charSequence.toString()
        Log.i("AuthPresenter", "EditText login = $login")
        view?.hideError()
    }

}
