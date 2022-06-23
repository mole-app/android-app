package com.mole.android.mole.create.view.amount

import com.mole.android.mole.MoleBaseView
import com.mole.android.mole.create.view.CreateDebtScreen

interface ChooseAmountView : MoleBaseView {
    fun showError()
    fun closeScreen(result: CreateDebtScreen.CreatedDebt)
    fun disableButton()
    fun enableButton()
}