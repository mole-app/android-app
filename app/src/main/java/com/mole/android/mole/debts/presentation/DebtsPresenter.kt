package com.mole.android.mole.debts.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.debts.data.DebtorData
import com.mole.android.mole.debts.data.DebtsData
import com.mole.android.mole.debts.model.DebtsModel
import com.mole.android.mole.debts.view.DebtsView
import com.mole.android.mole.ui.Content
import com.mole.android.mole.ui.Error
import com.mole.android.mole.ui.Loading
import com.mole.android.mole.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DebtsPresenter(
    private val model: DebtsModel
) : MoleBasePresenter<DebtsView>() {

    private val mutableState: MutableStateFlow<UiState<DebtsData>> =
        MutableStateFlow(Loading())
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
            mutableState.value = Loading()
            launch {
                model.loadDebtsData()
                    .withResult { result ->
                        mutableState.value = Content(result)
                    }
                    .withError { error ->
                        mutableState.value = Error(error)
                    }
            }
        }
    }
}