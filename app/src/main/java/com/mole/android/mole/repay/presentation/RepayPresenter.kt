package com.mole.android.mole.repay.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.profile.domain.GetProfileUseCase
import com.mole.android.mole.repay.data.RepayData
import com.mole.android.mole.repay.model.RepayModel
import com.mole.android.mole.repay.view.RepayView
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

class RepayPresenter(
    private val getProfileUseCase: GetProfileUseCase,
    private val model: RepayModel,
    private val repayData: RepayData?,
    private val isOpenChat: Boolean
) : MoleBasePresenter<RepayView>() {

    private var isLoading: Boolean = false
    private var amount: Int = 0

    fun onInitMaxValue(): Int = repayData?.allDebtsSum?.absoluteValue ?: 0

    fun onInitUiData() {
        withScope {
            launch {
                getProfileUseCase.invoke().withResult { profile ->
                    renderData(profile.profileUserInfo.name, profile.profileUserInfo.photoNormal)
                }.withError {
                    renderData("", "")
                }
            }
        }
    }

    private fun renderData(profileName: String, profileIcon: String) {
        repayData?.let { data ->
            withView { view ->
                if (data.allDebtsSum < 0) {
                    view.initUiData(
                        repayingDebtUserName = profileName,
                        acceptorDebtUserName = repayData.userName,
                        repayingDebtUserIconUrl = profileIcon,
                        acceptorDebtUserIconUrl = repayData.userIconUrl
                    )
                } else {
                    view.initUiData(
                        repayingDebtUserName = repayData.userName,
                        acceptorDebtUserName = profileName,
                        repayingDebtUserIconUrl = repayData.userIconUrl,
                        acceptorDebtUserIconUrl = profileIcon
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
                sendRepayAmount()
            }
        }
    }

    fun onRetryButtonClick() {
        withView { view ->
            view.showLoading()
            sendRepayAmount()
        }
    }

    private fun sendRepayAmount() {
        isLoading = true
        withScope {
            launch {
                repayData?.let { data ->
                    model.repayAmount(
                        userId = data.userId,
                        amount = amount,
                        isUserRepayDebt = data.allDebtsSum > 0
                    ).withResult {
                        withView { view ->
                            isLoading = false
                            view.hideLoading()
                            view.closeScreen(data.userId, isOpenChat)
                        }
                    }.withError {
                        withView { view ->
                            isLoading = false
                            view.hideLoading()
                            view.showError()
                        }
                    }
                }
            }
        }
    }
}
