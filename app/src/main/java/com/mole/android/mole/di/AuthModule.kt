package com.mole.android.mole.di

import android.content.Context
import com.mole.android.mole.auth.data.AuthDataLogin
import com.mole.android.mole.auth.model.AuthModel
import com.mole.android.mole.auth.model.AuthModelImplementation
import com.mole.android.mole.auth.model.AuthService
import com.mole.android.mole.auth.presentation.AuthBeginPresenter
import com.mole.android.mole.auth.presentation.AuthLoginPresenter

class AuthModule(
    private val context: Context,
    private val retrofitModule: RetrofitModule,
    private val baseScopeModule: BaseScopeModule,
    private val fingerprintRepository: FingerprintRepository,
) {

    val beginPresenter
        get() = AuthBeginPresenter(authModel, baseScopeModule.mainScope)

    val loginPresenter: (AuthDataLogin) -> AuthLoginPresenter = {
        AuthLoginPresenter(authModel, it)
    }

    private val authModel: AuthModel by lazy {
        AuthModelImplementation(
            authService,
            fingerprintRepository,
            baseScopeModule.mainScope
        )
    }

    private val authService by lazy {
        retrofitModule.retrofit.create(AuthService::class.java)
    }
}