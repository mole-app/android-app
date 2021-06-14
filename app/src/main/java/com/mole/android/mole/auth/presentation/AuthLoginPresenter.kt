package com.mole.android.mole.auth.presentation

import android.util.Log
import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.auth.data.AuthDataVkLogin
import com.mole.android.mole.auth.model.AuthModel
import com.mole.android.mole.auth.model.AuthModelImplementation
import com.mole.android.mole.auth.view.AuthLoginResources
import com.mole.android.mole.auth.view.AuthLoginView
import kotlinx.coroutines.*

class AuthLoginPresenter(
    private val model: AuthModel,
    private val authLoginResources: AuthLoginResources,
    private val client: AuthDataVkLogin,
    private val scope: CoroutineScope
) :
    MoleBasePresenter<AuthLoginView>() {

    private var login: String = ""

    override fun attachView(view: AuthLoginView) {
        this.view = view
        val login = client.login
        if (login != "") {
            val prefix = authLoginResources.loginPrefix
            view.setUserLogin(prefix + login)
        }
    }

    fun onFabClick() {
        scope.launch {
            Log.i("AuthPresenter", "Fab login = $login")
            if (model.addUser(login)) {
                view?.hideError()

            } else {
                view?.showLoginExistError()
            }
        }
    }

    fun onTextChanged(charSequence: CharSequence) {
        login = charSequence.toString()
        Log.i("AuthPresenter", "EditText login = $login")
        view?.hideError()
    }

}
