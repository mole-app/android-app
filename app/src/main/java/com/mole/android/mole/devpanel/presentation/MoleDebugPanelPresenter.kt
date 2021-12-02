package com.mole.android.mole.devpanel.presentation

import com.mole.android.mole.MoleBasePresenter
import com.mole.android.mole.devpanel.model.DevPanelModel
import com.mole.android.mole.devpanel.model.DevPanelModel.Companion.CORRUPTED_PART
import com.mole.android.mole.devpanel.view.MoleDebugPanelView

class MoleDebugPanelPresenter(
    private val model: DevPanelModel
) : MoleBasePresenter<MoleDebugPanelView>() {

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
        val accessToken = model.getAccessToken()
        val corruptedAccessToken =
            accessToken?.removeRange(accessToken.length / 2, accessToken.length) + CORRUPTED_PART
        model.setAccessToken(corruptedAccessToken)
    }

    fun onButtonCorruptedRefreshToken() {
        val refreshToken = model.getRefreshToken()
        val corruptedAccessToken =
            refreshToken?.removeRange(refreshToken.length / 2, refreshToken.length) + CORRUPTED_PART
        model.setRefreshToken(corruptedAccessToken)
    }

    fun onButtonRemoveAccount() {
        view?.removeAccount()
        view?.corruptedAccessButtonEnable(false)
        view?.corruptedRefreshButtonEnable(false)
        view?.removeButtonEnable(false)
    }
}