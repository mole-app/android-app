package com.mole.android.mole.repay.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.chat.data.DebtType
import com.mole.android.mole.repay.data.RepayData
import com.mole.android.mole.repay.model.RepayModel
import com.mole.android.mole.repay.view.RepayView
import kotlinx.coroutines.launch

class RepayPresenter(
    private val model: RepayModel,
    private val repayData: RepayData?
) : MoleBasePresenter<RepayView>() {

    private var isLoading: Boolean = false

    fun onInitMaxValue(): Int = repayData?.allDebtsSum ?: 0

    fun onRepayButtonClick(amount: Int) {
        if (!isLoading) {
            withView { view ->
                sendRepayAmount(view, amount)
            }
        }
    }

    private fun sendRepayAmount(view: RepayView, amount: Int) {
        isLoading = true
        withScope {
            launch {
                model.repayAmount(
                    userId = repayData?.userId ?: -1,
                    type = repayData?.debtType ?: DebtType.GET,
                    amount = amount
                )
                    .withResult {
                        isLoading = false
                        view.hideLoading()
                        view.closeScreen(repayData?.userId ?: -1)
                    }
                    .withError {
                        isLoading = false
                        view.hideLoading()
                        view.showError()
                    }
            }
        }
    }
}
