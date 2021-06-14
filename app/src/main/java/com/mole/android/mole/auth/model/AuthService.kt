package com.mole.android.mole.auth.model

import com.mole.android.mole.auth.data.AuthDataVkLogin
import retrofit2.http.*

interface AuthService {
    @POST("vkSignIn")
    suspend fun getVkAuth(
        @Query("code") code: String,
        @Query("fingerprint") fingerprint: String,
        @Header("x-api-key") apiKey: String = "testAndroid"
    ): AuthDataVkLogin

    @POST("googleSignIn")
    suspend fun getGoogleAuth(
        @Query("googleToken") token: String,
        @Query("fingerprint") fingerprint: String,
        @Header("x-api-key") apiKey: String = "testAndroid"
    ): AuthDataVkLogin
}
