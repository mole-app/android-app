package com.mole.android.mole.profile.model

import com.mole.android.mole.profile.data.ProfileData
import com.mole.android.mole.profile.data.ProfilePhoto
import com.mole.android.mole.profile.data.ProfileUserInfoRemote
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface ProfileService {
    @GET("profile/getProfileInfo")
    suspend fun getProfileInfo(): ProfileUserInfoRemote

    @PUT("profile/editProfile")
    suspend fun editProfile(
        @Query("name") name: String,
        @Query("login") login:String?
    )

    companion object {
        private const val PHOTO = "https://sun9-76.userapi.com/s/v1/if1/9vqE-GMh7yRDDpaa233wVV9yqUTwuZTzd3FutD_iFJrcfXVhglQ40p2tum98hGr4K8T8-_zb.jpg?size=50x50&quality=96&crop=381,67,840,840&ava=1"
        val MOCK = ProfileUserInfoRemote(
            ProfileData(1000, "Login", "Name", "1"),
            ProfilePhoto(PHOTO, PHOTO),
            listOf("Tag"),
            5000
        )
    }
}
