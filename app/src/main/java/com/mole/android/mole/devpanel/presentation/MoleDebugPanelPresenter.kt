package com.mole.android.mole.devpanel.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.devpanel.model.DevPanelModel
import com.mole.android.mole.devpanel.view.MoleDebugPanelView

class MoleDebugPanelPresenter(
    private val model: DevPanelModel
) : MoleBasePresenter<MoleDebugPanelView>() {

    override fun attachView(view: MoleDebugPanelView) {
        super.attachView(view)
        val hasAccount = model.isHasAccount()
        view.corruptedAccessButtonEnable(hasAccount)
        view.corruptedRefreshButtonEnable(hasAccount)
        view.removeButtonEnable(hasAccount)
        view.removeRemoteButtonEnable(hasAccount)
    }

    fun onButtonBack() {
        withView { view ->
            view.hide()
        }
    }

    fun onButtonCorruptedAccessToken() {
        model.corruptedAccessToken()
    }

    fun onButtonCorruptedRefreshToken() {
        model.corruptedRefreshToken()
    }

    fun onButtonRemoveAccount() {
        withView { view ->
            model.removeAccount()
            view.corruptedAccessButtonEnable(false)
            view.corruptedRefreshButtonEnable(false)
            view.removeButtonEnable(false)
            view.removeRemoteButtonEnable(false)
        }
    }

    fun onButtonRemoveRemoteAccount() {
        model.removeRemoteAccount()
    }
}