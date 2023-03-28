package com.mole.android.mole.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.mole.android.mole.web.service.AccountRepository
import com.mole.android.mole.web.service.AccountRepositoryImpl

class AccountManagerModule(
    context: Context,
    activity: AppCompatActivity?,
    googleClientModule: GoogleClientModule
) {
    val accountRepository: AccountRepository =
        AccountRepositoryImpl(context, activity, googleClientModule.googleSignInClient)
}
