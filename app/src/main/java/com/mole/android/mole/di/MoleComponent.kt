package com.mole.android.mole.di

import android.accounts.AccountManager
import android.content.Context
import com.mole.android.mole.BuildConfig
import com.mole.android.mole.component

class MoleComponent(
    val context: Context
) {
    private val retrofitModule = RetrofitModule()
    private val scopeModule = BaseScopeModule()
    val firebaseModule = FirebaseModule()
    val routingModule = RoutingModule()
    val authModule = AuthModule(context, retrofitModule, routingModule, scopeModule, firebaseModule)
    val accountManagerModule = AccountManagerModule(context)
}
