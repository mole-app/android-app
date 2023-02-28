package com.mole.android.mole.auth.view

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.mole.android.mole.MoleBaseView

interface AuthBeginView : MoleBaseView {
    val googleAccount: GoogleSignInAccount

    fun openAuthLogin(login: String)

    fun openDebts()

    fun openBrowser(actionAfter:(String) -> Unit)

    fun showError()
}