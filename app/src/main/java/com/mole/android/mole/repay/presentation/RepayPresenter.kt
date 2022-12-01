package com.mole.android.mole.repay.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.repay.data.RepayData
import com.mole.android.mole.repay.model.RepayModel
import com.mole.android.mole.repay.view.RepayView

class RepayPresenter(
    private val model: RepayModel,
    private val repayData: RepayData
) : MoleBasePresenter<RepayView>() {

    fun onInitStartValue(): Int = repayData.currentDebt

    fun onInitMaxValue(): Int = repayData.allDebts

    fun onRepayButtonClick(){}
}