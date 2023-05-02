package com.mole.android.mole.profile.model

import com.mole.android.mole.profile.data.ProfileUserInfo
import com.mole.android.mole.web.service.ApiResult


interface ProfileModel {
    suspend fun getProfileInfo(): ApiResult<SuccessProfileResult>
    suspend fun setProfileInfo(name: String, login: String)

    class SuccessProfileResult(val profileUserInfo: ProfileUserInfo)
}
