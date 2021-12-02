package com.mole.android.mole.di

import com.mole.android.mole.devpanel.model.DevPanelModel
import com.mole.android.mole.devpanel.model.DevPanelModelImpl
import com.mole.android.mole.devpanel.presentation.MoleDebugPanelPresenter

class DevPanelModule(private val accountManagerModule: AccountManagerModule) {
    val devPanelPresenter
        get() = MoleDebugPanelPresenter(
            devPanelModel
        )

    private val devPanelModel: DevPanelModel by lazy {
        DevPanelModelImpl(accountManagerModule)
    }
}
