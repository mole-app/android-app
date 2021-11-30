package com.mole.android.mole.devpanel.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.devpanel.view.MoleDebugPanelView

class MoleDebugPanelPresenter : MoleBasePresenter<MoleDebugPanelView>() {

    override fun attachView(view: MoleDebugPanelView) {
        super.attachView(view)
        val hasAccount = view.isHasAccount()
        view.corruptedAccessButtonEnable(hasAccount)
        view.corruptedRefreshButtonEnable(hasAccount)
        view.removeButtonEnable(hasAccount)
    }

    fun onButtonBack() {
        view?.hide()
    }

    fun onButtonCorruptedAccessToken() {
        view?.corruptedAccessToken()
    }

    fun onButtonCorruptedRefreshToken() {
        view?.corruptedRefreshToken()
    }

    fun onButtonRemoveAccount() {
        view?.removeAccount()
        view?.corruptedAccessButtonEnable(false)
        view?.corruptedRefreshButtonEnable(false)
        view?.removeButtonEnable(false)
    }
}