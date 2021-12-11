package com.mole.android.mole.di

import android.content.Context

class MoleComponent(
    val context: Context
) {
    private val retrofitModule = RetrofitModule()
    private val scopeModule = BaseScopeModule()
    private val firebaseModule = FirebaseModule()
    val routingModule = RoutingModule()
    val authModule = AuthModule(context, retrofitModule, routingModule, scopeModule, firebaseModule)
}
