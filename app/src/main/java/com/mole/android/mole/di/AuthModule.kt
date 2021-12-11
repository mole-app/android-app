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
import com.mole.android.mole.debts.model.DebtsModel
import com.mole.android.mole.debts.model.DebtsModelImplementation
import com.mole.android.mole.debts.presentation.DebtsPresenter

class AuthModule(
    private val context: Context,
    private val retrofitModule: RetrofitModule,
    private val routingModule: RoutingModule,
    private val baseScopeModule: BaseScopeModule,
    private val firebaseModule: FirebaseModule
) : Module() {

    val beginPresenter
        get() = AuthBeginPresenter(
            authModel,
            routingModule.router,
            baseScopeModule.mainScope
        )

    val loginPresenter: (AuthDataVkLogin) -> AuthLoginPresenter = {
        AuthLoginPresenter(authModel, loginResources, it, baseScopeModule.mainScope)
    }

    val debtsPresenter
        get() = DebtsPresenter(
            debtsModel,
            routingModule.router,
            baseScopeModule.mainScope
        )

    private val loginResources: AuthLoginResources by lazy {
        AuthLoginResourcesImplementation(context)
    }

    private val authModel: AuthModel by lazy {
        AuthModelImplementation(
            authService,
            firebaseModule.instInstallation,
            baseScopeModule.mainScope
        )
    }

    private val debtsModel: DebtsModel by lazy {
        DebtsModelImplementation()
    }

    private val authService by lazy {
        retrofitModule.retrofit.create(AuthService::class.java)
    }
}