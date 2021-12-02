package com.mole.android.mole.di

import android.content.Context
import com.mole.android.mole.auth.data.AuthDataLogin
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
    private val baseScopeModule: BaseScopeModule,
    private val firebaseModule: FirebaseModule,
) {

    val beginPresenter
        get() = AuthBeginPresenter(
            authModel,
            baseScopeModule.mainScope,
        )

    val loginPresenter: (AuthDataLogin) -> AuthLoginPresenter = {
        AuthLoginPresenter(authModel, loginResources, it, baseScopeModule.mainScope)
    }

    private val loginResources: AuthLoginResources by lazy {
        AuthLoginResourcesImplementation(context)
    }

    private val authModel: AuthModel by lazy {
        AuthModelImplementation(
            authService,
            firebaseModule,
            baseScopeModule.mainScope
        )
    }

    private val authService by lazy {
        retrofitModule.retrofit.create(AuthService::class.java)
    }
}