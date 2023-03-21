package com.mole.android.mole.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.mole.android.mole.web.service.AccountRepository
import com.mole.android.mole.web.service.AccountRepositoryImpl

class AccountManagerModule(
    context: Context,
    activity: AppCompatActivity?,
    googleSignInClient: GoogleSignInClient?
) {
    val accountRepository: AccountRepository =
        AccountRepositoryImpl(context, activity, googleSignInClient)
}
