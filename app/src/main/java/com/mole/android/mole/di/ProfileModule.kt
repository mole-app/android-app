package com.mole.android.mole.di

import com.mole.android.mole.profile.domain.GetProfileUseCase
import com.mole.android.mole.profile.domain.GetProfileUseCaseImpl
import com.mole.android.mole.profile.domain.ObserveProfileUseCase
import com.mole.android.mole.profile.domain.ObserveProfileUseCaseImpl
import com.mole.android.mole.profile.model.*
import com.mole.android.mole.profile.presentation.ProfilePresenter

class ProfileModule(
    retrofitModule: RetrofitModule,
    private val baseScopeModule: BaseScopeModule,
) {
    val profilePresenter
        get() = ProfilePresenter(observeProfileUseCase)

    val getProfileUseCase: GetProfileUseCase by lazy {
        GetProfileUseCaseImpl(profileStorage, profileModel)
    }

    private val observeProfileUseCase: ObserveProfileUseCase by lazy {
        ObserveProfileUseCaseImpl(profileStorage, profileModel)
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