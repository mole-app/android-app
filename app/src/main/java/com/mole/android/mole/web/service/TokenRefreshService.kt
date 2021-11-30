package com.mole.android.mole.web.service

import com.mole.android.mole.web.service.RequestTokenInterceptor.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface TokenRefreshService {
    @GET("auth/refreshToken")
    @Headers("${API_KEY}")
    fun getNewAuthToken(
        @Query("idProfile") idProfile: Long,
        @Query("refreshToken") refreshToken: String,
        @Query("fingerprint") fingerprint: String,
    ): AuthTokenData

}