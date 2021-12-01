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
    val accountManagerModule = AccountManagerModule(context)
    val authModule = AuthModule(context, retrofitModule, routingModule, scopeModule, firebaseModule)
    val debtsModule = DebtsModule(routingModule, scopeModule)
    var activity: AppCompatActivity? = null
}
