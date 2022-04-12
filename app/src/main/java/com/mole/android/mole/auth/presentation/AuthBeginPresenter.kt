package com.mole.android.mole.auth.presentation

import android.util.Log
import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.auth.model.AuthModel
import com.mole.android.mole.auth.view.AuthBeginView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AuthBeginPresenter(
    private val model: AuthModel,
) :
    MoleBasePresenter<AuthBeginView>() {

    fun onVkClick() {
        withView { view ->
            view.openBrowser { code ->
                Log.i("AuthBegin", code)
                withScope {
                    it.launch {
                        model.getUserVk(code).withResult { successResult ->
                            when (successResult) {
                                is AuthModel.SuccessAuthResult.SuccessForExistedUser -> view.openDebts()
                                is AuthModel.SuccessAuthResult.SuccessNewUser -> view.openAuthLogin(
                                    successResult.login
                                )
                            }
                        }.withError {
                        }
                    }
                }
            }
        }
    }

    fun onGoogleClick() {
        withView { view ->
            val token = view.googleAccount.idToken
            Log.i("Auth", "Google")
            withScope {
                it.launch {
                    if (token != null) {
                        model.getUserGoogle(token).withResult { successResult ->
                            when (successResult) {
                                is AuthModel.SuccessAuthResult.SuccessForExistedUser -> view.openDebts()
                                is AuthModel.SuccessAuthResult.SuccessNewUser -> view.openAuthLogin(
                                    successResult.login
                                )
                            }
                        }.withError {
                        }
                    }
                }
            }
        }
    }
}