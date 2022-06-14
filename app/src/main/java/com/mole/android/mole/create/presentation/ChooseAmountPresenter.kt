package com.mole.android.mole.create.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.create.data.CreateDebtModel
import com.mole.android.mole.create.data.CreateDebtsDataRepository
import com.mole.android.mole.create.view.CreateDebtScreen
import com.mole.android.mole.create.view.amount.ChooseAmountView
import kotlinx.coroutines.launch

class ChooseAmountPresenter(
    private val createDebtModel: CreateDebtModel,
    private val dataRepository: CreateDebtsDataRepository
) : MoleBasePresenter<ChooseAmountView>()  {

    fun confirm(amount: Int) {
        view?.disableButton()
        withScope {
            launch {
                createDebtModel.createDebt(
                    userId = dataRepository.userId(),
                    side = dataRepository.side(),
                    tag = dataRepository.tag(),
                    amount = amount
                )
                    .withResult { view?.closeScreen(
                            CreateDebtScreen.CreatedDebt(
                                    id = it.id,
                                    side = dataRepository.side(),
                                    avatarUrl = dataRepository.avatarUri(),
                                    userId = dataRepository.userId(),
                                    name = dataRepository.userName(),
                                    tag = dataRepository.tag(),
                                    amount = amount
                                )
                        )
                    }
                    .withError {
                        view?.showError()
                        view?.enableButton()
                    }
            }
        }
    }
}