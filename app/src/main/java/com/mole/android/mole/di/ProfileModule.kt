package com.mole.android.mole.di

import com.mole.android.mole.profile.presentation.ProfilePresenter

class ProfileModule {
    val profilePresenter
        get() = ProfilePresenter()
}