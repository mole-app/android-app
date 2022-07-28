package com.mole.android.mole.debts.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.debts.model.DebtsModel
import com.mole.android.mole.debts.view.DebtsView
import kotlinx.coroutines.launch

class DebtsPresenter(
    private val model: DebtsModel
) : MoleBasePresenter<DebtsView>() {

    override fun attachView(view: DebtsView) {
        super.attachView(view)
        view.showLoading()
        dataLoading(view)

    }

    fun onItemShortClick(idDebtor: Int) {
        view?.showChatScreen(idDebtor)
    }

    fun onItemLongClick() {
        view?.showDeleteDialog()
    }

    private fun dataLoading(view: DebtsView) {
        withScope {
            launch {
                model.loadDebtsData()
                    .withResult { result ->
                        view.setData(result)
                        view.hideLoading()
                    }
                    .withError { error ->
                        view.hideLoading()
                        view.showError(error.code, error.description)
                    }
            }
        }
    }
}