package com.mole.android.mole.auth.presentation

import android.util.Log
import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.auth.model.AuthModel
import com.mole.android.mole.auth.view.AuthBeginView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AuthBeginPresenter(
    private val model: AuthModel,
    private val scope: CoroutineScope,
) :
    MoleBasePresenter<AuthBeginView>() {

    fun onVkClick() {
        applyWithView { view ->
            view.openBrowser { code ->
                Log.i("AuthBegin", code)
                scope.launch {
                    val login = model.getUserVk(code)
                    if (login.isNullOrEmpty()) {
                        view.openDebts()
                    } else {
                        view.openAuthLogin(login)
                    }
                }
            }
        }
    }

    fun onGoogleClick() {
        applyWithView { view ->
            val token = view.googleAccount.idToken
            Log.i("Auth", "Google")
            scope.launch {
                if (token != null) {
                    val login = model.getUserGoogle(token)
                    if (login.isNullOrEmpty()) {
                        view.openDebts()
                    } else {
                        view.openAuthLogin(login)
                    }
                }
            }
        }
    }
}