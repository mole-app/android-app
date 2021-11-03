package com.mole.android.mole.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.mole.android.mole.auth.view.AuthLoginViewImplementation
import com.mole.android.mole.auth.view.AuthWebViewImpl

object Screens {
    fun AuthLogin(login: String) = FragmentScreen { AuthLoginViewImplementation.newInstance(login) }

    fun AuthBrowser(url: String) = FragmentScreen { AuthWebViewImpl.newInstance(url) }
}