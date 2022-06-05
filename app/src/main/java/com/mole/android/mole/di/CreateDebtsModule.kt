package com.mole.android.mole.di

import com.mole.android.mole.create.data.CreateDebtsDataRepository
import com.mole.android.mole.create.data.MockedCreateDebtModel
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
    fun chooseAmountPresenter(dataRepository: CreateDebtsDataRepository) = ChooseAmountPresenter(createDebtModel, dataRepository)
    private val findUserModel by lazy { MockedFindUserModel(baseScopeModule.ioScope) }
    private val provideTagsModel by lazy { MockedProvideTagsModel(baseScopeModule.ioScope) }
    private val createDebtModel by lazy { MockedCreateDebtModel(baseScopeModule.ioScope) }
}