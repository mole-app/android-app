package com.mole.android.mole.auth.model

import com.mole.android.mole.auth.data.AuthDataVkLogin
import com.mole.android.mole.auth.data.ProfileUserInfo
import com.mole.android.mole.web.service.RequestTokenInterceptor.Companion.API_KEY
import retrofit2.http.*

interface AuthService {
    @Headers("$API_KEY")
    @POST("auth/vkSignIn")
    suspend fun getVkAuth(
        @Query("code") code: String,
        @Query("fingerprint") fingerprint: String
    ): AuthDataVkLogin

    @Headers("$API_KEY")
    @POST("auth/googleSignIn")
    suspend fun getGoogleAuth(
        @Query("googleToken") token: String,
        @Query("fingerprint") fingerprint: String
    ): AuthDataVkLogin

    @GET("profile/getProfileInfo")
    suspend fun getProfileInfo(): ProfileUserInfo
}
