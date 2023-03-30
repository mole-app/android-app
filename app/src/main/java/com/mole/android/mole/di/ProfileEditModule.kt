package com.mole.android.mole.di

import com.mole.android.mole.profile.presentation.ProfileEditPresenter

class ProfileEditModule(
    private val profileModule: ProfileModule
) {
    val profileEditPresenter
        get() = ProfileEditPresenter(profileModule.getProfileUseCase)

}

