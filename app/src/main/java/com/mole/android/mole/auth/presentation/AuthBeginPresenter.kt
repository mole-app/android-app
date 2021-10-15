package com.mole.android.mole.auth.presentation

import android.util.Log
import com.github.terrakok.cicerone.Router
import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.auth.view.AuthBeginView
import com.mole.android.mole.navigation.Screens
import kotlinx.coroutines.launch
import com.mole.android.mole.auth.model.AuthModel
import kotlinx.coroutines.CoroutineScope

class AuthBeginPresenter(
    private val model: AuthModel,
    private val router: Router,
    private val scope: CoroutineScope,
    private val vkAuthUrl: String
) :
    MoleBasePresenter<AuthBeginView>() {

    fun onVkClick() {
        router.setResultListener("code") { data ->
            val code = data as String
            Log.i("AuthBegin", code)
            scope.launch {
                val login = model.getUserVk(code)
                router.replaceScreen(Screens.AuthLogin(login))
            }
        }
        router.navigateTo(Screens.AuthBrowser(vkAuthUrl))
    }

    fun onGoogleClick() {
        val token = view?.googleAccount?.idToken
        Log.i("Auth", "Google")
        scope.launch {
            if (token != null) {
                val login = model.getUserGoogle(token)
                router.replaceScreen(Screens.AuthLogin(login))
            }
        }
    }
}