package com.mole.android.mole.web.service

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface TokenRefreshService {
    @GET("auth/refreshToken")
    @Headers("ApiKey: true")
    fun getNewAuthToken(
        @Query("idProfile") idProfile: Long,
        @Query("refreshToken") refreshToken: String,
        @Query("fingerprint") fingerprint: String,
    ): AuthTokenData

}