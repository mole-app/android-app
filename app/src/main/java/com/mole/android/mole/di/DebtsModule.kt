package com.mole.android.mole.di

import com.mole.android.mole.debts.model.DebtsModel
import com.mole.android.mole.debts.model.DebtsModelImplementation
import com.mole.android.mole.debts.presentation.DebtsPresenter

class DebtsModule(
    baseScopeModule: BaseScopeModule,
) {
    private val debtsModel: DebtsModel by lazy {
        DebtsModelImplementation()
    }

    val debtsPresenter = DebtsPresenter(debtsModel, baseScopeModule.mainScope)
}
