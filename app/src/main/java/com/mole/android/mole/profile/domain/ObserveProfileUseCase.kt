package com.mole.android.mole.profile.domain

import com.mole.android.mole.profile.model.ProfileModel
import com.mole.android.mole.profile.model.ProfileStorage
import com.mole.android.mole.web.service.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

typealias ProfileResultType = ApiResult<ProfileModel.SuccessProfileResult>

interface ObserveProfileUseCase {
    suspend fun invoke(): Flow<ProfileResultType>
}

class ObserveProfileUseCaseImpl(
    private val storage: ProfileStorage,
    private val model: ProfileModel
) : ObserveProfileUseCase {
    override suspend fun invoke(): Flow<ProfileResultType> {
        return flow {
            val cached = storage.get()
            if (cached != null) emit(ApiResult.create(ProfileModel.SuccessProfileResult(cached)))

            val newData = model.getProfileInfo().withResult {
                storage.set(it.profileUserInfo)
            }
            emit(newData)
        }
    }
}
