package com.mole.android.mole.auth.presentation

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
        withView { view ->
            view.openBrowser { code ->
                scope.launch {
                    model.getUserVk(code).withResult { successResult ->
                        when (successResult) {
                            is AuthModel.SuccessAuthResult.SuccessForExistedUser -> view.openDebts()
                            is AuthModel.SuccessAuthResult.SuccessNewUser -> view.openAuthLogin(
                                successResult.login
                            )
                        }
                    }.withError {
                        view.showError()
                    }
                }
            }
        }
    }

    fun onGoogleClick() {
        withView { view ->
            val token = view.googleAccount.idToken
            withScope {
                launch {
                    if (token != null) {
                        model.getUserGoogle(token).withResult { successResult ->
                            when (successResult) {
                                is AuthModel.SuccessAuthResult.SuccessForExistedUser -> view.openDebts()
                                is AuthModel.SuccessAuthResult.SuccessNewUser -> view.openAuthLogin(
                                    successResult.login
                                )
                                else -> {}
                            }
                        }.withError {
                            view.showError()
                        }
                    }
                }
            }
        }
    }
}