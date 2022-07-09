package com.mole.android.mole.profile.model

import com.mole.android.mole.profile.data.ProfileData
import com.mole.android.mole.profile.data.ProfilePhoto
import com.mole.android.mole.profile.data.ProfileUserInfoDomain
import retrofit2.http.GET

interface ProfileService {
    @GET("profile/getProfileInfo")
    suspend fun getProfileInfo(): ProfileUserInfoDomain

    companion object {
        private const val PHOTO = "https://sun9-76.userapi.com/s/v1/if1/9vqE-GMh7yRDDpaa233wVV9yqUTwuZTzd3FutD_iFJrcfXVhglQ40p2tum98hGr4K8T8-_zb.jpg?size=50x50&quality=96&crop=381,67,840,840&ava=1"
        val MOCK = ProfileUserInfoDomain(
            ProfileData(1000, "Login", "Name"),
            ProfilePhoto(PHOTO, PHOTO)
        )
    }
}