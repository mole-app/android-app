package com.mole.android.mole.di

import com.mole.android.mole.profile.domain.*
import com.mole.android.mole.profile.model.*
import com.mole.android.mole.profile.presentation.ProfileEditPresenter
import com.mole.android.mole.profile.presentation.ProfilePresenter

class ProfileModule(
    retrofitModule: RetrofitModule,
    private val baseScopeModule: BaseScopeModule,
) {
    val profilePresenter
        get() = ProfilePresenter(observeProfileUseCase)

    val profileEditPresenter
        get() = ProfileEditPresenter(getProfileUseCase, setProfileUseCase)

    private val observeProfileUseCase: ObserveGetProfileUseCase by lazy {
        ObserveGetProfileUseCaseImpl(profileStorage, profileModel, baseScopeModule.ioScope)
    }

    private val setProfileUseCase: SetProfileUseCase by lazy {
        SetProfileUseCaseImpl(profileStorage, profileModel)
    }

    val getProfileUseCase: GetProfileUseCase by lazy {
        GetProfileUseCaseImpl(profileStorage, profileModel)
    }

    private val profileModel: ProfileModel by lazy {
        ProfileModelImpl(
            profileService,
            baseScopeModule.ioScope
        )
    }

    private val profileStorage: ProfileStorage by lazy {
        InMemoryProfileStorage()
    }

    private val profileService by lazy {
        retrofitModule.retrofit.create(ProfileService::class.java)
    }
}
