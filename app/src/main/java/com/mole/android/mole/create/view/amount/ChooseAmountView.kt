package com.mole.android.mole.create.view.amount

import com.mole.android.mole.MoleBaseView

interface ChooseAmountView : MoleBaseView {
    fun showError()
    fun closeScreen(resultId: Int)
}