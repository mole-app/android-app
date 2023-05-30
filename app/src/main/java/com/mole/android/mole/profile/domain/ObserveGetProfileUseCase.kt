package com.mole.android.mole.profile.domain

import com.mole.android.mole.profile.model.ProfileModel
import com.mole.android.mole.profile.model.ProfileStorage
import com.mole.android.mole.web.service.ApiResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

typealias ProfileResultType = ApiResult<ProfileModel.SuccessProfileResult>

interface ObserveGetProfileUseCase {
    suspend fun invoke(): StateFlow<ProfileResultType>
}

class ObserveGetProfileUseCaseImpl(
    private val storage: ProfileStorage,
    private val model: ProfileModel,
    private val scope: CoroutineScope
) : ObserveGetProfileUseCase {

    override suspend fun invoke(): StateFlow<ProfileResultType> {
        return storage.getFresh().flatMapMerge { userInfo ->
            when (userInfo) {
                null -> flow { emit(model.getProfileInfo()) }
                else -> flowOf(ApiResult.create(ProfileModel.SuccessProfileResult(userInfo)))
            }
        }.distinctUntilChanged().stateIn(scope)
    }
}
