package com.mole.android.mole.di

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignInClient

class MoleComponent(
    val context: Context,
) {
    var activity: AppCompatActivity? = null
    var googleSignInClient: GoogleSignInClient? = null

    private val retrofitModule by lazy { RetrofitModule() }
    private val scopeModule by lazy { BaseScopeModule() }

    val remoteConfigModule: RemoteConfigModule by lazy { RemoteConfigModule() }
    val buildConfigModule: BuildConfigModule by lazy { BuildConfigModule() }
    val firebaseModule by lazy { FirebaseModule() }
    val routingModule by lazy { RoutingModule() }
    val accountManagerModule by lazy { AccountManagerModule(context, activity, googleSignInClient) }
    val authModule by lazy { AuthModule(context, retrofitModule, scopeModule, firebaseModule) }
    val profileModule by lazy { ProfileModule(retrofitModule, scopeModule) }
    val debtsModule by lazy { DebtsModule(retrofitModule, scopeModule) }
    val settingsModule by lazy { SettingsModule() }
    val aboutModule by lazy { AboutModule(retrofitModule, scopeModule) }
    val devPanelModule by lazy { DevPanelModule(accountManagerModule, scopeModule) }
    val chatModule by lazy { ChatModule(retrofitModule, scopeModule) }
    val createDebtsModule by lazy { CreateDebtsModule(scopeModule, retrofitModule) }
    val repayModule by lazy { RepayModule(retrofitModule, profileModule, scopeModule) }
}
