package com.mole.android.mole.create.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.create.view.chooseside.ChooseSideView

class ChooseSidePresenter : MoleBasePresenter<ChooseSideView>() {
    fun onMyDebtClicked() {
        view?.openNextScreen(false)
    }

    fun onHisDebtClicked() {
        view?.openNextScreen(true)
    }
}