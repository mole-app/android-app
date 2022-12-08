package com.mole.android.mole.repay.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.repay.data.RepayData
import com.mole.android.mole.repay.model.RepayModel
import com.mole.android.mole.repay.view.RepayView
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

class RepayPresenter(
    private val model: RepayModel,
    private val repayData: RepayData?
) : MoleBasePresenter<RepayView>() {

    private var isLoading: Boolean = false
    private var amount: Int = 0

    fun onInitMaxValue(): Int = repayData?.allDebtsSum?.absoluteValue ?: 0

    fun onInitUiData() {
        withView {
            repayData?.let { data ->
                if (data.allDebtsSum < 0) {
                    it.initUiData(
                        repayingDebtUserName = repayData.ownerName,
                        acceptorDebtUserName = repayData.userName,
                        repayingDebtUserIconUrl = repayData.ownerIconUrl,
                        acceptorDebtUserIconUrl = repayData.userIconUrl
                    )
                } else {
                    it.initUiData(
                        repayingDebtUserName = repayData.userName,
                        acceptorDebtUserName = repayData.ownerName,
                        repayingDebtUserIconUrl = repayData.userIconUrl,
                        acceptorDebtUserIconUrl = repayData.ownerIconUrl
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
                repayData?.let { data ->
                    model.repayAmount(
                        userId = data.userId,
                        amount = amount,
                        isUserRepayDebt = data.allDebtsSum > 0
                    ).withResult {
                        isLoading = false
                        view.hideLoading()
                        view.closeScreen(data.userId)
                    }.withError {
                        isLoading = false
                        view.hideLoading()
                        view.showError()
                    }
                }
            }
        }
    }
}
