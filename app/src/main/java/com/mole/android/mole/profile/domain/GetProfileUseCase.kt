package com.mole.android.mole.profile.domain

import com.mole.android.mole.profile.model.ProfileModel
import com.mole.android.mole.profile.model.ProfileStorage
import com.mole.android.mole.web.service.ApiResult

interface GetProfileUseCase {
    suspend fun invoke(): ApiResult<ProfileModel.SuccessProfileResult>
}

class GetProfileUseCaseImpl(
    private val storage: ProfileStorage,
    private val model: ProfileModel
) : GetProfileUseCase {
    override suspend fun invoke(): ApiResult<ProfileModel.SuccessProfileResult> {
        return storage.get()
            ?.let { ApiResult.create(ProfileModel.SuccessProfileResult(it)) }
            ?: model.getProfileInfo().withResult { storage.set(it.profileUserInfo) }
    }
}
