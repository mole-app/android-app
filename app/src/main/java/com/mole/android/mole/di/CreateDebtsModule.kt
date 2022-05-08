package com.mole.android.mole.di

import com.mole.android.mole.create.presentation.ChooseAmountPresenter
import com.mole.android.mole.create.presentation.ChooseNamePresenter
import com.mole.android.mole.create.presentation.ChooseSidePresenter
import com.mole.android.mole.create.presentation.ChooseTagPresenter

class CreateDebtsModule {
    val chooseSidePresenter = ChooseSidePresenter()
    val chooseNamePresenter = ChooseNamePresenter()
    val chooseTagPresenter = ChooseTagPresenter()
    val chooseAmountPresenter = ChooseAmountPresenter()
}