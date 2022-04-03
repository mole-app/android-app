package com.mole.android.mole.profile.model

import com.mole.android.mole.profile.data.ProfileUserInfoDomain
import retrofit2.http.GET

interface ProfileService {
    @GET("profile/getProfileInfo")
    suspend fun getProfileInfo(): ProfileUserInfoDomain
}