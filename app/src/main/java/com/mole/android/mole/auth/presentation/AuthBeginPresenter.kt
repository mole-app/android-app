package com.mole.android.mole.auth.presentation

import android.util.Log
import com.github.terrakok.cicerone.Router
import com.google.android.gms.tasks.Task
import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.auth.model.AuthModelImplementation
import com.mole.android.mole.auth.view.AuthBeginView
import com.mole.android.mole.navigation.Screens
import kotlinx.coroutines.launch
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.mole.android.mole.auth.model.AuthModel
import kotlinx.coroutines.CoroutineScope

class AuthBeginPresenter(
    private val model: AuthModel,
    private val router: Router,
    private val scope: CoroutineScope
) :
    MoleBasePresenter<AuthBeginView>() {
    private lateinit var client: GoogleSignInClient

    companion object {
        private const val VK_URL = "https://oauth.vk.com/authorize?" +
                "client_id=7843036" +
                "&display=mobile" +
                "&redirect_uri=https://mole-app.ru/android/auth" +
                "&scope=friends" +
                "&response_type=code" +
                "&v=5.131"
    }

    fun onVkClick() {
        router.setResultListener("code") { data ->
            val code = data as String
            Log.i("AuthBegin", code)
            scope.launch {
                val login = model.getUserVk(code)
                Log.i("AuthBegin", login)
                router.navigateTo(Screens.AuthLogin())
            }
        }
        router.navigateTo(Screens.AuthBrowser(VK_URL, router))
    }

    fun onGoogleClick() {
        Log.i("Auth", "Google")

        router.setResultListener("code") { data ->
            val code = data as String
            Log.i("AuthBegin", code)
            scope.launch {
                model.getUserGoogle(code)
            }
        }
        router.navigateTo(Screens.AuthBrowser(VK_URL, router))

    }
}