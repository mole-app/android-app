package com.mole.android.mole.auth.presentation

import android.util.Log
import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.auth.data.AuthDataLogin
import com.mole.android.mole.auth.model.AuthModel
import com.mole.android.mole.auth.view.AuthLoginView
import kotlinx.coroutines.*

class AuthLoginPresenter(
    private val model: AuthModel,
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
                    when {
                        !isValidLogin(login) -> view.showInvalidLoginError()
                        login.isEmpty() -> view.showInvalidLoginError()
                        else -> {
                            model.addUser(login).withResult {
                                view.hideError()
                                view.openDebts()
                            }.withError {
                                view.showLoginExistError()
                            }
                        }
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

    private fun isValidLogin(login: String): Boolean {
        return true
    }

}
