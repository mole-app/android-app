package com.mole.android.mole.bottomNavigation.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.bottomNavigation.view.BottomBarView

class BottomBarPresenter : MoleBasePresenter<BottomBarView>() {
    fun onDebtsClick() {
        withView {
            it.openDebts()
        }
    }

    fun onNewDebtClick() {
        withView {
            it.openNewDebt()
        }
    }

    fun onProfileClick() {
        withView {
            it.openProfile()
        }
    }
}