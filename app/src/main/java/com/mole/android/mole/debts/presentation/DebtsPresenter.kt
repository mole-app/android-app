package com.mole.android.mole.debts.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.debts.data.DebtorData
import com.mole.android.mole.debts.model.DebtsModel
import com.mole.android.mole.debts.view.DebtsView
import com.mole.android.mole.web.service.State
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DebtsPresenter(
    private val model: DebtsModel
) : MoleBasePresenter<DebtsView>() {

    override fun attachView(view: DebtsView) {
        super.attachView(view)
        dataLoading()

    }

    fun onItemShortClick(idDebtor: Int) {
        view?.showChatScreen(idDebtor)
    }

    fun onBalanceItem(data: DebtorData) {
        view?.showRepayScreen(data)
    }

    fun onRetryClick() {
        dataLoading()
    }

    private fun dataLoading() {
        withScope {
            launch {
                model.loadDebtsData().collect { state ->
                    when (state) {
                        is State.Loading -> {
                            withView { it.showLoading() }
                        }
                        is State.Content -> {
                            withView {
                                it.setData(state.data)
                                it.hideLoading()
                            }
                        }
                        is State.Error -> {
                            withView {
                                it.hideLoading()
                                it.showError(-1, "error")
                            }
                        }
                    }
                }
            }
        }
    }
}