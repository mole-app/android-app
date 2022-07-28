package com.mole.android.mole.di

import com.mole.android.mole.debts.model.DebtsModel
import com.mole.android.mole.debts.model.DebtsService
import com.mole.android.mole.debts.model.MockedDebtsModel
import com.mole.android.mole.debts.presentation.DebtsPresenter

class DebtsModule(
    retrofitModule: RetrofitModule,
    private val baseScopeModule: BaseScopeModule
) {

    val debtsPresenter
        get() = DebtsPresenter(debtsModel)

    private val debtsModel: DebtsModel by lazy {
//        DebtsModelImplementation(
//            debtsService,
//            baseScopeModule.mainScope
//        )

        MockedDebtsModel(baseScopeModule.mainScope)
    }

    private val debtsService by lazy {
        retrofitModule.retrofit.create(DebtsService::class.java)
    }
}
