package com.mole.android.mole.di

import android.content.Context
import com.mole.android.mole.auth.data.AuthDataVkLogin
import com.mole.android.mole.auth.model.AuthModel
import com.mole.android.mole.auth.model.AuthModelImplementation
import com.mole.android.mole.auth.model.AuthService
import com.mole.android.mole.auth.presentation.AuthBeginPresenter
import com.mole.android.mole.auth.presentation.AuthLoginPresenter
import com.mole.android.mole.auth.view.AuthLoginResources
import com.mole.android.mole.auth.view.AuthLoginResourcesImplementation

class AuthModule(
    private val context: Context,
    private val retrofitModule: RetrofitModule,
    private val routingModule: RoutingModule,
    private val baseScopeModule: BaseScopeModule,
    private val firebaseModule: FirebaseModule
) : Module() {

    val beginPresenter get() = AuthBeginPresenter(authModel(), routingModule.router, baseScopeModule.mainScope)

    val loginPresenter: (AuthDataVkLogin) -> AuthLoginPresenter =
        { AuthLoginPresenter(authModel(), loginResources(), it, baseScopeModule.mainScope) }

    private val loginResources: () -> AuthLoginResources = single("login_resources") {
        AuthLoginResourcesImplementation(context)
    }

    private val authModel: () -> AuthModel = single("auth_model") {
        AuthModelImplementation(authService(), firebaseModule.instInstallation, baseScopeModule.mainScope)
    }

    private val authService = single("auth_service") {
        retrofitModule.retrofit.create(AuthService::class.java)
    }
}