package com.mole.android.mole.di

import com.mole.android.mole.create.data.*
import com.mole.android.mole.create.presentation.ChooseAmountPresenter
import com.mole.android.mole.create.presentation.ChooseNamePresenter
import com.mole.android.mole.create.presentation.ChooseSidePresenter
import com.mole.android.mole.create.presentation.ChooseTagPresenter

class CreateDebtsModule(baseScopeModule: BaseScopeModule, retrofitModule: RetrofitModule) {
    val chooseSidePresenter by lazy { ChooseSidePresenter() }
    val chooseNamePresenter by lazy { ChooseNamePresenter(findUserModel) }
    val chooseTagPresenter by lazy { ChooseTagPresenter(provideTagsModel) }
    fun chooseAmountPresenter(dataRepository: CreateDebtsDataRepository) = ChooseAmountPresenter(createDebtModel, dataRepository)
    private val findUserModel by lazy {
        // MOCK
        // MockedFindUserModel(baseScopeModule.ioScope)
        RemoteFindUserModel(service, baseScopeModule.ioScope)
    }
    private val provideTagsModel by lazy {
        // MOCK
        // MockedProvideTagsModel(baseScopeModule.ioScope)
        RemoteProvideTagsModel(service, baseScopeModule.ioScope)
    }
    private val createDebtModel by lazy {
        // MOCK
        // MockedCreateDebtModel(baseScopeModule.ioScope)
        RemoteCreateDebtModel(service, baseScopeModule.ioScope)
    }
    private val service by lazy { retrofitModule.retrofit.create(CreateDebtService::class.java) }
}