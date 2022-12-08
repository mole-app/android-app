package com.mole.android.mole.repay.view

import com.mole.android.mole.MoleBaseView

interface RepayView : MoleBaseView {
    fun initUiData(
        repayingDebtUserName: String,
        acceptorDebtUserName: String,
        repayingDebtUserIconUrl: String,
        acceptorDebtUserIconUrl: String
    )
    fun showLoading()
    fun hideLoading()
    fun showError()
    fun closeScreen(userId: Int)
}
