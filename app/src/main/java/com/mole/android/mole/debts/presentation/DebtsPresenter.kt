package com.mole.android.mole.debts.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.debts.data.DebtorData
import com.mole.android.mole.debts.data.DebtsData
import com.mole.android.mole.debts.model.DebtsModel
import com.mole.android.mole.debts.view.DebtsView
import com.mole.android.mole.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DebtsPresenter(
    private val model: DebtsModel
) : MoleBasePresenter<DebtsView>() {

    private val mutableState = MutableStateFlow(UiState<DebtsData>())
    val state: StateFlow<UiState<DebtsData>> = mutableState.asStateFlow()

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
            mutableState.value = UiState(
                isLoading = true,
                isContentVisible = true,
                isErrorVisible = false
            )
            launch {
                model.loadDebtsData()
                    .withResult { result ->
                        mutableState.value = UiState(
                            isLoading = false,
                            content = result,
                            isContentVisible = true,
                            isErrorVisible = false
                        )
//                        withView { view ->
//                            view.setData(result)
//                            view.hideLoading()
//                        }
                    }
                    .withError { error ->
                        mutableState.value = UiState(
                            isLoading = false,
                            error = error,
                            isContentVisible = false,
                            isErrorVisible = true
                        )
//                        withView { view ->
//                            view.hideLoading()
//                            view.showError(error.code, error.description)
//                        }
                    }
            }
        }
    }
}