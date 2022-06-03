package com.mole.android.mole.di

import com.mole.android.mole.create.data.MockedFindUserModel
import com.mole.android.mole.create.data.MockedProvideTagsModel
import com.mole.android.mole.create.presentation.ChooseAmountPresenter
import com.mole.android.mole.create.presentation.ChooseNamePresenter
import com.mole.android.mole.create.presentation.ChooseSidePresenter
import com.mole.android.mole.create.presentation.ChooseTagPresenter

class CreateDebtsModule(baseScopeModule: BaseScopeModule) {
    val chooseSidePresenter by lazy { ChooseSidePresenter() }
    val chooseNamePresenter by lazy { ChooseNamePresenter(findUserModel) }
    val chooseTagPresenter by lazy { ChooseTagPresenter(provideTagsModel) }
    val chooseAmountPresenter by lazy { ChooseAmountPresenter() }
    private val findUserModel by lazy { MockedFindUserModel(baseScopeModule.ioScope) }
    private val provideTagsModel by lazy { MockedProvideTagsModel(baseScopeModule.ioScope) }
}