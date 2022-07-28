package com.mole.android.mole.di

import com.mole.android.mole.settings.presentation.SettingsPresenter

class SettingsModule {
    val settingsPresenter
        get() = SettingsPresenter()
}
