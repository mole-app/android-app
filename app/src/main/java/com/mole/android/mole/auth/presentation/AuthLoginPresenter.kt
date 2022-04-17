package com.mole.android.mole.auth.presentation

import android.util.Log
import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.auth.data.AuthDataLogin
import com.mole.android.mole.auth.model.AuthModel
import com.mole.android.mole.auth.view.AuthLoginResources
import com.mole.android.mole.auth.view.AuthLoginView
import kotlinx.coroutines.*

class AuthLoginPresenter(
    private val model: AuthModel,
    private val authLoginResources: AuthLoginResources,
    private val client: AuthDataLogin
) :
    MoleBasePresenter<AuthLoginView>() {

    private var login: String = ""

    override fun attachView(view: AuthLoginView) {
        super.attachView(view)
        val login = client.login
        if (login != null && login != "") {
            view.setUserLogin(login)
        }
    }

    fun onFabClick() {
        withView { view ->
            withScope {
                launch {
                    Log.i("AuthPresenter", "Fab login = $login")
                    if (model.addUser(login)) {
                        view.hideError()
                        view.openDebts()
                    } else {
                        view.showLoginExistError()
                    }
                }
            }
        }
    }

    fun onTextChanged(charSequence: CharSequence) {
        login = charSequence.toString()
        Log.i("AuthPresenter", "EditText login = $login")
        withView { view ->
            view.hideError()
        }
    }

}
