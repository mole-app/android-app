package com.mole.android.mole.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

class MoleComponent(
    val context: Context,
) {
    private val retrofitModule = RetrofitModule()
    private val scopeModule = BaseScopeModule()
    var activity: AppCompatActivity? = null
    val firebaseModule = FirebaseModule()
    val routingModule = RoutingModule()
    val accountManagerModule = AccountManagerModule(context, activity)
    val authModule = AuthModule(context, retrofitModule, scopeModule, firebaseModule)
    val profileModule = ProfileModule(retrofitModule, scopeModule)
    val debtsModule = DebtsModule(routingModule)
    val devPanelModule = DevPanelModule(accountManagerModule, scopeModule)
    val chatModule = ChatModule(scopeModule)
    val createDebtsModule = CreateDebtsModule(scopeModule, retrofitModule)
}
