package com.mole.android.mole.auth.view

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

interface AuthBeginView {
    val googleAccount: GoogleSignInAccount
}