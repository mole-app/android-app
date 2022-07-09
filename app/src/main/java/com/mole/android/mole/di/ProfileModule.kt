package com.mole.android.mole.di

import com.mole.android.mole.profile.model.ProfileModel
import com.mole.android.mole.profile.model.ProfileModelImpl
import com.mole.android.mole.profile.model.ProfileModelMockImpl
import com.mole.android.mole.profile.model.ProfileService
import com.mole.android.mole.profile.presentation.ProfilePresenter

class ProfileModule(
    retrofitModule: RetrofitModule,
    private val baseScopeModule: BaseScopeModule,
) {
    val profilePresenter
        get() = ProfilePresenter(profileModel)

    private val profileModel: ProfileModel by lazy {
        ProfileModelImpl(
            profileService,
            baseScopeModule.mainScope
        )
//        ProfileModelMockImpl(
//            baseScopeModule.mainScope
//        )
    }

    private val profileService by lazy {
        retrofitModule.retrofit.create(ProfileService::class.java)
    }
}