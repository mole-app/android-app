package com.mole.android.mole.devpanel.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.devpanel.view.MoleDebugPanelView

class MoleDebugPanelPresenter : MoleBasePresenter<MoleDebugPanelView>() {

    override fun attachView(view: MoleDebugPanelView) {
        super.attachView(view)
        view.corruptedButtonEnable(view.isHasAccount())
        view.removeButtonEnable(view.isHasAccount())
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
        view?.corruptedButtonEnable(false)
        view?.removeButtonEnable(false)
    }
}