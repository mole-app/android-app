package com.mole.android.mole.auth.model

import com.mole.android.mole.auth.data.AuthDataVkLogin
import com.mole.android.mole.auth.data.ProfileUserInfo
import retrofit2.http.*

interface AuthService {
    @Headers("ApiKey: true")
    @POST("auth/vkSignIn")
    suspend fun getVkAuth(
        @Query("code") code: String,
        @Query("fingerprint") fingerprint: String
    ): AuthDataVkLogin

    @Headers("ApiKey: true")
    @POST("auth/googleSignIn")
    suspend fun getGoogleAuth(
        @Query("googleToken") token: String,
        @Query("fingerprint") fingerprint: String
    ): AuthDataVkLogin

    @GET("profile/getProfileInfo")
    suspend fun getProfileInfo(): ProfileUserInfo
}
