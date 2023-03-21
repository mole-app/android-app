package com.mole.android.mole.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignInClient

class MoleComponent(
    val context: Context,
) {
    private val retrofitModule = RetrofitModule()
    private val scopeModule = BaseScopeModule()
    var activity: AppCompatActivity? = null
    var googleSignInClient: GoogleSignInClient? = null
    val remoteConfigModule: RemoteConfigModule = RemoteConfigModule()
    val buildConfigModule: BuildConfigModule = BuildConfigModule()
    val firebaseModule = FirebaseModule()
    val routingModule = RoutingModule()
    val accountManagerModule by lazy { AccountManagerModule(context, activity, googleSignInClient) }
    val authModule = AuthModule(context, retrofitModule, scopeModule, firebaseModule)
    val profileModule = ProfileModule(retrofitModule, scopeModule)
    val debtsModule = DebtsModule(retrofitModule, scopeModule)
    val settingsModule = SettingsModule()
    val aboutModule = AboutModule(retrofitModule, scopeModule)
    val devPanelModule by lazy { DevPanelModule(accountManagerModule, scopeModule) }
    val chatModule = ChatModule(retrofitModule, scopeModule)
    val createDebtsModule = CreateDebtsModule(scopeModule, retrofitModule)
    val repayModule = RepayModule(retrofitModule, profileModule, scopeModule)
}
