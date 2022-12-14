package com.mole.android.mole.profile.model

import com.mole.android.mole.profile.data.ProfileUserInfo
import com.mole.android.mole.web.service.ApiResult
import com.mole.android.mole.web.service.call
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

class ProfileModelImpl(
    private val service: ProfileService,
    private val scope: CoroutineScope
) : ProfileModel {

    override suspend fun getProfileInfo(): ApiResult<ProfileModel.SuccessProfileResult> {
        val task = scope.async {
            call {
                val profileUserInfoDomain = service.getProfileInfo()
                ProfileModel.SuccessProfileResult(
                    ProfileUserInfo(
                        profileUserInfoDomain
                    )
                )
            }
        }
        return task.await()
    }

}