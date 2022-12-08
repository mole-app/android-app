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
    private var amount: Int = 0

    fun onInitMaxValue(): Int = repayData?.allDebtsSum ?: 0

    fun onInitUiData() {
        withView {
            repayData?.let { data ->
                if (data.allDebtsSum < 0) {
                    it.initUiData(
                        userName = repayData.userName,
                        creatorName = repayData.ownerName,
                        userIconUrl = repayData.userIconUrl,
                        creatorIconUrl = repayData.ownerIconUrl
                    )
                } else {
                    it.initUiData(
                        userName = repayData.ownerName,
                        creatorName = repayData.userName,
                        userIconUrl = repayData.ownerIconUrl,
                        creatorIconUrl = repayData.userIconUrl
                    )
                }
            }
        }
    }

    fun onRepayButtonClick(amount: Int) {
        if (!isLoading) {
            withView { view ->
                view.showLoading()
                this.amount = amount
                sendRepayAmount(view)
            }
        }
    }

    fun onRetryButtonClick() {
        withView { view ->
            view.showLoading()
            sendRepayAmount(view)
        }
    }

    private fun sendRepayAmount(view: RepayView) {
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
