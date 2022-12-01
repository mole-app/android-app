package com.mole.android.mole.di

import com.mole.android.mole.repay.data.RepayData
import com.mole.android.mole.repay.model.RepayModelImplementation
import com.mole.android.mole.repay.model.RepayService
import com.mole.android.mole.repay.presentation.RepayPresenter

class RepayModule(
    retrofitModule: RetrofitModule,
    private val baseScopeModule: BaseScopeModule
) {

    fun repayPresenter(repayData: RepayData): RepayPresenter {
        return RepayPresenter(repayModel, repayData)
    }

    private val repayModel by lazy {
        RepayModelImplementation(
            repayService,
            baseScopeModule.ioScope
        )
    }

    private val repayService by lazy {
        retrofitModule.retrofit.create(RepayService::class.java)
    }
}