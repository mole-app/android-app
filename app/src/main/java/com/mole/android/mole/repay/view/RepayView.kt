package com.mole.android.mole.repay.view

import com.mole.android.mole.MoleBaseView

interface RepayView : MoleBaseView {
    fun initUiData(
        userName: String,
        ownerName: String,
        userIconUrl: String,
        ownerIconUrl: String
    )
    fun showLoading()
    fun hideLoading()
    fun showError()
    fun closeScreen(userId: Int)
}
