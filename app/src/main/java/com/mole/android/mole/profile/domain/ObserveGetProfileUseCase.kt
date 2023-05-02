package com.mole.android.mole.profile.domain

import com.mole.android.mole.profile.model.ProfileModel
import com.mole.android.mole.profile.model.ProfileStorage
import com.mole.android.mole.web.service.ApiResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

typealias ProfileResultType = ApiResult<ProfileModel.SuccessProfileResult>

interface ObserveGetProfileUseCase {
    suspend fun invoke(): StateFlow<ProfileResultType>
}

class ObserveGetProfileUseCaseImpl(
    private val storage: ProfileStorage,
    private val model: ProfileModel,
    private val scope: CoroutineScope
) : ObserveGetProfileUseCase {

    private val profileState = MutableSharedFlow<ProfileResultType>()

    override suspend fun invoke(): StateFlow<ProfileResultType> {
        scope.launch {
            storage.getFresh().collect {
                when (it) {
                    null -> profileState.emit(model.getProfileInfo())
                    else -> profileState.emit(ApiResult.create(ProfileModel.SuccessProfileResult(it)))
                }
            }
        }
        return profileState.stateIn(scope)
    }
}
