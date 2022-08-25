package com.mole.android.mole.auth.model

import com.mole.android.mole.auth.data.AuthDataLogin
import com.mole.android.mole.web.service.RequestTokenInterceptor.Companion.API_KEY
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface AuthService {
    @Headers(API_KEY)
    @POST("auth/vkSignIn")
    suspend fun getVkAuth(
        @Query("code") code: String,
        @Query("fingerprint") fingerprint: String
    ): AuthDataLogin

    @Headers(API_KEY)
    @POST("auth/googleSignIn")
    suspend fun getGoogleAuth(
        @Query("googleToken") token: String,
        @Query("fingerprint") fingerprint: String
    ): AuthDataLogin

    @PUT("profile/editProfile")
    suspend fun editProfileInfo(
        @Query("login") login: String,
    )
}
