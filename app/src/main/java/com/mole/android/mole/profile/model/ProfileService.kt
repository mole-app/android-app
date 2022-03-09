package com.mole.android.mole.profile.model

import com.mole.android.mole.profile.data.ProfileUserInfoDomain
import retrofit2.http.POST

interface ProfileService {
    @POST("profile/getProfileInfo")
    suspend fun getProfileInfo(): ProfileUserInfoDomain
}