package com.mole.android.mole.debts.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.debts.data.DebtorData
import com.mole.android.mole.debts.model.DebtsModel
import com.mole.android.mole.debts.view.DebtsView
import kotlinx.coroutines.launch

class DebtsPresenter(
    private val model: DebtsModel
) : MoleBasePresenter<DebtsView>() {

    override fun attachView(view: DebtsView) {
        super.attachView(view)
        view.showLoading()
        dataLoading()

    }

    fun onItemShortClick(idDebtor: Int) {
        view?.showChatScreen(idDebtor)
    }

    fun onRetryClick() {
        dataLoading()
    }

    fun onBalanceItem(data: DebtorData) {
        view?.showRepayScreen(data)
    }

    private fun dataLoading() {
        withScope {
            launch {
                model.loadDebtsData()
                    .withResult { result ->
                        withView { view ->
                            view.setData(result)
                            view.hideLoading()
                        }
                    }
                    .withError { error ->
                        withView { view ->
                            view.hideLoading()
                            view.showError(error.code, error.description)
                        }
                    }
            }
        }
    }
}