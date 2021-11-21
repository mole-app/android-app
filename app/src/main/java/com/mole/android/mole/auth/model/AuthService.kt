package com.mole.android.mole.auth.model

import com.mole.android.mole.auth.data.AuthDataVkLogin
import com.mole.android.mole.auth.data.ProfileUserInfo
import retrofit2.http.*

interface AuthService {
    @POST("vkSignIn")
    @Headers("ApiKey: true")
    suspend fun getVkAuth(
        @Query("code") code: String,
        @Query("fingerprint") fingerprint: String
    ): AuthDataVkLogin

    @POST("googleSignIn")
    @Headers("ApiKey: true")
    suspend fun getGoogleAuth(
        @Query("googleToken") token: String,
        @Query("fingerprint") fingerprint: String
    ): AuthDataVkLogin

    @GET("profile/getProfileInfo")
    suspend fun getProfileInfo(): ProfileUserInfo
}
