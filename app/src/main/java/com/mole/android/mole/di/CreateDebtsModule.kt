package com.mole.android.mole.di

import com.mole.android.mole.create.presentation.ChooseNamePresenter
import com.mole.android.mole.create.presentation.ChooseSidePresenter

class CreateDebtsModule {
    val chooseSidePresenter = ChooseSidePresenter()
    val chooseNamePresenter = ChooseNamePresenter()
}