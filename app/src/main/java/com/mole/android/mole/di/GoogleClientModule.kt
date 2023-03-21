package com.mole.android.mole.di

import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.mole.android.mole.BuildConfig

class GoogleClientModule(
    activity: AppCompatActivity?
) {
    val googleSignInClient = activity?.let {
        GoogleSignIn.getClient(
            activity,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(BuildConfig.GOOGLE_SERVER_CLIENT_ID)
                .requestEmail()
                .build()
        )
    }
}
