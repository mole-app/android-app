package com.mole.android.mole.auth.model

import com.mole.android.mole.auth.data.AuthDataVkLogin
import com.mole.android.mole.web.service.ApiKey
import retrofit2.http.*

interface AuthService {
    @POST("vkSignIn")
    @ApiKey
    suspend fun getVkAuth(
        @Query("code") code: String,
        @Query("fingerprint") fingerprint: String
    ): AuthDataVkLogin

    @POST("googleSignIn")
    suspend fun getGoogleAuth(
        @Query("googleToken") token: String,
        @Query("fingerprint") fingerprint: String
    ): AuthDataVkLogin
}
