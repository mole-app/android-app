package com.mole.android.mole.create.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.create.data.CreateDebtModel
import com.mole.android.mole.create.data.CreateDebtsDataRepository
import com.mole.android.mole.create.view.amount.ChooseAmountView

class ChooseAmountPresenter(
    private val createDebtModel: CreateDebtModel,
    private val dataRepository: CreateDebtsDataRepository
) : MoleBasePresenter<ChooseAmountView>()  {

    fun confirm(amount: Int) {
//        createDebtModel.createDebt(
//            userId = dataRepository.userId(),
//            side = dataRepository.side(),
//            tag = dataRepository.tag(),
//            amount = amount
//        )
    }
}