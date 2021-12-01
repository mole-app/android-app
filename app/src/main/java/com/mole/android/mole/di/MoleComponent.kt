package com.mole.android.mole.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

class MoleComponent(
    val context: Context
) {
    private val retrofitModule = RetrofitModule()
    private val scopeModule = BaseScopeModule()
    val firebaseModule = FirebaseModule()
    val routingModule = RoutingModule()
    val authModule = AuthModule(context, retrofitModule, routingModule, scopeModule, firebaseModule)
    val accountManagerModule = AccountManagerModule(context)
    var activity: AppCompatActivity? = null
}
