package com.mole.android.mole.navigation

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.mole.android.mole.auth.view.AuthLoginViewImplementation
import com.mole.android.mole.auth.view.AuthWebViewImpl

object Screens {
    fun AuthLogin() = FragmentScreen { AuthLoginViewImplementation() }

    fun AuthBrowser(url: String, router: Router) = FragmentScreen { AuthWebViewImpl(url, router) }

//    fun AuthGoogle(url: String) = ActivityScreen {
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken("866261272024-9oc61vo2mfgci38pm9duk2d480gljlap.apps.googleusercontent.com")
//            .requestEmail()
//            .build()
//
//        val client = GoogleSignIn.getClient(requireActivity(), gso)
//        client.signInIntent
//    }
}